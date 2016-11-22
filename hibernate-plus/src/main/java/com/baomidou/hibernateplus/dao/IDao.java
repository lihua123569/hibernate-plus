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
public interface IDao<T, V> {
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
	 * 删除方法
	 *
	 * @param wrapper
	 */
	public int delete(Wrapper wrapper);

	/**
	 * 修改方法
	 *
	 * @param o
	 */
	public void update(T o);

	/**
	 * 修改方法
	 *
	 * @param setMap
	 * @param wrapper
	 */
	public int update(Map<String, Object> setMap, Wrapper wrapper);

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
	 * 批量添加
	 *
	 * @param list
	 * @return
	 */
	public boolean insertBatch(List<T> list, int size);

	/**
	 * 批量修改
	 *
	 * @param list
	 * @return
	 */
	public boolean updateBatch(List<T> list, int size);

	/**
	 * 根据class生成count语句执行
	 *
	 * @return long
	 */
	public int selectCount();

	/**
	 * 根据class生成count语句执行
	 *
	 * @param property
	 * @param value
	 * @return long
	 */
	public int selectCount(String property, Object... value);

	/**
	 * 根据class生成count语句执行
	 *
	 * @param property
	 * @param value
	 * @return long
	 */
	public int selectCount(String[] property, Object... value);

	/**
	 * 根据class生成count语句执行
	 *
	 * @param map
	 * @return long
	 */
	public int selectCount(Map<String, Object> map);

	/**
	 * 查询结果集排序
	 *
	 * @param order
	 * @return List<T>
	 */
	public List<T> queryOrder(String order);

	public <E> Page<E> selectPage(Wrapper wrapper, Class<E> clazz, Page<E> page);

	public <E> List<E> selectList(Wrapper wrapper, Class<E> clazz);

	public int selectCount(Wrapper wrapper);

	/**
	 *
	 * 根据class生成Hql执行 (可强转为需要的对象)
	 *
	 * @param property
	 * @param value
	 * @return Object
	 *
	 */
	public T get(String property, Object value);

}
