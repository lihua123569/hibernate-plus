package com.baomidou.framework.service;

import com.baomidou.framework.entity.PrimaryKey;
import com.baomidou.hibernateplus.page.Page;
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
	 *
	 * 根据class生成Hql执行 (可强转为需要的对象)
	 *
	 * @param property
	 * @param value
	 * @return Object
	 *
	 */
	public V get(String property, Object value);
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
	public List<V> queryOrder(String order);

	/**
	 * 查询排序(分页)
	 *
	 * @param order
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<V> queryOrder(String order, int page, int rows);

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
	public long selectCount();

	/**
	 * 查询数量
	 *
	 * @param property
	 * @param value
	 * @return
	 */
	public long selectCount(String property, Object... value);

	/**
	 * 查询数量
	 *
	 * @param property
	 * @param value
	 * @return
	 */
	public long selectCount(String[] property, Object... value);

	/**
	 * 查询数量
	 *
	 * @param map
	 * @return
	 */
	public long selectCount(Map<String, Object> map);

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
	public Page<V> findAllPage(Page<V> page);

	/**
	 * 查询分页
	 *
	 * @param page
	 * @return
	 */
	public Page<V> findPage(Page<V> page);

	/**
	 * 查询分页
	 *
	 * @param page
	 * @param property
	 * @param value
	 * @return
	 */
	public Page<V> findPage(Page<V> page, String property, Object value);

	/**
	 * 查询分页
	 *
	 * @param page
	 * @param property
	 * @param value
	 * @return
	 */
	public Page<V> findPage(Page<V> page, String[] property, Object... value);

	/**
	 * 查询分页
	 *
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<V> findPage(Page<V> page, Map<String, Object> map);

	public Page<?> queryListWithSql(Wrapper wrapper, Page page);

	public List<?> queryListWithSql(Wrapper wrapper);

	public long selectCount(Wrapper wrapper);

}
