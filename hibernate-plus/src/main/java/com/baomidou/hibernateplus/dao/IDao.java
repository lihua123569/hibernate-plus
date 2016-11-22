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

}
