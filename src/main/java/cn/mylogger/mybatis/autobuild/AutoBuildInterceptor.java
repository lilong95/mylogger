package cn.mylogger.mybatis.autobuild;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
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

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }
) })
public class AutoBuildInterceptor implements Interceptor {
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory
			.getLog(AutoBuildInterceptor.class);
	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	private static final DefaultReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();
	private static final String INSERT_REGEX = ".*insert$";
	private static final String DELETEBYID_REGEX = ".*deleteById$";
	private static final String UPDATE_REGEX = ".*update$";
	private static final String GETBYID_REGEX = ".*selectById$";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		StatementHandler statementHandler = (StatementHandler) invocation
				.getTarget();
		// MetaObject可包装某一个类然后可以访问到其私有成员
		MetaObject metaStatementHandler = MetaObject.forObject(
				statementHandler, DEFAULT_OBJECT_FACTORY,
				DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
		// 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理
		while (metaStatementHandler.hasGetter("h")) {
			// target存在h代理下面，存在h则必有target
			metaStatementHandler = toTargetMetaObj(metaStatementHandler, "h");
			metaStatementHandler = toTargetMetaObj(metaStatementHandler,
					"target");
		}
		String stmtId = (String) metaStatementHandler
				.getValue("delegate.mappedStatement.id");
		String clazzName = getClazzNameByStmtId(stmtId);
		Object parameterObject = metaStatementHandler
				.getValue("delegate.boundSql.parameterObject");
		String sql = (String) metaStatementHandler
				.getValue("delegate.boundSql.sql");
		if (stmtId.matches(INSERT_REGEX)) {
			sql = SqlBuilder.buildInsert(parameterObject);
		} else if (stmtId.matches(DELETEBYID_REGEX)) {
			sql = SqlBuilder.buildDeleteByIdSql(parameterObject, clazzName);
		} else if (stmtId.matches(UPDATE_REGEX)) {
			sql = SqlBuilder.buildUpdate(parameterObject);
		} else if (stmtId.matches(GETBYID_REGEX)) {
			sql = SqlBuilder.buildGetByIdSql(parameterObject, clazzName);
		}
		metaStatementHandler.setValue("delegate.boundSql.sql",sql);
		return invocation.proceed();
	}

	public MetaObject toTargetMetaObj(MetaObject metaObject, String fieldValue) {
		Object object = metaObject.getValue(fieldValue);
		return MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY,
				DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
	}

	public static String getClazzNameByStmtId(String stmtId) {
		String[] strings = stmtId.split("\\.");
		for (int i = 0; i < strings.length; i++) {
			if (strings[i].contains("Mapper")) {
				return strings[i].substring(0, strings[i].indexOf("Mapper"));
			}
		}
		return "";
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}

}
