package com.app.hibernate.persist.dao;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * CRUDDao接口
 * </p>
 *
 * @author Caratacus
 * @date 2016-10-14
 */
public interface CRUDDao {

	/**
	 *
	 * 根据hql获取数量
	 * 
	 * @param hql
	 * @return long
	 *
	 */
	public long queryCountWithHql(String hql);

	/**
	 *
	 * 根据hql获取数量
	 * 
	 * @param hql
	 * @param params
	 * @return long
	 *
	 */
	public long queryCountWithHql(String hql, Map<String, Object> params);

	/**
	 *
	 * 执行insert、update hql语句
	 * 
	 * @param hql
	 * @return int
	 *
	 */
	public int executeHql(String hql);

	/**
	 *
	 * 执行insert、update hql语句
	 * 
	 * @param hql
	 * @param params
	 * @return int
	 *
	 */
	public int executeHql(String hql, Map<String, Object> params);

	/**
	 *
	 * 执行insert、update sql语句
	 * 
	 * @param sql
	 * @return int
	 * 
	 */
	public int executeSql(String sql);

	/**
	 *
	 * 执行insert、update sql语句
	 * 
	 * @param sql
	 * @param params
	 * @return int
	 *
	 */
	public int executeSql(String sql, Map<String, Object> params);

	/**
	 *
	 * 根据sql获取数量
	 * 
	 * @param sql
	 * @return long
	 *
	 */
	public long queryCountWithSql(String sql);

	/**
	 *
	 * 根据sql获取数量
	 * 
	 * @param sql
	 * @param params
	 * @return long
	 *
	 */
	public long queryCountWithSql(String sql, Map<String, Object> params);

	/**
	 * 执行sql语句返回list结构
	 *
	 * @param sql
	 * @return List<?
*
	 */
	public List<?> queryListWithSql(String sql);

	/**
	 * 执行sql语句返回map结构
	 *
	 * @param sql
	 * @return Map<?,?>
	 *
	 */
	public Map<?, ?> queryMapWithSql(String sql);

	/**
	 * 执行sql语句返回list<map>结构 带参数
	 *
	 * @param sql
	 * @param args
	 * @return List<?>
	 */
	public List<?> queryListWithSql(String sql, Map<String, Object> args);

	/**
	 * 执行sql语句返回list<map>结构 带参数(分页)
	 *
	 * @param sql
	 * @param args
	 * @return List<?>
	 */
	public List<?> queryListWithSql(String sql, Map<String, Object> args, int page, int rows);

	/**
	 * 执行sql语句返回map结构 带参数
	 *
	 * @param sql
	 * @param args
	 * @return Map<?,?>
	 */
	public Map<?, ?> queryMapWithSql(String sql, Map<String, Object> args);

	/**
	 * 执行修改操作
	 *
	 * @param sql
	 * @return int
	 */
	public int executeSqlUpdate(String sql);

	/**
	 * 执行修改操作 带参数
	 *
	 * @param sql
	 * @param args
	 * @return int
	 */
	public int executeSqlUpdate(String sql, Map<String, Object> args);

	/**
	 * 执行sql语句返回list<map>结构 带参数
	 *
	 * @param sql
	 * @param args
	 * @return List<?>
	 */
	public List<?> queryListWithSql(String sql, Object[] args);

	/**
	 * 执行sql语句返回map结构 带参数
	 *
	 * @param sql
	 * @param args
	 * @return Map<?,?>
	 */
	public Map<?, ?> queryMapWithSql(String sql, Object[] args);

	/**
	 * 执行修改操作 带参数
	 *
	 * @param sql
	 * @param args
	 * @return int
	 */
	public int executeSqlUpdate(String sql, Object[] args);

	/**
	 * 
	 * 根据class生成Hql 执行
	 * 
	 * @param clazz
	 * @return List<?>
	 * 
	 */
	public List<?> queryListWithHql(Class clazz);

	/**
	 *
	 * 根据class生成Hql 执行
	 * 
	 * @param clazz
	 * @param property
	 * @param value
	 * @return List<?>
	 *
	 */
	public List<?> queryListWithHql(Class clazz, String property, Object value);

	/**
	 *
	 * 根据class生成Hql执行 (可强转为需要的对象)
	 * 
	 * @param clazz
	 * @param property
	 * @param value
	 * @return Object
	 *
	 */
	public Object queryMapWithHql(Class clazz, String property, Object value);

	/**
	 *
	 * 根据Hql语句查询
	 * 
	 * @param hql
	 * @author Caratacus
	 * @return List<?>
	 *
	 */
	public List<?> queryListWithHql(String hql);

	/**
	 *
	 * 根据Hql语句查询
	 *
	 * @param hql
	 * @param page
	 * @param rows
	 * @return List<?>
	 *
	 */
	public List<?> queryListWithHql(String hql, int page, int rows);

	/**
	 *
	 * 根据class生成Hql执行
	 * 
	 * @param clazz
	 * @param property
	 * @param value
	 * @return List<?>
	 *
	 */
	public List<?> queryListWithHql(Class clazz, String[] property, Object... value);

	/**
	 *
	 * 根据class生成Hql执行
	 * 
	 * @param clazz
	 * @param map
	 * @return List<?>
	 *
	 */
	public List<?> queryListWithHql(Class clazz, Map<String, Object> map);

	/**
	 * 执行sql语句返回list结构 (分页)
	 * 
	 * @param sql
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<?> queryListWithSql(String sql, int page, int rows);
}
