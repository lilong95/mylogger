package cn.mylogger.mybatis.page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }

) })
public class PageInterceptor implements Interceptor {
	private static final Log logger = LogFactory.getLog(PageInterceptor.class);
	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	private static final DefaultReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();
	private static String dialect = "mysql"; // 数据库类型(默认为mysql)
	private static String pageSqlId = ".*Page.*$"; // 需要拦截的ID(正则匹配)

	/**
	 * 与注解中的方法签名一样则执行该方法
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// RoutingStatementHandler
		// 会选择一个StatementHandler（Simple，Prepared,Callable），属性名为delegate
		StatementHandler statementHandler = (StatementHandler) invocation
				.getTarget();
		// MetaObject可包装某一个类然后可以访问到其私有成员
		MetaObject metaStatementHandler = MetaObject.forObject(
				statementHandler, DEFAULT_OBJECT_FACTORY,
				DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);

		// 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理
		while (metaStatementHandler.hasGetter("h")) {
			// target存在h代理下面，即存在h则必有target
			metaStatementHandler = toTargetMetaObj(metaStatementHandler, "h");
			metaStatementHandler = toTargetMetaObj(metaStatementHandler,
					"target");
		}

		// getValue方法可以根据属性名取到相应Object，可以使用ognl表达式写法
		// mappedStatement 对应xml中的如<select id="">
		MappedStatement mappedStatement = (MappedStatement) metaStatementHandler
				.getValue("delegate.mappedStatement");
		if (mappedStatement.getId().matches(pageSqlId)) {
			BoundSql boundSql = (BoundSql) metaStatementHandler
					.getValue("delegate.boundSql");
			String rawSql = boundSql.getSql();
			// 重写sql
			String pageSql = buildPageSql(rawSql);
			metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
			// 采用物理分页后，就不需要mybatis的内存分页了，所以重置下面的两个参数
			metaStatementHandler.setValue("delegate.rowBounds.offset",
					RowBounds.NO_ROW_OFFSET);
			metaStatementHandler.setValue("delegate.rowBounds.limit",
					RowBounds.NO_ROW_LIMIT);
			Connection connection = (Connection) invocation.getArgs()[0];
			// 重设分页参数里的总页数等
			setPageParameter(rawSql, connection, mappedStatement, boundSql);
		}

		return invocation.proceed();
	}

	public MetaObject toTargetMetaObj(MetaObject metaObject, String fieldValue) {
		Object object = metaObject.getValue(fieldValue);
		return MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY,
				DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
	}

	/**
	 * 设置page的属性(总记录数和总页数)
	 * 
	 * @param boundSql
	 *            原始的BoundSql
	 * @param rawSql
	 * @param conn
	 */
	private void setPageParameter(String sql, Connection connection,
			MappedStatement mappedStatement, BoundSql boundSql) {
		// 记录总记录数
		String countSql = "select count(0) from (" + sql + ") as total";
		PreparedStatement countStmt = null;
		ResultSet rs = null;
		try {
			countStmt = connection.prepareStatement(countSql);
			// ParameterObject是传入的参数值，对应占位符?
			Object parameterObject = boundSql.getParameterObject();
			// ParameterMappings是javaType和jdbcType对应映射,新的countSql
			BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(),
					countSql, boundSql.getParameterMappings(), parameterObject);
			// 对SQL参数(?)设值，传入相应的parameterObject和要设值的boundSql
			ParameterHandler parameterHandler = new DefaultParameterHandler(
					mappedStatement, parameterObject, countBS);
			// 对哪个prepareStatement进行SQL参数(?)设值
			parameterHandler.setParameters(countStmt);

			rs = countStmt.executeQuery();
			long totalCount = 0;
			if (rs.next()) {
				totalCount = rs.getInt(1);
			}
			// page.setTotalCount(totalCount);
			PageContext.setTotalRecord(totalCount);
			long totalPage = totalCount / PageContext.getPageSize()
					+ ((totalCount % PageContext.getPageSize() == 0) ? 0 : 1);
			PageContext.setTotalPage(totalPage);
		} catch (SQLException e) {
			logger.error("Ignore this exception", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("Ignore this exception", e);
			}
			try {
				countStmt.close();
			} catch (SQLException e) {
				logger.error("Ignore this exception", e);
			}
		}

	}

	/**
	 * 生成相应的pageSql
	 * 
	 * @param rawSql
	 * @return
	 */
	private String buildPageSql(String rawSql) {
		if ("mysql".equals(dialect)) {
			return buildPageSqlForMysql(rawSql);
		} else if ("oracle".equals(dialect)) {
			return buildPageSqlForOracle(rawSql);
		}
		return rawSql;
	}

	/**
	 * 生成Oracle的分页sql
	 * 
	 * @param rawSql
	 * @return
	 */
	private String buildPageSqlForOracle(String rawSql) {
		StringBuilder pageSql = new StringBuilder(100);
		String beginrow = String.valueOf((PageContext.getPageCode() - 1)
				* PageContext.getPageSize());
		String endrow = String.valueOf(PageContext.getPageCode()
				* PageContext.getPageSize());
		pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
		pageSql.append(rawSql);
		pageSql.append(" ) temp where rownum <= ").append(endrow);
		pageSql.append(") where row_id > ").append(beginrow);
		return pageSql.toString();
	}

	/**
	 * 生成mysql的分页sql
	 * 
	 * @param rawSql
	 * @return
	 */
	private String buildPageSqlForMysql(String rawSql) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(rawSql);
		String offSet = String.valueOf((PageContext.getPageCode() - 1)
				* PageContext.getPageSize());
		sqlBuilder.append(" limit " + offSet + "," + PageContext.getPageSize());
		return sqlBuilder.toString();
	}

	@Override
	public Object plugin(Object target) {
		// 这里是直接根据签名确定是返回代理还是原target的
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		if (properties != null) {
			// 如果properties中有配置dialect
			if (properties.getProperty("dialect") != null
					&& properties.getProperty("dialect").length() > 0) {
				dialect = properties.getProperty("dialect");
				logger.debug("dialect : " + dialect);
			}
			// 如果properties中有配置pageSqlId
			if (properties.getProperty("pageSqlId") != null
					&& properties.getProperty("pageSqlId").length() > 0) {
				pageSqlId = properties.getProperty("pageSqlId");
				logger.debug("pageSqlId :" + pageSqlId);
			}
		}
	}

}
