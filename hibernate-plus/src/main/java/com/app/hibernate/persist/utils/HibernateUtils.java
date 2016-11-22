package com.app.hibernate.persist.utils;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * <p>
 * HibernateUtil
 * </p>
 *
 * @author Caratacus
 * @date 2016-10-14
 */
public class HibernateUtils {
	protected static final Logger logger = Logger.getLogger("HibernateUtil");
	private static final String BASE_COUNT = "SELECT COUNT(0) FROM ";
	private static final String BASE_LIST = " FROM ";

	/**
	 * 生成当前对象的HQL
	 *
	 * @param clazz
	 * @return String
	 */
	public static String getListHql(Class clazz) {
		return getListHql(clazz, Collections.EMPTY_MAP);
	}

	/**
	 * BASE LIST HQL
	 *
	 * @param clazz
	 * @return StringBuilder
	 */
	private static StringBuilder getBaseListHql(Class clazz) {
		return getBaseHql(clazz, BASE_LIST);
	}

	/**
	 * 基本的hql
	 *
	 * @param clazz
	 * @return StringBuilder
	 */
	private static StringBuilder getBaseHql(Class clazz, String baseHql) {
		StringBuilder builder = new StringBuilder(baseHql);
		builder.append(clazz.getSimpleName());
		return builder;
	}

	/**
	 * 生成当前对象的HQL
	 *
	 * @param order
	 * @param clazz
	 * @return String
	 */
	public static String getListHql(String order, Class clazz) {
		return getListHql(order, clazz, Collections.EMPTY_MAP);
	}

	/**
	 * 生成当前对象的HQL
	 *
	 * @param clazz
	 * @param property
	 * @return String
	 */
	public static String getListHql(Class clazz, String... property) {
		return getListHql(StringUtils.EMPTY_STRING, clazz, property);
	}

	/**
	 * 生成当前对象的HQL
	 *
	 * @param clazz
	 * @param params
	 * @return String
	 */
	public static String getListHql(Class clazz, Map<String, Object> params) {
		return getListHql(StringUtils.EMPTY_STRING, clazz, params);
	}

	/**
	 * 生成当前对象的HQL
	 *
	 * @param order
	 * @param clazz
	 * @param property
	 * @return String
	 */
	public static String getListHql(String order, Class clazz, String... property) {
		StringBuilder builder = getBaseListHql(clazz);
		getWhere(builder, property);
		getOrderby(order, builder);
		return builder.toString();
	}

	/**
	 * 生成HQL WHERE SQL
	 *
	 * @param builder
	 * @param property
	 * @return
	 */
	private static void getWhere(StringBuilder builder, String... property) {
		if (StringUtils.isNoneBlank(property)) {
			builder.append(" WHERE ");
			for (int i = 0; i < property.length; i++) {
				builder.append(property[i]);
				if (i == property.length - 1) {
					setByArray(builder, i);
				} else {
					setAndByArray(builder, i);
				}
			}
		}
	}

	/**
	 * 生成当前对象的HQL
	 *
	 * @param order
	 * @param clazz
	 * @param params
	 * @return String
	 */
	public static String getListHql(String order, Class clazz, Map<String, Object> params) {
		StringBuilder builder = getBaseListHql(clazz);
		getWhere(builder, params);
		getOrderby(order, builder);
		return builder.toString();
	}

	/**
	 * 生成HQL ORDER BY SQL
	 *
	 * @param builder
	 * @param builder
	 * @return
	 */
	private static void getOrderby(String order, StringBuilder builder) {
		if (StringUtils.isNotBlank(order)) {
			builder.append(" ORDER BY ");
			builder.append(order);
		}
	}

	/**
	 * 生成当前对象的HQL
	 *
	 * @param clazz
	 * @return String
	 */
	public static String getCountHql(Class clazz) {
		return getCountHql(clazz, Collections.EMPTY_MAP);
	}

	/**
	 * 简单的count hql
	 *
	 * @param clazz
	 * @return StringBuilder
	 */
	private static StringBuilder getBaseCountHql(Class clazz) {
		return getBaseHql(clazz, BASE_COUNT);
	}

	/**
	 * 生成当前对象的HQL
	 *
	 * @param clazz
	 * @param property
	 * @return String
	 */
	public static String getCountHql(Class clazz, String... property) {
		StringBuilder builder = getBaseCountHql(clazz);
		getWhere(builder, property);
		return builder.toString();
	}

	/**
	 * 生成当前对象的HQL
	 *
	 * @param clazz
	 * @param params
	 * @return String
	 */
	public static String getCountHql(Class clazz, Map<String, Object> params) {
		StringBuilder builder = getBaseCountHql(clazz);
		getWhere(builder, params);
		return builder.toString();
	}

	/**
	 * 生成HQL WHERE SQL
	 *
	 * @param builder
	 * @param params
	 * @return
	 */
	private static void getWhere(StringBuilder builder, Map<String, Object> params) {
		if (MapUtils.isNotEmpty(params)) {
			List<String> list = new ArrayList<String>();
			list.addAll(params.keySet());
			builder.append(" WHERE ");
			for (int i = 0; i < list.size(); i++) {
				String property = list.get(i);
				Object value = params.get(property);
				builder.append(property);
				if (i == list.size() - 1) {
					setByMap(builder, property, value);
				} else {
					setAndByMap(builder, property, value);
				}
			}
		}
	}

	/**
	 * sql语句连接
	 *
	 * @param builder
	 * @param index
	 * @return
	 */
	private static void setByArray(StringBuilder builder, int index) {
		builder.append(" = ?");
		builder.append(index);
	}

	/**
	 * sql语句连接And
	 *
	 * @param builder
	 * @param index
	 * @return
	 */
	private static void setAndByArray(StringBuilder builder, int index) {
		builder.append(" = ? AND ");
		builder.append(index);
	}

	/**
	 * sql语句连接
	 *
	 * @param builder
	 * @param property
	 * @param object
	 * @return
	 */
	private static void setByMap(StringBuilder builder, String property, Object object) {
		if (object.getClass().isArray() || object instanceof List)
			builder.append(" IN ");
		else
			builder.append(" = ");
		builder.append(":");
		builder.append(property);
	}

	/**
	 * sql语句连接And
	 *
	 * @param builder
	 * @param property
	 * @param object
	 * @return
	 */
	private static void setAndByMap(StringBuilder builder, String property, Object object) {
		if (object.getClass().isArray() || object instanceof List)
			builder.append(" IN ");
		else
			builder.append(" = ");
		builder.append(":");
		builder.append(property);
		builder.append(" AND ");
	}

	/**
	 * 设置hibernate参数
	 *
	 * @param query
	 * @param key
	 * @param obj
	 * @return
	 */
	public static void setParams(Query query, String key, Object obj) {
		if (obj.getClass().isArray()) {
			query.setParameterList(key, (Object[]) obj);
		} else if (obj instanceof List) {
			query.setParameterList(key, (List) obj);
		} else {
			query.setParameter(key, obj);
		}
	}

	/**
	 * 设置分页
	 *
	 * @param page
	 * @param rows
	 * @param query
	 * @return
	 */
	public static void setPage(int page, int rows, Query query) {
		if (0 != rows) {
			// 只判断row , 如果page异常 初始化为1
			page = Logis.getInteger(page, 1);
			query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		}
	}

	/**
	 * 获取SQLQuery对象
	 *
	 * @param sql
	 * @param factory
	 * @return Query
	 */
	public static Query getSqlQuery(String sql, SessionFactory factory) {
		System.err.println("Execute SQL：" + SqlUtils.sqlFormat(sql, true));
		return getCurrentSession(factory).createSQLQuery(sql);
	}

	/**
	 * 获取HQLQuery对象
	 *
	 * @param hql
	 * @param factory
	 * @return Query
	 */
	public static Query getHqlQuery(String hql, SessionFactory factory) {
		System.err.println("Execute HQL：" + SqlUtils.sqlFormat(hql, true));
		return getCurrentSession(factory).createQuery(hql);
	}

	/**
	 * 获得当前事务的session
	 *
	 * @param factory
	 * @return Session
	 */
	public static Session getCurrentSession(SessionFactory factory) {
		return factory.getCurrentSession();
	}

}
