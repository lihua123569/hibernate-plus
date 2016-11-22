package com.baomidou.hibernateplus.dao;

import com.baomidou.hibernateplus.page.Page;
import com.baomidou.hibernateplus.query.Wrapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * IDao接口
 * </p>
 *
 * @author Caratacus
 * @date 2016-10-23
 */
public interface IDao<T> {
	/**
	 * 保存方法
	 *
	 * @param o
	 * @return T
	 */
	public T save(T o);

	/**
	 * 删除方法
	 *
	 * @param o
	 */
	public void delete(T o);

	/**
	 * 修改方法
	 *
	 * @param o
	 */
	public void update(T o);

	/**
	 * 保存/修改方法
	 *
	 * @param o
	 */
	public void saveOrUpdate(T o);

	/**
	 * 根据id获取对象
	 *
	 * @param id
	 * @return T
	 */
	public T get(Serializable id);

	/**
	 * 根据hql获取对象
	 *
	 * @param hql
	 * @return T
	 */
	public T get(String hql);

	/**
	 * 根据hql获取对象
	 *
	 * @param hql
	 * @param params
	 * @return T
	 */
	public T get(String hql, Map<String, Object> params);

	/**
	 * 查询结果集
	 *
	 * @param hql
	 * @return List<T>
	 */
	public List<T> query(String hql);

	/**
	 * 查询结果集
	 *
	 * @param hql
	 * @param params
	 * @return List<T>
	 */
	public List<T> query(String hql, Map<String, Object> params);

	/**
	 * 查询结果集
	 *
	 * @param hql
	 * @param page
	 * @param rows
	 * @return List<T>
	 */
	public List<T> query(String hql, int page, int rows);

	/**
	 * 查询结果集
	 *
	 * @param hql
	 * @param params
	 * @param page
	 * @param rows
	 * @return List<T>
	 */
	public List<T> query(String hql, Map<String, Object> params, int page, int rows);

	/**
	 * 批量添加
	 *
	 * @param list
	 * @return
	 */
	public void insertWithBatch(List<T> list);

	/**
	 * 批量修改
	 *
	 * @param list
	 * @return
	 */
	public void updateWithBatch(List<T> list);

	/**
	 * 查询结果集
	 *
	 * @return List<T>
	 */
	public List<T> query();

	/**
	 * 查询结果集
	 *
	 * @param page
	 * @param rows
	 * @return List<T>
	 */
	public List<T> query(int page, int rows);

	/**
	 * 查询结果集
	 *
	 * @param property
	 * @param value
	 * @return List<T>
	 */
	public List<T> query(String property, Object value);

	/**
	 * 查询结果集
	 *
	 * @param property
	 * @param value
	 * @return List<T>
	 */
	public List<T> query(String[] property, Object... value);

	/**
	 * 查询结果集
	 *
	 * @param map
	 * @return List<T>
	 */
	public List<T> query(Map<String, Object> map);

	/**
	 * 查询结果集
	 *
	 * @param page
	 * @param rows
	 * @param property
	 * @param value
	 * @return List<T>
	 */
	public List<T> query(int page, int rows, String property, Object value);

	/**
	 * 查询结果集
	 *
	 * @param page
	 * @param rows
	 * @param property
	 * @param value
	 * @return List<T>
	 */
	public List<T> query(int page, int rows, String[] property, Object... value);

	/**
	 * 查询结果集
	 *
	 * @param page
	 * @param rows
	 * @param map
	 * @return List<T>
	 */
	public List<T> query(int page, int rows, Map<String, Object> map);

	/**
	 * 根据class生成count语句执行
	 *
	 * @return long
	 */
	public long count();

	/**
	 * 根据class生成count语句执行
	 *
	 * @param property
	 * @param value
	 * @return long
	 */
	public long count(String property, Object... value);

	/**
	 * 根据class生成count语句执行
	 *
	 * @param property
	 * @param value
	 * @return long
	 */
	public long count(String[] property, Object... value);

	/**
	 * 根据class生成count语句执行
	 *
	 * @param map
	 * @return long
	 */
	public long count(Map<String, Object> map);

	/**
	 * 查询结果集排序
	 *
	 * @param order
	 * @return List<T>
	 */
	public List<T> queryOrder(String order);

	/**
	 * 查询结果集排序
	 *
	 * @param page
	 * @param rows
	 * @return List<T>
	 */
	public List<T> queryOrder(String order, int page, int rows);

	/**
	 * 查询结果集排序
	 *
	 * @param property
	 * @param value
	 * @return List<T>
	 */
	public List<T> query(String order, String property, Object value);

	/**
	 * 查询结果集排序
	 *
	 * @param property
	 * @param value
	 * @return List<T>
	 */
	public List<T> query(String order, String[] property, Object... value);

	/**
	 * 查询结果集排序
	 *
	 * @param map
	 * @return List<T>
	 */
	public List<T> query(Map<String, Object> map, String order);

	/**
	 * 查询结果集排序
	 *
	 * @param page
	 * @param rows
	 * @param property
	 * @param value
	 * @return List<T>
	 */
	public List<T> query(int page, int rows, String order, String property, Object value);

	/**
	 * 查询结果集排序
	 *
	 * @param page
	 * @param rows
	 * @param property
	 * @param value
	 * @return List<T>
	 */
	public List<T> query(int page, int rows, String order, String[] property, Object... value);

	/**
	 * 查询结果集排序
	 *
	 * @param page
	 * @param rows
	 * @param map
	 * @return List<T>
	 */
	public List<T> query(int page, int rows, Map<String, Object> map, String order);

	public Page<?> queryListWithSql(Wrapper wrapper, Page page);

	public List<?> queryListWithSql(Wrapper wrapper);

	public long queryCountWithSql(Wrapper wrapper);

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
	 * @return List<?>
	 *
	 */
	public List<?> queryListWithHql();

	/**
	 *
	 * 根据class生成Hql 执行
	 *
	 * @param property
	 * @param value
	 * @return List<?>
	 *
	 */
	public List<?> queryListWithHql(String property, Object value);

	/**
	 *
	 * 根据class生成Hql执行 (可强转为需要的对象)
	 *
	 * @param property
	 * @param value
	 * @return Object
	 *
	 */
	public Object queryMapWithHql(String property, Object value);

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
	 * @param property
	 * @param value
	 * @return List<?>
	 *
	 */
	public List<?> queryListWithHql(String[] property, Object... value);

	/**
	 *
	 * 根据class生成Hql执行
	 *
	 * @param map
	 * @return List<?>
	 *
	 */
	public List<?> queryListWithHql(Map<String, Object> map);

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
