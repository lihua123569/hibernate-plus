package com.baomidou.framework.service.impl;

import com.baomidou.framework.entity.PrimaryKey;
import com.baomidou.framework.service.IService;
import com.baomidou.hibernateplus.converter.BeanConverter;
import com.baomidou.hibernateplus.dao.IDao;
import com.baomidou.hibernateplus.page.BasePage;
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
	public List<V> queryByOrder(String order) {
		return BeanConverter.convert(vClass, baseDao.query(order));
	}

	@Override
	public List<V> queryByOrder(String order, int page, int rows) {
		return BeanConverter.convert(vClass, baseDao.query(order, page, rows));
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
	public BasePage<V> findAllPage(BasePage<V> page) {
		List<V> rows = BeanConverter.convert(vClass, baseDao.query());
		page.setRows(rows);
		return page;
	}

	@Override
	public BasePage<V> findPage(BasePage<V> page) {
		List<V> rows = BeanConverter.convert(vClass, baseDao.query(page.getFirst(), page.getPageSize()));
		page.setRows(rows);
		return page;
	}

	@Override
	public BasePage<V> findPage(BasePage<V> page, String property, Object value) {
		List<V> rows = BeanConverter.convert(vClass, baseDao.query(page.getFirst(), page.getPageSize(), property, value));
		page.setRows(rows);
		return page;
	}

	@Override
	public BasePage<V> findPage(BasePage<V> page, String[] property, Object... value) {
		List<V> rows = BeanConverter.convert(vClass, baseDao.query(page.getFirst(), page.getPageSize(), property, value));
		page.setRows(rows);
		return page;
	}

	@Override
	public BasePage<V> findPage(BasePage<V> page, Map<String, Object> map) {
		List<V> rows = BeanConverter.convert(vClass, baseDao.query(page.getFirst(), page.getPageSize(), map));
		page.setRows(rows);
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
	public long queryCountWithHql(String hql) {
		return baseDao.queryCountWithHql(hql);
	}

	@Override
	public long queryCountWithHql(String hql, Map<String, Object> params) {
		return baseDao.queryCountWithHql(hql, params);
	}

	@Override
	public int executeHql(String hql) {
		return baseDao.executeHql(hql);
	}

	@Override
	public int executeHql(String hql, Map<String, Object> params) {
		return baseDao.executeHql(hql, params);

	}

	@Override
	public int executeSql(String sql) {
		return baseDao.executeSql(sql);
	}

	@Override
	public int executeSql(String sql, Map<String, Object> params) {
		return baseDao.executeSql(sql, params);

	}

	@Override
	public long queryCountWithSql(String sql) {
		return baseDao.queryCountWithSql(sql);
	}

	@Override
	public long queryCountWithSql(String sql, Map<String, Object> params) {
		return baseDao.queryCountWithSql(sql, params);
	}

	@Override
	public Map<?, ?> queryMapWithSql(String sql, Map<String, Object> params) {
		return baseDao.queryMapWithSql(sql, params);
	}

	@Override
	public Map<?, ?> queryMapWithSql(String sql) {
		return baseDao.queryMapWithSql(sql);
	}

	@Override
	public List<?> queryListWithSql(String sql) {
		return baseDao.queryListWithSql(sql);
	}

	@Override
	public List<?> queryListWithSql(String sql, int page, int rows) {
		return baseDao.queryListWithSql(sql, page, rows);
	}

	@Override
	public List<?> queryListWithSql(String sql, Map<String, Object> params, int page, int rows) {
		return baseDao.queryListWithSql(sql, params, page, rows);
	}

	@Override
	public List<?> queryListWithSql(String sql, Map<String, Object> params) {
		return baseDao.queryListWithSql(sql, params);
	}

	@Override
	public int executeSqlUpdate(String sql) {
		return baseDao.executeSqlUpdate(sql);
	}

	@Override
	public int executeSqlUpdate(String sql, Map<String, Object> params) {
		return baseDao.executeSqlUpdate(sql, params);
	}

	@Override
	public List<?> queryListWithSql(String sql, Object[] args) {
		return baseDao.queryListWithSql(sql, args);
	}

	@Override
	public Map<?, ?> queryMapWithSql(String sql, Object[] args) {
		return baseDao.queryMapWithSql(sql, args);
	}

	@Override
	public int executeSqlUpdate(String sql, Object[] args) {
		return baseDao.executeSqlUpdate(sql, args);
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

	@Override
	public List<?> queryListWithHql(String hql) {
		return baseDao.queryListWithHql(hql);
	}

	@Override
	public List<?> queryListWithHql(String hql, int page, int rows) {
		return baseDao.queryListWithHql(hql, page, rows);
	}

}
