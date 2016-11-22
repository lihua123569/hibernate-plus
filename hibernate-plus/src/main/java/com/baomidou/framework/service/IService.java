package com.baomidou.framework.service;

import com.baomidou.hibernateplus.page.BasePage;
import com.baomidou.hibernateplus.page.Page;
import com.baomidou.framework.entity.PrimaryKey;
import com.baomidou.hibernateplus.query.Wrapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * IService
 * </p>
 *
 * @author Caratacus
 * @date 2016-10-23
 */
public interface IService<V extends PrimaryKey> {
	/**
	 * 保存
	 *
	 * @param vo
	 * @return
	 */
	public V save(V vo);

	/**
	 * 删除
	 *
	 * @param vo
	 */
	public void delete(V vo);

	/**
	 * 修改
	 *
	 * @param vo
	 */
	public void update(V vo);

	/**
	 * 保存或修改
	 *
	 * @param vo
	 */
	public void saveOrUpdate(V vo);

	/**
	 * 获取单个对象
	 *
	 * @param id
	 * @return
	 */
	public V get(Serializable id);

	/**
	 * 获取单个对象
	 *
	 * @param hql
	 * @return
	 */
	public V get(String hql);

	/**
	 * 获取单个对象
	 *
	 * @param hql
	 * @param params
	 * @return
	 */
	public V get(String hql, Map<String, Object> params);

	/**
	 * 查询
	 *
	 * @param hql
	 * @return
	 */
	public List<V> query(String hql);

	/**
	 * 查询
	 *
	 * @param hql
	 * @param params
	 * @return
	 */
	public List<V> query(String hql, Map<String, Object> params);

	/**
	 * 查询
	 *
	 * @param hql
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<V> query(String hql, int page, int rows);

	/**
	 * 查询
	 *
	 * @param hql
	 * @param params
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<V> query(String hql, Map<String, Object> params, int page, int rows);

	/**
	 * 查询
	 *
	 * @return
	 */
	public List<V> query();

	/**
	 * 查询(分页)
	 *
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<V> query(int page, int rows);

	/**
	 * 查询(分页)
	 *
	 * @param page
	 * @param rows
	 * @param property
	 * @param value
	 * @return
	 */
	public List<V> query(int page, int rows, String property, Object value);

	/**
	 * 查询(分页)
	 *
	 * @param page
	 * @param rows
	 * @param property
	 * @param value
	 * @return
	 */
	public List<V> query(int page, int rows, String[] property, Object... value);

	/**
	 * 查询(分页)
	 *
	 * @param page
	 * @param rows
	 * @param map
	 * @return
	 */
	public List<V> query(int page, int rows, Map<String, Object> map);

	/**
	 * 查询
	 *
	 * @param property
	 * @param value
	 * @return
	 */
	public List<V> query(String property, Object value);

	/**
	 * 查询
	 *
	 * @param property
	 * @param value
	 * @return
	 */
	public List<V> query(String[] property, Object... value);

	/**
	 * 查询
	 *
	 * @param map
	 * @return
	 */
	public List<V> query(Map<String, Object> map);

	/**
	 * 查询排序
	 *
	 * @param order
	 * @return
	 */
	public List<V> queryByOrder(String order);

	/**
	 * 查询排序(分页)
	 *
	 * @param order
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<V> queryByOrder(String order, int page, int rows);

	/**
	 * 查询排序
	 *
	 * @param order
	 * @param property
	 * @param value
	 * @return
	 */
	public List<V> query(String order, String property, Object value);

	/**
	 * 查询排序
	 *
	 * @param order
	 * @param property
	 * @param value
	 * @return
	 */
	public List<V> query(String order, String[] property, Object... value);

	/**
	 * 查询排序
	 *
	 * @param map
	 * @param order
	 * @return
	 */
	public List<V> query(Map<String, Object> map, String order);

	/**
	 * 查询排序(分页)
	 *
	 * @param page
	 * @param rows
	 * @param order
	 * @param property
	 * @param value
	 * @return
	 */
	public List<V> query(int page, int rows, String order, String property, Object value);

	/**
	 * 查询排序(分页)
	 *
	 * @param page
	 * @param rows
	 * @param order
	 * @param property
	 * @param value
	 * @return
	 */
	public List<V> query(int page, int rows, String order, String[] property, Object... value);

	/**
	 * 查询排序(分页)
	 *
	 * @param page
	 * @param rows
	 * @param map
	 * @param order
	 * @return
	 */
	public List<V> query(int page, int rows, Map<String, Object> map, String order);

	/**
	 * 批量插入(不带事务 慎用)
	 *
	 * @param list
	 */
	public void insertWithBatch(List<V> list);

	/**
	 * 批量修改(不带事务 慎用)
	 *
	 * @param list
	 */
	public void updateWithBatch(List<V> list);

	/**
	 * 查询数量
	 *
	 * @return
	 */
	public long count();

	/**
	 * 查询数量
	 *
	 * @param property
	 * @param value
	 * @return
	 */
	public long count(String property, Object... value);

	/**
	 * 查询数量
	 *
	 * @param property
	 * @param value
	 * @return
	 */
	public long count(String[] property, Object... value);

	/**
	 * 查询数量
	 *
	 * @param map
	 * @return
	 */
	public long count(Map<String, Object> map);

	/**
	 * 删除
	 *
	 * @param id
	 */
	public void delete(Serializable id);

	/**
	 * 查询分页
	 *
	 * @param page
	 * @return
	 */
	public BasePage<V> findAllPage(BasePage<V> page);

	/**
	 * 查询分页
	 *
	 * @param page
	 * @return
	 */
	public BasePage<V> findPage(BasePage<V> page);

	/**
	 * 查询分页
	 *
	 * @param page
	 * @param property
	 * @param value
	 * @return
	 */
	public BasePage<V> findPage(BasePage<V> page, String property, Object value);

	/**
	 * 查询分页
	 *
	 * @param page
	 * @param property
	 * @param value
	 * @return
	 */
	public BasePage<V> findPage(BasePage<V> page, String[] property, Object... value);

	/**
	 * 查询分页
	 *
	 * @param page
	 * @param map
	 * @return
	 */
	public BasePage<V> findPage(BasePage<V> page, Map<String, Object> map);

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
