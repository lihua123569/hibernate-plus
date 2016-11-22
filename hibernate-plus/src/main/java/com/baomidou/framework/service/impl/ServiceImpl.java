package com.baomidou.framework.service.impl;

import com.baomidou.framework.entity.PrimaryKey;
import com.baomidou.framework.service.IService;
import com.baomidou.hibernateplus.converter.BeanConverter;
import com.baomidou.hibernateplus.dao.IDao;
import com.baomidou.hibernateplus.page.Page;
import com.baomidou.hibernateplus.query.Wrapper;
import com.baomidou.hibernateplus.utils.ReflectionKit;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * IService 实现类（ 泛型：T 是数据库实体TO，V 对应数据库实体的VO ）
 * </p>
 *
 * @author Caratacus
 * @date 2016-10-23
 */
public class ServiceImpl<T extends PrimaryKey, V extends PrimaryKey> implements IService<V> {
	// 反射TO泛型
	protected Class<T> tClass = ReflectionKit.getSuperClassGenricType(getClass(), 0);
	// 反射VO泛型
	protected Class<V> vClass = ReflectionKit.getSuperClassGenricType(getClass(), 1);
	@Autowired
	protected IDao<T> baseDao;

	@Override
	public V save(V vo) {
		return baseDao.save(vo.convert(tClass)).convert(vClass);
	}

	@Override
	public void delete(V vo) {
		baseDao.delete(vo.convert(tClass));
	}

	@Override
	public void update(V vo) {
		baseDao.update(vo.convert(tClass));
	}

	@Override
	public void saveOrUpdate(V vo) {
		baseDao.saveOrUpdate(vo.convert(tClass));
	}

	@Override
	public V get(Serializable id) {
		return baseDao.get(id) == null ? null : baseDao.get(id).convert(vClass);
	}

	@Override
	public List<V> query() {
		return BeanConverter.convert(vClass, baseDao.query());
	}

	@Override
	public List<V> query(int page, int rows) {
		return BeanConverter.convert(vClass, baseDao.query(page, rows));
	}

	@Override
	public List<V> query(int page, int rows, String property, Object value) {
		return BeanConverter.convert(vClass, baseDao.query(page, rows, property, value));
	}

	@Override
	public List<V> query(int page, int rows, String[] property, Object... value) {
		return BeanConverter.convert(vClass, baseDao.query(page, rows, property, value));
	}

	@Override
	public List<V> query(int page, int rows, Map<String, Object> map) {
		return BeanConverter.convert(vClass, baseDao.query(page, rows, map));
	}

	@Override
	public List<V> query(String property, Object value) {
		return BeanConverter.convert(vClass, baseDao.query(property, value));
	}

	@Override
	public List<V> query(String[] property, Object... value) {
		return BeanConverter.convert(vClass, baseDao.query(property, value));
	}

	@Override
	public List<V> query(Map<String, Object> map) {
		return BeanConverter.convert(vClass, baseDao.query(map));
	}

	@Override
	public List<V> queryOrder(String order) {
		return BeanConverter.convert(vClass, baseDao.queryOrder(order));
	}

	@Override
	public List<V> queryOrder(String order, int page, int rows) {
		return BeanConverter.convert(vClass, baseDao.queryOrder(order, page, rows));
	}

	@Override
	public List<V> query(String order, String property, Object value) {
		return BeanConverter.convert(vClass, baseDao.query(order, property, value));
	}

	@Override
	public List<V> query(String order, String[] property, Object... value) {
		return BeanConverter.convert(vClass, baseDao.query(order, property, value));
	}

	@Override
	public List<V> query(Map<String, Object> map, String order) {
		return BeanConverter.convert(vClass, baseDao.query(map, order));
	}

	@Override
	public List<V> query(int page, int rows, String order, String property, Object value) {
		return BeanConverter.convert(vClass, baseDao.query(page, rows, order, property, value));
	}

	@Override
	public List<V> query(int page, int rows, String order, String[] property, Object... value) {
		return BeanConverter.convert(vClass, baseDao.query(page, rows, order, property, value));

	}

	@Override
	public List<V> query(int page, int rows, Map<String, Object> map, String order) {
		return BeanConverter.convert(vClass, baseDao.query(page, rows, map, order));
	}

	@Override
	public void insertWithBatch(List<V> list) {
		baseDao.insertWithBatch(BeanConverter.convert(tClass, list));
	}

	@Override
	public void updateWithBatch(List<V> list) {
		baseDao.updateWithBatch(BeanConverter.convert(tClass, list));
	}

	@Override
	public long count() {
		return baseDao.count();
	}

	@Override
	public long count(String property, Object... value) {
		return baseDao.count(property, value);
	}

	@Override
	public long count(String[] property, Object... value) {
		return baseDao.count(property, value);
	}

	@Override
	public long count(Map<String, Object> map) {
		return baseDao.count(map);
	}

	@Override
	public void delete(Serializable id) {
		baseDao.delete(baseDao.get(id));
	}

	@Override
	public Page<V> findAllPage(Page<V> page) {
		List<V> rows = BeanConverter.convert(vClass, baseDao.query());
		page.setRecords(rows);
		return page;
	}

	@Override
	public Page<V> findPage(Page<V> page) {
		List<V> rows = BeanConverter.convert(vClass, baseDao.query(page.getCurrent(), page.getSize()));
		page.setRecords(rows);
		return page;
	}

	@Override
	public Page<V> findPage(Page<V> page, String property, Object value) {
		List<V> rows = BeanConverter.convert(vClass, baseDao.query(page.getCurrent(), page.getSize(), property, value));
		page.setRecords(rows);
		return page;
	}

	@Override
	public Page<V> findPage(Page<V> page, String[] property, Object... value) {
		List<V> rows = BeanConverter.convert(vClass, baseDao.query(page.getCurrent(), page.getSize(), property, value));
		page.setRecords(rows);
		return page;
	}

	@Override
	public Page<V> findPage(Page<V> page, Map<String, Object> map) {
		List<V> rows = BeanConverter.convert(vClass, baseDao.query(page.getCurrent(), page.getSize(), map));
		page.setRecords(rows);
		return page;
	}

	public Page<?> queryListWithSql(Wrapper wrapper, Page page) {
		return baseDao.queryListWithSql(wrapper, page);
	}

	public List<?> queryListWithSql(Wrapper wrapper) {
		return baseDao.queryListWithSql(wrapper);
	}

	public long queryCountWithSql(Wrapper wrapper) {
		return baseDao.queryCountWithSql(wrapper);
	}

	@Override
	public List<?> queryListWithHql() {
		return baseDao.queryListWithHql();
	}

	@Override
	public List<?> queryListWithHql(String property, Object value) {
		return baseDao.queryListWithHql(property, value);
	}

	@Override
	public Object queryMapWithHql(String property, Object value) {
		return baseDao.queryMapWithHql(property, value);
	}

	@Override
	public List<?> queryListWithHql(String[] property, Object... value) {
		return baseDao.queryListWithHql(property, value);

	}

	@Override
	public List<?> queryListWithHql(Map<String, Object> map) {
		return baseDao.queryListWithHql(map);

	}

}
