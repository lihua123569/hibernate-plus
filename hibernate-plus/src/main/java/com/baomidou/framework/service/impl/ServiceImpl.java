package com.baomidou.framework.service.impl;

import com.baomidou.framework.entity.PrimaryKey;
import com.baomidou.framework.service.IService;
import com.baomidou.hibernateplus.converter.BeanConverter;
import com.baomidou.hibernateplus.dao.IDao;
import com.baomidou.hibernateplus.page.Page;
import com.baomidou.hibernateplus.query.Condition;
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
	protected IDao<T, V> baseDao;

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
	public void delete(Serializable id) {
		baseDao.delete(baseDao.get(id));
	}

	@Override
	public Page<V> selectPage(Page<V> page) {
		return baseDao.selectPage(Condition.instance(), page);
	}

	@Override
	public Page<V> selectPage(Page<V> page, String property, Object value) {
		return selectPage(Condition.instance().where(String.format("%s = {0}", property), value), page);
	}

	public Page<V> selectPage(Wrapper wrapper, Page<V> page) {
		return baseDao.selectPage(wrapper, page);
	}

	public List<?> queryListWithSql(Wrapper wrapper) {
		return baseDao.queryListWithSql(wrapper);
	}

	public int selectCount(Wrapper wrapper) {
		return baseDao.selectCount(wrapper);
	}

	@Override
	public V get(String property, Object value) {
		return baseDao.get(property, value) == null ? null : baseDao.get(property, value).convert(vClass);

	}

}
