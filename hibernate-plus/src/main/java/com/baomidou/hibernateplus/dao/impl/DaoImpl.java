package com.baomidou.hibernateplus.dao.impl;

import com.baomidou.hibernateplus.dao.IDao;
import com.baomidou.hibernateplus.exceptions.HibernatePlusException;
import com.baomidou.hibernateplus.page.CountOptimize;
import com.baomidou.hibernateplus.page.Page;
import com.baomidou.hibernateplus.query.Wrapper;
import com.baomidou.hibernateplus.utils.CollectionUtils;
import com.baomidou.hibernateplus.utils.HibernateUtils;
import com.baomidou.hibernateplus.utils.MapUtils;
import com.baomidou.hibernateplus.utils.ReflectionKit;
import com.baomidou.hibernateplus.utils.SqlUtils;
import com.baomidou.hibernateplus.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
public abstract class DaoImpl<T> implements IDao<T> {

	protected static final Logger logger = Logger.getLogger("DaoImpl");

	protected Class<T> clazz = ReflectionKit.getSuperClassGenricType(getClass(), 0);

	public abstract SessionFactory getSessionFactory();

	@Override
	public T save(T t) {
		if (null == t)
			throw new HibernatePlusException("execute Save Fail! Param is Empty !");
		HibernateUtils.getCurrentSession(getSessionFactory()).save(t);
		return t;
	}

	@Override
	public T get(Serializable id) {
		if (null == id)
			throw new HibernatePlusException("execute Get Fail! Param is Empty !");
		return (T) HibernateUtils.getCurrentSession(getSessionFactory()).get(clazz, id);
	}

	protected T get(String hql) {
		return (T) get(hql, Collections.EMPTY_MAP);
	}

	protected T get(String hql, Map<String, Object> params) {
		if (StringUtils.isBlank(hql))
			throw new HibernatePlusException("execute Get Fail! Param is Empty !");
		T t = null;
		try {
			Query query = HibernateUtils.getHqlQuery(hql, getSessionFactory());
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
			throw new HibernatePlusException("execute Delete! Param is Empty !");
		HibernateUtils.getCurrentSession(getSessionFactory()).delete(t);
	}

	@Override
	public void update(T t) {
		if (null == t)
			throw new HibernatePlusException("execute Update! Param is Empty !");
		HibernateUtils.getCurrentSession(getSessionFactory()).merge(t);
	}

	@Override
	public void saveOrUpdate(T t) {
		if (null == t)
			throw new HibernatePlusException("execute SaveOrUpdate! Param is Empty !");
		HibernateUtils.getCurrentSession(getSessionFactory()).saveOrUpdate(t);
	}

	protected List<T> query(String hql) {
		return query(hql, Collections.EMPTY_MAP);
	}

	protected List<T> query(String hql, Map<String, Object> params) {
		return query(hql, params, 0, 0);
	}

	protected List<T> query(String hql, Map<String, Object> params, int page, int rows) {
		if (StringUtils.isBlank(hql))
			throw new HibernatePlusException("execute Query Fail! Param is Empty !");
		List<T> list = Collections.emptyList();
		try {
			Query query = HibernateUtils.getHqlQuery(hql, getSessionFactory());
			setParamMap(params, query);
			HibernateUtils.setPage(page, rows, query);
			list = query.list();
		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return list;

	}

	protected List<T> query(String hql, int page, int rows) {
		return query(hql, Collections.EMPTY_MAP, page, rows);
	}

	@Override
	public void insertWithBatch(List<T> list) {
		if (CollectionUtils.isEmpty(list))
			throw new HibernatePlusException("execute BatchInsert Fail! Param is Empty !");
		Session session = HibernateUtils.getCurrentSession(getSessionFactory());
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
			throw new HibernatePlusException("execute BatchUpdate Fail! Param is Empty !");
		Session session = HibernateUtils.getCurrentSession(getSessionFactory());
		for (int i = 0; i < list.size(); i++) {
			session.update(list.get(i));
			if (i % 30 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	@Override
	public List<T> query(String property, Object value) {
		return query(0, 0, property, value);
	}

	@Override
	public List<T> query(String[] property, Object... value) {
		return query(0, 0, property, value);
	}

	@Override
	public List<T> query(int page, int rows, String property, Object value) {
		return query(page, rows, StringUtils.EMPTY_STRING, property, value);
	}

	@Override
	public List<T> query(int page, int rows, String[] property, Object... value) {
		return query(0, 0, StringUtils.EMPTY_STRING, property, value);
	}

	@Override
	public List<T> query(String order, String property, Object value) {
		return query(0, 0, order, property, value);
	}

	@Override
	public List<T> query(String order, String[] property, Object... value) {
		return query(0, 0, order, property, value);
	}

	@Override
	public List<T> query(int page, int rows, String order, String property, Object value) {
		return query(page, rows, order, new String[] { property }, value);
	}

	@Override
	public List<T> query(int page, int rows, String order, String[] property, Object... value) {
		List<T> list = Collections.emptyList();
		try {
			String hql = HibernateUtils.getListHql(order, clazz, property);
			Query query = HibernateUtils.getHqlQuery(hql, getSessionFactory());
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
	public List<T> queryOrder(String order) {
		return query(Collections.EMPTY_MAP, order);
	}

	@Override
	public List<T> queryOrder(String order, int page, int rows) {
		return query(page, rows, Collections.EMPTY_MAP, order);
	}

	@Override
	public List<T> query() {
		return query(Collections.EMPTY_MAP);
	}

	@Override
	public List<T> query(int page, int rows) {
		return query(page, rows, Collections.EMPTY_MAP, null);
	}

	@Override
	public List<T> query(Map<String, Object> params) {
		return query(params, StringUtils.EMPTY_STRING);

	}

	@Override
	public List<T> query(Map<String, Object> params, String order) {
		return query(0, 0, params, order);
	}

	@Override
	public List<T> query(int page, int rows, Map<String, Object> params, String order) {
		List<T> list = Collections.emptyList();
		try {
			String hql = HibernateUtils.getListHql(order, clazz, params);
			Query query = HibernateUtils.getHqlQuery(hql, getSessionFactory());
			setParamMap(params, query);
			HibernateUtils.setPage(page, rows, query);
			list = query.list();
		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return list;

	}

	@Override
	public List<T> query(int page, int rows, Map<String, Object> map) {
		return query(page, rows, map, StringUtils.EMPTY_STRING);
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
	public int selectCount() {
		return selectCount(Collections.EMPTY_MAP);
	}

	@Override
	public int selectCount(String property, Object... value) {
		return selectCount(new String[] { property }, value);
	}

	@Override
	public int selectCount(String[] property, Object... value) {
		String countHql = HibernateUtils.getCountHql(clazz, property);
		Query query = HibernateUtils.getHqlQuery(countHql, getSessionFactory());
		for (int i = 0; i < value.length; i++) {
			HibernateUtils.setParams(query, StringUtils.toString(i), value[i]);
		}
		return (Integer) query.uniqueResult();
	}

	@Override
	public int selectCount(Map<String, Object> params) {
		String hql = HibernateUtils.getCountHql(clazz, params);
		Query query = HibernateUtils.getHqlQuery(hql, getSessionFactory());
		if (MapUtils.isNotEmpty(params)) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				HibernateUtils.setParams(query, key, obj);
			}
		}
		return (Integer) query.uniqueResult();

	}

	@Override
	public Page<?> selectPage(Wrapper wrapper, Page page) {
		try {
			String sql = SqlUtils.sqlList(clazz, wrapper, page);
			Query query = HibernateUtils.getSqlQuery(sql, getSessionFactory());
			HibernateUtils.setPage(page.getCurrent(), page.getSize(), query);
			page.setRecords(query.list());
			CountOptimize countOptimize = SqlUtils.getCountOptimize(sql, page.isOptimizeCount());
			Query countQuery = HibernateUtils.getSqlQuery(countOptimize.getCountSQL(), getSessionFactory());
			BigInteger bigInteger = (BigInteger) countQuery.uniqueResult();
			page.setTotal(bigInteger.intValue());
		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return page;
	}

	@Override
	public List<?> queryListWithSql(Wrapper wrapper) {
		List list = Collections.EMPTY_LIST;
		try {
			String sql = SqlUtils.sqlList(clazz, wrapper, null);
			Query query = HibernateUtils.getSqlQuery(sql, getSessionFactory());
			list = query.list();
		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return list;
	}

	@Override
	public int selectCount(Wrapper wrapper) {
		int count = 0;
		try {
			String sql = SqlUtils.sqlCount(clazz, wrapper);
			Query query = HibernateUtils.getSqlQuery(sql, getSessionFactory());
			BigInteger bigInteger = (BigInteger) query.uniqueResult();
			count = bigInteger.intValue();
		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return count;
	}

	protected long queryCountWithHql(String hql) {
		return queryCountWithHql(hql, Collections.EMPTY_MAP);
	}

	protected long queryCountWithHql(String hql, Map<String, Object> params) {
		if (StringUtils.isBlank(hql))
			throw new HibernatePlusException("Query Count Fail! Param is Empty !");
		Query query = HibernateUtils.getHqlQuery(hql, getSessionFactory());
		if (MapUtils.isNotEmpty(params)) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				HibernateUtils.setParams(query, key, obj);
			}
		}
		return (Long) query.uniqueResult();
	}

	protected int executeHql(String hql) {
		return executeHql(hql, Collections.EMPTY_MAP);
	}

	protected int executeHql(String hql, Map<String, Object> params) {
		if (StringUtils.isBlank(hql))
			throw new HibernatePlusException("execute Query Fail! Param is Empty !");
		Query query = HibernateUtils.getHqlQuery(hql, getSessionFactory());
		if (MapUtils.isNotEmpty(params)) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				HibernateUtils.setParams(query, key, obj);
			}
		}
		return query.executeUpdate();
	}

	protected int executeSql(String sql) {
		return executeSql(sql, Collections.EMPTY_MAP);
	}

	protected int executeSql(String sql, Map<String, Object> params) {
		if (StringUtils.isBlank(sql))
			throw new HibernatePlusException("execute Query Fail! Param is Empty !");
		Query query = HibernateUtils.getSqlQuery(sql, getSessionFactory());
		if (MapUtils.isNotEmpty(params)) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				HibernateUtils.setParams(query, key, obj);
			}
		}
		return query.executeUpdate();
	}

	protected long queryCountWithSql(String sql) {
		return queryCountWithSql(sql, Collections.EMPTY_MAP);
	}

	protected long queryCountWithSql(String sql, Map<String, Object> params) {
		if (StringUtils.isBlank(sql))
			throw new HibernatePlusException("execute Query Fail! Param is Empty !");
		Query query = HibernateUtils.getSqlQuery(sql, getSessionFactory());
		if (MapUtils.isNotEmpty(params)) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				HibernateUtils.setParams(query, key, obj);
			}
		}
		BigInteger count = (BigInteger) query.uniqueResult();
		return count.longValue();
	}

	protected Map<?, ?> queryMapWithSql(String sql, Map<String, Object> params) {
		if (StringUtils.isBlank(sql))
			throw new HibernatePlusException("execute Query Fail! Param is Empty !");
		Map resultMap = Collections.EMPTY_MAP;
		try {
			Query query = HibernateUtils.getSqlQuery(sql, getSessionFactory());
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			if (MapUtils.isNotEmpty(params)) {
				for (String key : params.keySet()) {
					Object obj = params.get(key);
					HibernateUtils.setParams(query, key, obj);
				}
			}
			resultMap = (Map) query.uniqueResult();
		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return resultMap;
	}

	protected Map<?, ?> queryMapWithSql(String sql) {
		return queryMapWithSql(sql, Collections.EMPTY_MAP);
	}

	protected List<?> queryListWithSql(String sql) {
		return queryListWithSql(sql, Collections.EMPTY_MAP);
	}

	protected List<?> queryListWithSql(String sql, int page, int rows) {
		return queryListWithSql(sql, Collections.EMPTY_MAP, page, rows);
	}

	protected List<?> queryListWithSql(String sql, Map<String, Object> params, int page, int rows) {
		if (StringUtils.isBlank(sql))
			throw new HibernatePlusException("execute Query Fail! Param is Empty !");
		List list = Collections.EMPTY_LIST;
		try {
			Query query = HibernateUtils.getSqlQuery(sql, getSessionFactory());
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			if (MapUtils.isNotEmpty(params)) {
				for (String key : params.keySet()) {
					Object obj = params.get(key);
					HibernateUtils.setParams(query, key, obj);
				}
			}
			HibernateUtils.setPage(page, rows, query);
			list = query.list();
		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return list;
	}

	protected List<?> queryListWithSql(String sql, Map<String, Object> params) {
		return queryListWithSql(sql, params, 0, 0);
	}

	protected int executeSqlUpdate(String sql) {
		return executeSqlUpdate(sql, Collections.EMPTY_MAP);
	}

	protected int executeSqlUpdate(String sql, Map<String, Object> params) {
		if (StringUtils.isBlank(sql))
			throw new HibernatePlusException("execute Query Fail! Param is Empty !");
		int resultCount = 0;
		if (StringUtils.isNotBlank(sql)) {
			try {
				Query query = HibernateUtils.getSqlQuery(sql, getSessionFactory());
				if ((params != null) && !params.isEmpty()) {
					for (String key : params.keySet()) {
						Object obj = params.get(key);
						HibernateUtils.setParams(query, key, obj);
					}
				}
				resultCount = query.executeUpdate();
			} catch (Exception e) {
				logger.warning("Warn: Unexpected exception.  Cause:" + e);
			}
		}
		return resultCount;
	}

	protected List<?> queryListWithSql(String sql, Object[] args) {
		if (StringUtils.isBlank(sql))
			throw new HibernatePlusException("execute Query Fail! Param is Empty !");
		List list = Collections.EMPTY_LIST;
		try {
			Query query = HibernateUtils.getSqlQuery(sql, getSessionFactory());
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			if (null != args) {
				for (int i = 0; i < args.length; i++) {
					HibernateUtils.setParams(query, StringUtils.toString(i), args[i]);
				}
			}
			list = query.list();
		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return list;
	}

	protected Map<?, ?> queryMapWithSql(String sql, Object[] args) {
		if (StringUtils.isBlank(sql))
			throw new HibernatePlusException("execute Query Fail! Param is Empty !");
		Map resultMap = Collections.EMPTY_MAP;
		try {
			Query query = HibernateUtils.getSqlQuery(sql, getSessionFactory());
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			if (null != args) {
				for (int i = 0; i < args.length; i++) {
					HibernateUtils.setParams(query, StringUtils.toString(i), args[i]);
				}
			}
			resultMap = (Map) query.uniqueResult();
		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return resultMap;
	}

	protected int executeSqlUpdate(String sql, Object[] args) {
		if (StringUtils.isBlank(sql))
			throw new HibernatePlusException("execute Query Fail! Param is Empty !");
		int resultCount = 0;
		try {
			Query query = HibernateUtils.getSqlQuery(sql, getSessionFactory());
			if (null != args) {
				for (int i = 0; i < args.length; i++) {
					HibernateUtils.setParams(query, StringUtils.toString(i), args[i]);
				}
			}
			resultCount = query.executeUpdate();
		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return resultCount;
	}

	// TODO 保留方法 @Override
	public List<?> queryListWithHql() {
		return queryListWithHql(Collections.EMPTY_MAP);
	}

	// TODO 保留方法 @Override
	public List<?> queryListWithHql(String property, Object value) {
		return queryListWithHql(new String[] { property }, value);
	}

	@Override
	public T get(String property, Object value) {
		T t = null;
		try {
			String hql = HibernateUtils.getListHql(clazz, property);
			Query query = HibernateUtils.getHqlQuery(hql, getSessionFactory());
			HibernateUtils.setParams(query, "0", value);
			t = (T) query.uniqueResult();
		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return t;
	}

	// TODO 保留方法 @Override
	public List<?> queryListWithHql(String[] property, Object... value) {
		List list = Collections.EMPTY_LIST;
		try {
			String hql = HibernateUtils.getListHql(clazz, property);
			Query query = HibernateUtils.getHqlQuery(hql, getSessionFactory());
			if (null != value) {
				for (int i = 0; i < value.length; i++) {
					HibernateUtils.setParams(query, StringUtils.toString(i), value);
				}
			}
			list = query.list();

		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return list;

	}

	// TODO 保留方法 @Override
	public List<?> queryListWithHql(Map<String, Object> map) {
		List list = Collections.EMPTY_LIST;
		try {
			String hql = HibernateUtils.getListHql(clazz, map);
			Query query = HibernateUtils.getHqlQuery(hql, getSessionFactory());
			for (String key : map.keySet()) {
				Object obj = map.get(key);
				HibernateUtils.setParams(query, key, obj);
			}
			list = query.list();
		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return list;

	}

	protected List<?> queryListWithHql(String hql) {
		return queryListWithHql(hql, 0, 0);
	}

	protected List<?> queryListWithHql(String hql, int page, int rows) {
		if (StringUtils.isBlank(hql))
			throw new HibernatePlusException("execute Query Fail! Param is Empty !");
		List list = Collections.EMPTY_LIST;
		try {
			Query query = HibernateUtils.getHqlQuery(hql, getSessionFactory());
			HibernateUtils.setPage(page, rows, query);
			list = query.list();

		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return list;
	}

}
