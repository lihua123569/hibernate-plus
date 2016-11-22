package com.app.hibernate.persist.dao.impl;

import com.app.hibernate.persist.dao.IDao;
import com.app.hibernate.persist.exceptions.AppHibernateException;
import com.app.hibernate.persist.page.CountOptimize;
import com.app.hibernate.persist.page.Page;
import com.app.hibernate.persist.query.Wrapper;
import com.app.hibernate.persist.utils.CollectionUtils;
import com.app.hibernate.persist.utils.HibernateUtils;
import com.app.hibernate.persist.utils.MapUtils;
import com.app.hibernate.persist.utils.SqlUtils;
import com.app.hibernate.persist.utils.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * <p>
 * IDao接口实现类
 * </p>
 *
 * @author Caratacus
 * @date 2016-10-23
 */
@Repository
public class DaoImpl<T> implements IDao<T> {
	protected static final Logger logger = Logger.getLogger("DaoImpl");
    @Autowired
	private SessionFactory sessionFactory;

    @Override
	public T save(T t) {
		if (null == t)
			throw new AppHibernateException("execute Save Fail! Param is Empty !");
		HibernateUtils.getCurrentSession(sessionFactory).save(t);
		return t;
	}

	@Override
	public T get(Class<T> clazz, Serializable id) {
		if (null == id)
			throw new AppHibernateException("execute Get Fail! Param is Empty !");
		return (T) HibernateUtils.getCurrentSession(sessionFactory).get(clazz, id);
	}

	@Override
	public T get(String hql) {
		return (T) get(hql, Collections.EMPTY_MAP);
	}

	@Override
	public T get(String hql, Map<String, Object> params) {
		if (StringUtils.isBlank(hql))
			throw new AppHibernateException("execute Get Fail! Param is Empty !");
		T t = null;
		try {
			Query query = HibernateUtils.getHqlQuery(hql, sessionFactory);
			if (MapUtils.isNotEmpty(params)) {
				for (String key : params.keySet()) {
					Object obj = params.get(key);
					HibernateUtils.setParams(query, key, obj);
				}
			}
			t = (T) query.uniqueResult();
		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return t;

	}

	@Override
	public void delete(T t) {
		if (null == t)
			throw new AppHibernateException("execute Delete! Param is Empty !");
		HibernateUtils.getCurrentSession(sessionFactory).delete(t);
	}

	@Override
	public void update(T t) {
		if (null == t)
			throw new AppHibernateException("execute Update! Param is Empty !");
		HibernateUtils.getCurrentSession(sessionFactory).merge(t);
	}

	@Override
	public void saveOrUpdate(T t) {
		if (null == t)
			throw new AppHibernateException("execute SaveOrUpdate! Param is Empty !");
		HibernateUtils.getCurrentSession(sessionFactory).saveOrUpdate(t);
	}

	@Override
	public List<T> query(String hql) {
		return query(hql, Collections.EMPTY_MAP);
	}

	@Override
	public List<T> query(String hql, Map<String, Object> params) {
		return query(hql, params, 0, 0);
	}

	@Override
	public List<T> query(String hql, Map<String, Object> params, int page, int rows) {
		if (StringUtils.isBlank(hql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		List<T> list = Collections.emptyList();
		try {
			Query query = HibernateUtils.getHqlQuery(hql, sessionFactory);
			setParamMap(params, query);
			HibernateUtils.setPage(page, rows, query);
			list = query.list();
		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return list;

	}

	@Override
	public List<T> query(String hql, int page, int rows) {
		return query(hql, Collections.EMPTY_MAP, page, rows);
	}

	@Override
	public void insertWithBatch(List<T> list) {
		if (CollectionUtils.isEmpty(list))
			throw new AppHibernateException("execute BatchInsert Fail! Param is Empty !");
		Session session = HibernateUtils.getCurrentSession(sessionFactory);
		for (int i = 0; i < list.size(); i++) {
			session.save(list.get(i));
			if (i % 30 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	@Override
	public void updateWithBatch(List<T> list) {
		if (CollectionUtils.isEmpty(list))
			throw new AppHibernateException("execute BatchUpdate Fail! Param is Empty !");
		Session session = HibernateUtils.getCurrentSession(sessionFactory);
		for (int i = 0; i < list.size(); i++) {
			session.update(list.get(i));
			if (i % 30 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	@Override
	public List<T> query(Class<T> clazz, String property, Object value) {
		return query(clazz, 0, 0, property, value);
	}

	@Override
	public List<T> query(Class<T> clazz, String[] property, Object... value) {
		return query(clazz, 0, 0, property, value);
	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows, String property, Object value) {
		return query(clazz, page, rows, StringUtils.EMPTY_STRING, property, value);
	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows, String[] property, Object... value) {
		return query(clazz, 0, 0, StringUtils.EMPTY_STRING, property, value);
	}

	@Override
	public List<T> query(Class<T> clazz, String order, String property, Object value) {
		return query(clazz, 0, 0, order, property, value);
	}

	@Override
	public List<T> query(Class<T> clazz, String order, String[] property, Object... value) {
		return query(clazz, 0, 0, order, property, value);
	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows, String order, String property, Object value) {
		return query(clazz, page, rows, order, new String[] { property }, value);
	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows, String order, String[] property, Object... value) {
		List<T> list = Collections.emptyList();
		try {
			String hql = HibernateUtils.getListHql(order, clazz, property);
			Query query = HibernateUtils.getHqlQuery(hql, sessionFactory);
			if (null != value) {
				for (int i = 0; i < value.length; i++) {
					HibernateUtils.setParams(query, StringUtils.toString(i), value[i]);
				}
			}
			HibernateUtils.setPage(page, rows, query);
			list = query.list();
		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return list;
	}

	@Override
	public List<T> query(Class<T> clazz, String order) {
		return query(clazz, Collections.EMPTY_MAP, order);
	}

	@Override
	public List<T> query(Class<T> clazz, String order, int page, int rows) {
		return query(clazz, page, rows, Collections.EMPTY_MAP, order);
	}

	@Override
	public List<T> query(Class<T> clazz) {
		return query(clazz, Collections.EMPTY_MAP);
	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows) {
		return query(clazz, page, rows, Collections.EMPTY_MAP, null);
	}

	@Override
	public List<T> query(Class<T> clazz, Map<String, Object> params) {
		return query(clazz, params, StringUtils.EMPTY_STRING);

	}

	@Override
	public List<T> query(Class<T> clazz, Map<String, Object> params, String order) {
		return query(clazz, 0, 0, params, order);
	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows, Map<String, Object> params, String order) {
		List<T> list = Collections.emptyList();
		try {
			String hql = HibernateUtils.getListHql(order, clazz, params);
			Query query = HibernateUtils.getHqlQuery(hql, sessionFactory);
			setParamMap(params, query);
			HibernateUtils.setPage(page, rows, query);
			list = query.list();
		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return list;

	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows, Map<String, Object> map) {
		return query(clazz, page, rows, map, StringUtils.EMPTY_STRING);
	}

	/**
	 * Query设置Map参数
	 *
	 * @param params
	 * @param query
	 * @return
	 * @throws
	 * @author Caratacus
	 * @date 2016/9/2 0002
	 * @version 1.0
	 */
	private void setParamMap(Map<String, Object> params, Query query) {
		if (MapUtils.isNotEmpty(params)) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				HibernateUtils.setParams(query, key, obj);
			}
		}
	}

	@Override
	public long count(Class clazz) {
		return count(clazz, Collections.EMPTY_MAP);
	}

	@Override
	public long count(Class clazz, String property, Object... value) {
		return count(clazz, new String[] { property }, value);
	}

	@Override
	public long count(Class clazz, String[] property, Object... value) {
		String countHql = HibernateUtils.getCountHql(clazz, property);
		Query query = HibernateUtils.getHqlQuery(countHql, sessionFactory);
		for (int i = 0; i < value.length; i++) {
			HibernateUtils.setParams(query, StringUtils.toString(i), value[i]);
		}
		return (long) query.uniqueResult();
	}

	@Override
	public long count(Class clazz, Map<String, Object> params) {
		String hql = HibernateUtils.getCountHql(clazz, params);
		Query query = HibernateUtils.getHqlQuery(hql, sessionFactory);
		if (MapUtils.isNotEmpty(params)) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				HibernateUtils.setParams(query, key, obj);
			}
		}
		return (long) query.uniqueResult();

	}

	@Override
	public Page<?> queryListWithSql(Class clazz, Wrapper wrapper, Page page) {
		try {
			String sql = SqlUtils.sqlList(clazz, wrapper, page);
			Query query = HibernateUtils.getSqlQuery(sql, sessionFactory);
			HibernateUtils.setPage(page.getCurrent(), page.getSize(), query);
			page.setRecords(query.list());
			CountOptimize countOptimize = SqlUtils.getCountOptimize(sql, page.isOptimizeCount());
			Query countQuery = HibernateUtils.getSqlQuery(countOptimize.getCountSQL(), sessionFactory);
			BigInteger bigInteger = (BigInteger) countQuery.uniqueResult();
			page.setTotal(bigInteger.intValue());
		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return page;
	}

	@Override
	public List<?> queryListWithSql(Class clazz, Wrapper wrapper) {
		List list = Collections.EMPTY_LIST;
		try {
			String sql = SqlUtils.sqlList(clazz, wrapper, null);
			Query query = HibernateUtils.getSqlQuery(sql, sessionFactory);
			list = query.list();
		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return list;
	}

	@Override
	public long queryCountWithSql(Class clazz, Wrapper wrapper) {
		long count = 0;
		try {
			String sql = SqlUtils.sqlCount(clazz, wrapper);
			Query query = HibernateUtils.getSqlQuery(sql, sessionFactory);
			BigInteger bigInteger = (BigInteger) query.uniqueResult();
			count = bigInteger.longValue();
		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return count;
	}

}
