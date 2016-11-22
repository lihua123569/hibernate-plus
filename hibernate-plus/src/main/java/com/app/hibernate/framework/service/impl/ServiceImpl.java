package com.app.hibernate.framework.service.impl;

import com.app.hibernate.framework.entity.PrimaryKey;
import com.app.hibernate.framework.service.DaoService;
import com.app.hibernate.framework.service.IService;
import com.app.hibernate.persist.converter.BeanConverter;
import com.app.hibernate.persist.dao.IDao;
import com.app.hibernate.persist.page.BasePage;
import com.app.hibernate.persist.page.Page;
import com.app.hibernate.persist.query.Wrapper;
import com.app.hibernate.persist.utils.ReflectionKit;
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
    @Autowired
    protected DaoService daoService;

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
		return baseDao.get(tClass, id) == null ? null : baseDao.get(tClass, id).convert(vClass);
	}

	@Override
	public V get(String hql) {
		return baseDao.get(hql) == null ? null : baseDao.get(hql).convert(vClass);
	}

	@Override
	public V get(String hql, Map<String, Object> params) {
		return baseDao.get(hql, params) == null ? null : baseDao.get(hql, params).convert(vClass);
	}

	@Override
	public List<V> query(String hql) {
		return BeanConverter.convert(vClass, baseDao.query(hql));
	}

	@Override
	public List<V> query(String hql, Map<String, Object> params) {
		return BeanConverter.convert(vClass, baseDao.query(hql, params));
	}

	@Override
	public List<V> query(String hql, int page, int rows) {
		return BeanConverter.convert(vClass, baseDao.query(hql, page, rows));
	}

	@Override
	public List<V> query(String hql, Map<String, Object> params, int page, int rows) {
		return BeanConverter.convert(vClass, baseDao.query(hql, params, page, rows));
	}

	@Override
	public List<V> query() {
		return BeanConverter.convert(vClass, baseDao.query(tClass));
	}

	@Override
	public List<V> query(int page, int rows) {
		return BeanConverter.convert(vClass, baseDao.query(tClass, page, rows));
	}

	@Override
	public List<V> query(int page, int rows, String property, Object value) {
		return BeanConverter.convert(vClass, baseDao.query(tClass, page, rows, property, value));
	}

	@Override
	public List<V> query(int page, int rows, String[] property, Object... value) {
		return BeanConverter.convert(vClass, baseDao.query(tClass, page, rows, property, value));
	}

	@Override
	public List<V> query(int page, int rows, Map<String, Object> map) {
		return BeanConverter.convert(vClass, baseDao.query(tClass, page, rows, map));
	}

	@Override
	public List<V> query(String property, Object value) {
		return BeanConverter.convert(vClass, baseDao.query(tClass, property, value));
	}

	@Override
	public List<V> query(String[] property, Object... value) {
		return BeanConverter.convert(vClass, baseDao.query(tClass, property, value));
	}

	@Override
	public List<V> query(Map<String, Object> map) {
		return BeanConverter.convert(vClass, baseDao.query(tClass, map));
	}

	@Override
	public List<V> queryByOrder(String order) {
		return BeanConverter.convert(vClass, baseDao.query(tClass, order));
	}

	@Override
	public List<V> queryByOrder(String order, int page, int rows) {
		return BeanConverter.convert(vClass, baseDao.query(tClass, order, page, rows));
	}

	@Override
	public List<V> query(String order, String property, Object value) {
		return BeanConverter.convert(vClass, baseDao.query(tClass, order, property, value));
	}

	@Override
	public List<V> query(String order, String[] property, Object... value) {
		return BeanConverter.convert(vClass, baseDao.query(tClass, order, property, value));
	}

	@Override
	public List<V> query(Map<String, Object> map, String order) {
		return BeanConverter.convert(vClass, baseDao.query(tClass, map, order));
	}

	@Override
	public List<V> query(int page, int rows, String order, String property, Object value) {
		return BeanConverter.convert(vClass, baseDao.query(tClass, page, rows, order, property, value));
	}

	@Override
	public List<V> query(int page, int rows, String order, String[] property, Object... value) {
		return BeanConverter.convert(vClass, baseDao.query(tClass, page, rows, order, property, value));

	}

	@Override
	public List<V> query(int page, int rows, Map<String, Object> map, String order) {
		return BeanConverter.convert(vClass, baseDao.query(tClass, page, rows, map, order));
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
		return baseDao.count(tClass);
	}

	@Override
	public long count(String property, Object... value) {
		return baseDao.count(tClass, property, value);
	}

	@Override
	public long count(String[] property, Object... value) {
		return baseDao.count(tClass, property, value);
	}

	@Override
	public long count(Map<String, Object> map) {
		return baseDao.count(tClass, map);
	}

	@Override
	public void delete(Serializable id) {
		baseDao.delete(baseDao.get(tClass, id));
	}

	@Override
	public BasePage<V> findAllPage(BasePage<V> page) {
		List<V> rows = BeanConverter.convert(vClass, baseDao.query(tClass));
		page.setRows(rows);
		return page;
	}

	@Override
	public BasePage<V> findPage(BasePage<V> page) {
		List<V> rows = BeanConverter.convert(vClass, baseDao.query(tClass, page.getFirst(), page.getPageSize()));
		page.setRows(rows);
		return page;
	}

	@Override
	public BasePage<V> findPage(BasePage<V> page, String property, Object value) {
		List<V> rows = BeanConverter.convert(vClass, baseDao.query(tClass, page.getFirst(), page.getPageSize(), property, value));
		page.setRows(rows);
		return page;
	}

	@Override
	public BasePage<V> findPage(BasePage<V> page, String[] property, Object... value) {
		List<V> rows = BeanConverter.convert(vClass, baseDao.query(tClass, page.getFirst(), page.getPageSize(), property, value));
		page.setRows(rows);
		return page;
	}

	@Override
	public BasePage<V> findPage(BasePage<V> page, Map<String, Object> map) {
		List<V> rows = BeanConverter.convert(vClass, baseDao.query(tClass, page.getFirst(), page.getPageSize(), map));
		page.setRows(rows);
		return page;
	}

	public Page<?> queryListWithSql(Wrapper wrapper, Page page) {
		return baseDao.queryListWithSql(tClass, wrapper, page);
	}

	public List<?> queryListWithSql(Wrapper wrapper) {
		return baseDao.queryListWithSql(tClass, wrapper);
	}

	public long queryCountWithSql(Wrapper wrapper) {
		return baseDao.queryCountWithSql(tClass, wrapper);
	}

}
