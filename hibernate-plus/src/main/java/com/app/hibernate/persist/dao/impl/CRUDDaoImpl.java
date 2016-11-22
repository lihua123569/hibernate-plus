package com.app.hibernate.persist.dao.impl;

import com.app.hibernate.persist.dao.CRUDDao;
import com.app.hibernate.persist.exceptions.AppHibernateException;
import com.app.hibernate.persist.utils.HibernateUtils;
import com.app.hibernate.persist.utils.MapUtils;
import com.app.hibernate.persist.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * <p>
 * CRUDDao接口实现
 * </p>
 * @author Caratacus
 * @date 2016-10-14
 */
@Repository
public class CRUDDaoImpl implements CRUDDao {

	protected static final Logger logger = Logger.getLogger("CRUDDaoImpl");
	@Autowired
	private SessionFactory sessionFactory;

	public CRUDDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public long queryCountWithHql(String hql) {
		return queryCountWithHql(hql, Collections.EMPTY_MAP);
	}

	@Override
	public long queryCountWithHql(String hql, Map<String, Object> params) {
		if (StringUtils.isBlank(hql))
			throw new AppHibernateException("Query Count Fail! Param is Empty !");
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
	public int executeHql(String hql) {
		return executeHql(hql, Collections.EMPTY_MAP);
	}

	@Override
	public int executeHql(String hql, Map<String, Object> params) {
		if (StringUtils.isBlank(hql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		Query query = HibernateUtils.getHqlQuery(hql, sessionFactory);
		if (MapUtils.isNotEmpty(params)) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				HibernateUtils.setParams(query, key, obj);
			}
		}
		return query.executeUpdate();
	}

	@Override
	public int executeSql(String sql) {
		return executeSql(sql, Collections.EMPTY_MAP);
	}

	@Override
	public int executeSql(String sql, Map<String, Object> params) {
		if (StringUtils.isBlank(sql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		Query query = HibernateUtils.getSqlQuery(sql, sessionFactory);
		if (MapUtils.isNotEmpty(params)) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				HibernateUtils.setParams(query, key, obj);
			}
		}
		return query.executeUpdate();
	}

	@Override
	public long queryCountWithSql(String sql) {
		return queryCountWithSql(sql, Collections.EMPTY_MAP);
	}

	@Override
	public long queryCountWithSql(String sql, Map<String, Object> params) {
		if (StringUtils.isBlank(sql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		Query query = HibernateUtils.getSqlQuery(sql, sessionFactory);
		if (MapUtils.isNotEmpty(params)) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				HibernateUtils.setParams(query, key, obj);
			}
		}
		BigInteger count = (BigInteger) query.uniqueResult();
		return count.longValue();
	}

	@Override
	public Map<?, ?> queryMapWithSql(String sql, Map<String, Object> params) {
		if (StringUtils.isBlank(sql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		Map resultMap = Collections.EMPTY_MAP;
		try {
			Query query = HibernateUtils.getSqlQuery(sql, sessionFactory);
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

	@Override
	public Map<?, ?> queryMapWithSql(String sql) {
		return queryMapWithSql(sql, Collections.EMPTY_MAP);
	}

	@Override
	public List<?> queryListWithSql(String sql) {
		return queryListWithSql(sql, Collections.EMPTY_MAP);
	}

	@Override
	public List<?> queryListWithSql(String sql, int page, int rows) {
		return queryListWithSql(sql, Collections.EMPTY_MAP, page, rows);
	}

	@Override
	public List<?> queryListWithSql(String sql, Map<String, Object> params, int page, int rows) {
		if (StringUtils.isBlank(sql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		List list = Collections.EMPTY_LIST;
		try {
			Query query = HibernateUtils.getSqlQuery(sql, sessionFactory);
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

	@Override
	public List<?> queryListWithSql(String sql, Map<String, Object> params) {
		return queryListWithSql(sql, params, 0, 0);
	}

	@Override
	public int executeSqlUpdate(String sql) {
		return executeSqlUpdate(sql, Collections.EMPTY_MAP);
	}

	@Override
	public int executeSqlUpdate(String sql, Map<String, Object> params) {
		if (StringUtils.isBlank(sql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		int resultCount = 0;
		if (StringUtils.isNotBlank(sql)) {
			try {
				Query query = HibernateUtils.getSqlQuery(sql, sessionFactory);
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

	@Override
	public List<?> queryListWithSql(String sql, Object[] args) {
		if (StringUtils.isBlank(sql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		List list = Collections.EMPTY_LIST;
		try {
			Query query = HibernateUtils.getSqlQuery(sql, sessionFactory);
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

	@Override
	public Map<?, ?> queryMapWithSql(String sql, Object[] args) {
		if (StringUtils.isBlank(sql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		Map resultMap = Collections.EMPTY_MAP;
		try {
			Query query = HibernateUtils.getSqlQuery(sql, sessionFactory);
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

	@Override
	public int executeSqlUpdate(String sql, Object[] args) {
		if (StringUtils.isBlank(sql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		int resultCount = 0;
		try {
			Query query = HibernateUtils.getSqlQuery(sql, sessionFactory);
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

	@Override
	public List<?> queryListWithHql(Class clazz) {
		return queryListWithHql(clazz, Collections.EMPTY_MAP);
	}

	@Override
	public List<?> queryListWithHql(Class clazz, String property, Object value) {
		return queryListWithHql(clazz, new String[] { property }, value);
	}

	@Override
	public Object queryMapWithHql(Class clazz, String property, Object value) {
		Object object = null;
		try {
			String hql = HibernateUtils.getListHql(clazz, property);
			Query query = HibernateUtils.getHqlQuery(hql, sessionFactory);
			HibernateUtils.setParams(query, "0", value);
			object = query.uniqueResult();
		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return object;
	}

	@Override
	public List<?> queryListWithHql(Class clazz, String[] property, Object... value) {
		List list = Collections.EMPTY_LIST;
		try {
			String hql = HibernateUtils.getListHql(clazz, property);
			Query query = HibernateUtils.getHqlQuery(hql, sessionFactory);
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

	@Override
	public List<?> queryListWithHql(Class clazz, Map<String, Object> map) {
		List list = Collections.EMPTY_LIST;
		try {
			String hql = HibernateUtils.getListHql(clazz, map);
			Query query = HibernateUtils.getHqlQuery(hql, sessionFactory);
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

	@Override
	public List<?> queryListWithHql(String hql) {
		return queryListWithHql(hql, 0, 0);
	}

	@Override
	public List<?> queryListWithHql(String hql, int page, int rows) {
		if (StringUtils.isBlank(hql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		List list = Collections.EMPTY_LIST;
		try {
			Query query = HibernateUtils.getHqlQuery(hql, sessionFactory);
			HibernateUtils.setPage(page, rows, query);
			list = query.list();

		} catch (Exception e) {
			logger.warning("Warn: Unexpected exception.  Cause:" + e);
		}
		return list;
	}

}
