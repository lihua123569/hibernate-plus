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
package com.baomidou.hibernateplus.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.baomidou.hibernateplus.condition.DeleteWrapper;
import com.baomidou.hibernateplus.condition.SelectWrapper;
import com.baomidou.hibernateplus.condition.UpdateWrapper;
import com.baomidou.hibernateplus.entity.Convert;
import com.baomidou.hibernateplus.entity.page.Page;
import com.baomidou.hibernateplus.condition.wrapper.Wrapper;
import net.sf.jsqlparser.statement.select.Select;

/**
 * <p>
 * IService接口类
 * </p>
 *
 * @author Caratacus
 * @date 2016-11-23
 */
public interface IService<V extends Convert> {

	/**
	 * 获取单个对象
	 *
	 * @param id
	 * @return
	 */
	public V get(Serializable id);

	/**
	 * 保存
	 *
	 * @param vo
	 * @return
	 */
	public V save(V vo);

	/**
	 * 保存或修改
	 *
	 * @param vo
	 */
	public void saveOrUpdate(V vo);

	/**
	 * 修改
	 *
	 * @param vo
	 */
	public void update(V vo);

	/**
	 * 根据UpdateWrapper修改
	 *
	 * @param updateWrapper
	 * @return
	 */
	boolean update(UpdateWrapper updateWrapper);

	/**
	 * 删除
	 *
	 * @param vo
	 */
	public void delete(V vo);

	/**
	 * 根据Wrapper删除
	 *
	 * @param deleteWrapper
	 * @return
	 */
	boolean delete(DeleteWrapper deleteWrapper);

	/**
	 * 批量插入
	 * 
	 * @param list
	 * @return
	 */
	public boolean insertBatch(List<V> list);

	/**
	 * 批量插入
	 *
	 * @param list
	 * @param size
	 * @return
	 */
	public boolean insertBatch(List<V> list, int size);

	/**
	 * 批量修改
	 *
	 * @param list
	 * @return
	 */
	public boolean updateBatch(List<V> list);

	/**
	 * 批量修改
	 *
	 * @param list
	 * @param size
	 * @return
	 */
	public boolean updateBatch(List<V> list, int size);

	/**
	 * 获取单个对象
	 *
	 * @param selectWrapper
	 * @return
	 */
	public V selectOne(SelectWrapper selectWrapper);

	/**
	 * 查询列表
	 *
	 * @param selectWrapper
	 * @return
	 */
	public List<V> selectList(SelectWrapper selectWrapper);

	/**
	 * 查询列表
	 *
	 * @param selectWrapper
	 * @return
	 */
	public List<Map<String, Object>> selectMaps(SelectWrapper selectWrapper);

	/**
	 * 查询数量
	 *
	 * @param selectWrapper
	 * @return
	 */
	public int selectCount(SelectWrapper selectWrapper);

	/**
	 * 查询分页
	 * 
	 * @param selectWrapper
	 * @param page
	 * @return
	 */
	public Page<V> selectPage(SelectWrapper selectWrapper, Page<V> page);

	/**
	 * 查询分页
	 * 
	 * @param selectWrapper
	 * @param page
	 * @return
	 */
	public Page<Map<String, Object>> selectMapPage(SelectWrapper selectWrapper, Page<Map<String, Object>> page);

}
