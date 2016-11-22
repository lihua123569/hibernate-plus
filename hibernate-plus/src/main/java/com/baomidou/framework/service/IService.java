/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Caratacus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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
	 * 获取单个对象
	 *
	 * @param wrapper
	 * @return
	 */
	public V selectOne(Wrapper wrapper);

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
	 * 批量插入(不带事务 慎用)
	 *
	 * @param list
	 */
	public boolean insertBatch(List<V> list);

	/**
	 * 批量修改(不带事务 慎用)
	 *
	 * @param list
	 */
	public boolean updateBatch(List<V> list);

	/**
	 * 批量插入(不带事务 慎用)
	 *
	 * @param list
	 */
	public boolean insertBatch(List<V> list, int size);

	/**
	 * 批量修改(不带事务 慎用)
	 *
	 * @param list
	 */
	public boolean updateBatch(List<V> list, int size);

	/**
	 * 查询数量
	 *
	 * @return
	 */
	public int selectCount();

	/**
	 * 查询数量
	 *
	 * @param property
	 * @param value
	 * @return
	 */
	public int selectCount(String property, Object... value);

	/**
	 * 查询数量
	 *
	 * @param property
	 * @param value
	 * @return
	 */
	public int selectCount(String[] property, Object... value);

	/**
	 * 查询数量
	 *
	 * @param map
	 * @return
	 */
	public int selectCount(Map<String, Object> map);

	/**
	 * 删除
	 *
	 * @param id
	 */
	public void deleteById(Serializable id);

	/**
	 * 查询分页
	 *
	 * @param page
	 * @return
	 */
	public Page<V> selectPage(Page<V> page);

	/**
	 * 查询分页
	 *
	 * @param page
	 * @param property
	 * @param value
	 * @return
	 */
	public Page<V> selectPage(Page<V> page, String property, Object value);

	public Page<V> selectPage(Wrapper wrapper, Page<V> page);

	public <E> Page<E> selectPage(Wrapper wrapper, Class<E> clazz, Page<E> page);

	public List<V> selectList(Wrapper wrapper);

	public <E> List<E> selectList(Wrapper wrapper, Class<E> clazz);

	public int selectCount(Wrapper wrapper);

	/**
	 * <p>
	 * 根据 entity 条件，删除记录
	 * </p>
	 *
	 * @param wrapper
	 *            实体包装类 {@link Wrapper}
	 * @return boolean
	 */
	boolean delete(Wrapper wrapper);

	/**
	 * <p>
	 * 根据 whereEntity 条件，更新记录
	 * </p>
	 *
	 * @param setMap
	 *            实体对象
	 * @param wrapper
	 *            实体包装类 {@link Wrapper}
	 * @return boolean
	 */
	boolean update(Map<String, Object> setMap, Wrapper wrapper);

}
