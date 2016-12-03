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

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.baomidou.hibernateplus.condition.wrapper.Wrapper;
import com.baomidou.hibernateplus.entity.page.Page;

/**
 * <p>
 * IDao接口
 * </p>
 *
 * @author Caratacus
 * @date 2016-11-23
 */
public interface IDao<T> {
	/**
	 * 根据id获取对象
	 *
	 * @param id
	 * @return
	 */
	public T get(Serializable id);

	/**
	 * 保存方法
	 *
	 * @param o
	 * @return
	 */
	public T save(T o);

	/**
	 * 保存/修改方法
	 *
	 * @param o
	 */
	public T saveOrUpdate(T o);

	/**
	 * 修改方法
	 *
	 * @param o
	 */
	public T update(T o);

	/**
	 * 修改方法
	 *
	 * @param wrapper
	 * @return
	 */
	public int update(Wrapper wrapper);

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
	 * @return
	 */
	public int delete(Wrapper wrapper);

	/**
	 * 批量添加
	 *
	 * @param list
	 * @param size
	 * @return
	 */
	public boolean insertBatch(List<T> list, int size);

	/**
	 * 批量修改
	 *
	 * @param list
	 * @param size
	 * @return
	 */
	public boolean updateBatch(List<T> list, int size);

	/**
	 * 批量修改
	 *
	 * @param list
	 * @param size
	 * @return
	 */
	public boolean saveOrUpdateBatch(List<T> list, int size);

	/**
	 * 查询List<Map<String,Object>>结果集
	 *
	 * @param wrapper
	 * @return
	 */
	public List<Map<String, Object>> selectMaps(Wrapper wrapper);

	/**
	 * 查询列表
	 *
	 * @param wrapper
	 * @param <T>
	 * @return
	 */
	public <T> List<T> selectList(Wrapper wrapper);

	/**
	 * 查询数量
	 *
	 * @param wrapper
	 * @return
	 */
	public int selectCount(Wrapper wrapper);

	/**
	 * 查询分页
	 * 
	 * @param wrapper
	 * @param page
	 * @return
	 */
	public Page<Map<String, Object>> selectMapPage(Wrapper wrapper, Page<Map<String, Object>> page);

	/**
	 * 查询分页
	 *
	 * @param wrapper
	 * @param page
	 * @return
	 */
	public Page selectPage(Wrapper wrapper, Page page);

}