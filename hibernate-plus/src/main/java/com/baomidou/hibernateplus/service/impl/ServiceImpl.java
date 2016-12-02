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
package com.baomidou.hibernateplus.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.hibernateplus.converter.BeanConverter;
import com.baomidou.hibernateplus.dao.IDao;
import com.baomidou.hibernateplus.entity.Convert;
import com.baomidou.hibernateplus.entity.page.Page;
import com.baomidou.hibernateplus.query.Condition;
import com.baomidou.hibernateplus.query.Wrapper;
import com.baomidou.hibernateplus.service.IService;
import com.baomidou.hibernateplus.utils.CollectionUtils;
import com.baomidou.hibernateplus.utils.ReflectionKit;

/**
 * <p>
 * IService 实现类（ 泛型：T 是数据库实体TO，V 对应数据库实体的VO ）
 * </p>
 *
 * @author Caratacus
 * @date 2016-11-23
 */
public class ServiceImpl<T extends Convert, V extends Convert> implements IService<V> {

	private static final Logger logger = Logger.getLogger(ServiceImpl.class);

	@Autowired
	protected IDao<T> baseDao;

	@Override
	public V get(Serializable id) {
		return baseDao.get(id) == null ? null : baseDao.get(id).convert(voClass());
	}

	@Override
	public V get(String property, Object value) {
		return baseDao.get(property, value) == null ? null : baseDao.get(property, value).convert(voClass());
	}

	@Override
	public V save(V vo) {
		return baseDao.save(vo.convert(toClass())).convert(voClass());
	}

	@Override
	public void saveOrUpdate(V vo) {
		baseDao.saveOrUpdate(vo.convert(toClass()));
	}

	@Override
	public void update(V vo) {
		baseDao.update(vo.convert(toClass()));
	}

	@Override
	public boolean update(Map<String, Object> setMap, Wrapper wrapper) {
		return retBool(baseDao.update(setMap, wrapper));
	}

	@Override
	public void delete(V vo) {
		baseDao.delete(vo.convert(toClass()));
	}

	@Override
	public boolean delete(Wrapper wrapper) {
		return retBool(baseDao.delete(wrapper));
	}

	@Override
	public boolean insertBatch(List<V> list) {
		return baseDao.insertBatch(BeanConverter.convert(toClass(), list), 30);
	}

	@Override
	public boolean insertBatch(List<V> list, int size) {
		return baseDao.insertBatch(BeanConverter.convert(toClass(), list), size);
	}

	@Override
	public boolean updateBatch(List<V> list) {
		return baseDao.updateBatch(BeanConverter.convert(toClass(), list), 30);
	}

	@Override
	public boolean updateBatch(List<V> list, int size) {
		return baseDao.updateBatch(BeanConverter.convert(toClass(), list), size);
	}

	@Override
	public V selectOne(Wrapper wrapper) {
		List<V> list = selectList(wrapper);
		if (CollectionUtils.isNotEmpty(list)) {
			int size = list.size();
			if (size > 1) {
				logger.warn(String.format("Warn: selectOne Method There are  %s results.", size));
			}
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<V> selectList(Wrapper wrapper) {
		return BeanConverter.convert(voClass(), baseDao.selectList(wrapper));
	}

	@Override
	public List<Map<String, Object>> selectMaps(Wrapper wrapper) {
		return baseDao.selectMaps(wrapper);
	}

	@Override
	public int selectCount() {
		return baseDao.selectCount();
	}

	@Override
	public int selectCount(String property, Object... value) {
		return baseDao.selectCount(property, value);
	}

	@Override
	public int selectCount(String[] property, Object... value) {
		return baseDao.selectCount(property, value);
	}

	@Override
	public int selectCount(Map<String, Object> map) {
		return baseDao.selectCount(map);
	}

	@Override
	public int selectCount(Wrapper wrapper) {
		return baseDao.selectCount(wrapper);
	}

	@Override
	public Page<V> selectPage(Page<V> page) {
		return selectPage(Condition.instance(), page);
	}

	@Override
	public Page<V> selectPage(Page<V> page, String property, Object value) {
		return selectPage(Condition.instance().where(String.format("%s = {0}", property), value), page);
	}

	@Override
	public Page<V> selectPage(Wrapper wrapper, Page<V> page) {
		page = baseDao.selectPage(wrapper, page);
		page.setRecords(BeanConverter.convert(voClass(), page.getRecords()));
		return page;
	}

	@Override
	public Page<Map<String, Object>> selectMapPage(Wrapper wrapper, Page<Map<String, Object>> page) {
		return baseDao.selectMapPage(wrapper, page);
	}

	/**
	 * 判断数据库操作是否成功
	 *
	 * @param result
	 *            数据库操作返回影响条数
	 * @return boolean
	 */
	protected boolean retBool(int result) {
		return result >= 1;
	}

	/**
	 * 获取toClass
	 *
	 * @return
	 */
	protected Class<T> toClass() {
		return ReflectionKit.getSuperClassGenricType(getClass(), 0);
	}

	/**
	 * 获取voClass
	 *
	 * @return
	 */
	protected Class<V> voClass() {
		return ReflectionKit.getSuperClassGenricType(getClass(), 1);
	}

}
