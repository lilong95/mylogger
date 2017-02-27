package cn.mylogger.mybatis.autobuild;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.UUID;

import org.apache.ibatis.session.SqlSessionException;

public class SqlBuilder {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 生成插入sql
	 */
	public static String buildInsert(Object obj) throws IllegalArgumentException, IllegalAccessException {
		// insert into tabelName (columNames) values(values);
		Class<?> clz = obj.getClass();
		String clzName = clz.getSimpleName();
		String _clzName = convertName(clzName);
		StringBuilder rawInsertSql = new StringBuilder("insert into `" + _clzName + "` ");
		StringBuilder columnsStr = new StringBuilder("(");
		StringBuilder valuesStr = new StringBuilder("(");
		// 获取本身的属性
		Field[] localFields = clz.getDeclaredFields();
		// 获取继承的属性（必须为public的）
		Field[] inheritFields = getSuperFields(clz);
		String _name;
		String fName;
		Object value;
		for (Field field : inheritFields) {
			/**
			 * 抑制Java的访问控制检查 如果不加上上面这句，将会Error: TestPrivate can not access a
			 * member of class PrivateClass with modifiers "private"
			 */
			field.setAccessible(true);
			fName = field.getName();
			_name = convertName(fName);
			value = field.get(obj);
			// 如果id为""就替换掉，easyui的form表单提交会默认提交个""进来
			if (fName.equals("id") && value.equals(""))
				value = UUID.randomUUID().toString();
			// 跳过null
			if (value != null) {
				columnsStr.append(_name + ",");
				if (field.getType() == String.class && value != null)
					valuesStr.append("'" + value + "',");
				else if (field.getType() == java.util.Date.class)
					valuesStr.append("'" + sdf.format((java.util.Date) value) + "'");
				else
					valuesStr.append(value + ",");
			}
		}
		for (Field field : localFields) {
			/**
			 * 抑制Java的访问控制检查 如果不加上上面这句，将会Error: TestPrivate can not access a
			 * member of class PrivateClass with modifiers "private"
			 */
			field.setAccessible(true);
			fName = field.getName();
			_name = convertName(fName);
			value = field.get(obj);
			columnsStr.append(_name);
			if (field.getType() == String.class && value != null)
				valuesStr.append("'" + value + "'");
			else if (field.getType() == java.util.Date.class && value != null)
				valuesStr.append("'" + sdf.format((java.util.Date) value) + "'");
			else
				valuesStr.append(value);
			if (field == localFields[localFields.length - 1]) {
				columnsStr.append(")");
				valuesStr.append(")");
			} else {
				columnsStr.append(",");
				valuesStr.append(",");
			}
		}
		rawInsertSql.append(columnsStr + " values " + valuesStr);
		return rawInsertSql.toString();
	}

	/**
	 * 生成更新sql
	 */
	public static String buildUpdate(Object obj)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Class<?> clz = obj.getClass();
		String clzName = clz.getSimpleName();
		String _clzName = convertName(clzName);
		StringBuilder rawUpdateSql = new StringBuilder("update  `" + _clzName + "` set");
		// 获取本身的属性
		Field[] fields = clz.getDeclaredFields();
		// 获取继承的属性（必须为public的）
		Field[] inheritFields = getSuperFields(clz);
		String _name;
		String fName;
		Object value;
		for (Field field : inheritFields) {
			// 跳过主键
			if (field.getName().equals("id"))
				continue;
			/**
			 * 抑制Java的访问控制检查 如果不加上上面这句，将会Error: TestPrivate can not access a
			 * member of class PrivateClass with modifiers "private"
			 */
			field.setAccessible(true);
			fName = field.getName();
			_name = convertName(fName);
			value = field.get(obj);
			if (field.getType() == String.class && value != null)
				rawUpdateSql.append(" " + _name + " = '" + value + "',");
			else if (field.getType() == java.util.Date.class && value != null)
				rawUpdateSql.append(" " + _name + " = '" + sdf.format((java.util.Date) value) + "',");
			else if (value != null)
				rawUpdateSql.append(" " + _name + " = " + value + ",");
		}
		for (Field field : fields) {
			// 跳过主键
			if (field.getName().equals("id"))
				continue;
			/**
			 * 抑制Java的访问控制检查 如果不加上上面这句，将会Error: TestPrivate can not access a
			 * member of class PrivateClass with modifiers "private"
			 */
			field.setAccessible(true);
			fName = field.getName();
			_name = convertName(fName);
			value = field.get(obj);
			// 跳过null
			if (value != null) {
				if (field.getType() == String.class)
					rawUpdateSql.append(" " + _name + " = '" + value + "',");
				else if (field.getType() == java.util.Date.class)
					rawUpdateSql.append(" " + _name + " = '" + sdf.format((java.util.Date) value) + "',");
				else
					rawUpdateSql.append(" " + _name + " = " + value + ",");
			}
		}
		// 删除最后一个多余的逗号
		rawUpdateSql.deleteCharAt(rawUpdateSql.length() - 1);
		// 获取主键
		Field idField = getIdField(clz);
		idField.setAccessible(true);
		Object id = idField.get(obj);
		if (idField.getType() == String.class)
			rawUpdateSql.append(" where id = '" + id + "'");
		else
			rawUpdateSql.append(" where id = " + id);
		return rawUpdateSql.toString();
	}

	// 构建getById语句
	public static String buildGetByIdSql(Object id, String clazzName) {
		String _clzName = convertName(clazzName);
		StringBuilder getByIdSql = new StringBuilder("select * from `" + _clzName + "` where id = ");
		if (id == null)
			throw new SqlSessionException("Id 不能为 null");
		else if (id.getClass() == String.class)
			getByIdSql.append("'" + id + "'");
		else
			getByIdSql.append(id);
		return getByIdSql.toString();
	}

	// 构建删除语句
	public static String buildDeleteByIdSql(Object id, String clazzName) {
		String _clzName = convertName(clazzName);
		StringBuilder deleteByIdSql = new StringBuilder("delete from `" + _clzName + "` where id = ");
		if (id.getClass() == String.class && id != null)
			deleteByIdSql.append("'" + id + "'");
		else if (id != null)
			deleteByIdSql.append(id);
		return deleteByIdSql.toString();
	}

	/**
	 * 驼峰标识转换为数据库类下划线的命名
	 * 
	 * @param str
	 * @return
	 */
	public static String convertName(String name) {
		// SimpleDateFormat --- simple_date_format
		StringBuilder builder = new StringBuilder();
		if (name != null && name.length() > 0) {
			builder.append(name.charAt(0));
			for (int i = 1; i < name.length(); i++) {
				char c = name.charAt(i);
				if (Character.isUpperCase(c)) {
					builder.append("_");
				}
				builder.append(c);
			}
		}
		return builder.toString().toLowerCase();
	}

	public static Field[] getSuperFields(Class<?> clazz) {
		Field[] fields = null;
		clazz = clazz.getSuperclass();
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				if (fields == null || fields.length == 0) {
					fields = clazz.getDeclaredFields();
				} else {
					Field[] superFields = clazz.getDeclaredFields();
					Field[] newFields = Arrays.copyOf(fields, fields.length + superFields.length);
					if (fields.length > 0 || fields.length == 0) {
						System.arraycopy(superFields, 0, newFields, fields.length, superFields.length);
					}
					fields = newFields;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fields;
	}

	public static Field getIdField(Class<?> clazz) {
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				Field field = clazz.getDeclaredField("id");
				if (field != null) {
					return field;
				}
			} catch (Exception e) {
				// 不作处理
			}
		}
		return null;
	}

}
