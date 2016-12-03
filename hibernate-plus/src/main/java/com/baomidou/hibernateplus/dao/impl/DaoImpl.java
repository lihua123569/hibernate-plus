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
package com.baomidou.hibernateplus.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.jboss.logging.Logger;

import com.baomidou.hibernateplus.condition.SelectWrapper;
import com.baomidou.hibernateplus.condition.wrapper.Wrapper;
import com.baomidou.hibernateplus.dao.IDao;
import com.baomidou.hibernateplus.entity.Convert;
import com.baomidou.hibernateplus.entity.page.Page;
import com.baomidou.hibernateplus.utils.Assert;
import com.baomidou.hibernateplus.utils.CollectionUtils;
import com.baomidou.hibernateplus.utils.EntityInfoUtils;
import com.baomidou.hibernateplus.utils.HibernateUtils;
import com.baomidou.hibernateplus.utils.MapUtils;
import com.baomidou.hibernateplus.utils.RandomUtils;
import com.baomidou.hibernateplus.utils.ReflectionKit;
import com.baomidou.hibernateplus.utils.SqlUtils;
import com.baomidou.hibernateplus.utils.StringUtils;

/**
 * <p>
 * IDao接口实现类
 * </p>
 *
 * @author Caratacus
 * @date 2016-11-23
 */
public class DaoImpl<T extends Convert> implements IDao<T> {

	private static final Logger logger = Logger.getLogger(DaoImpl.class);

	/**
	 * 获取masterSession
	 *
	 * @return
	 */
	protected SessionFactory masterSession() {
		return EntityInfoUtils.getEntityInfo(toClass()).getMaster();
	}

	/**
	 * 获取slaveSession
	 *
	 * @return
	 */
	protected SessionFactory slaveSession() {
		Set<SessionFactory> slaves = EntityInfoUtils.getEntityInfo(toClass()).getSlaves();
		if (CollectionUtils.isEmpty(slaves)) {
			return masterSession();
		}
		return RandomUtils.getRandomElement(slaves);
	}

	@Override
	public T get(Serializable id) {
		Assert.notNull(id);
		return (T) HibernateUtils.getSession(slaveSession(), isCurrent()).get(toClass(), id);
	}

	/**
	 * 根据HQL查询单个对象
	 *
	 * @param hql
	 * @return
	 */
	protected T get(String hql) {
		return (T) get(hql, Collections.EMPTY_MAP);
	}

	/**
	 * 根据HQL查询单个对象
	 *
	 * @param hql
	 * @param params
	 * @return
	 */
	protected T get(String hql, Map<String, Object> params) {
		Assert.hasLength(hql);
		T t = null;
		try {
			Query query = HibernateUtils.getHqlQuery(hql, slaveSession(), isCurrent());
			if (MapUtils.isNotEmpty(params)) {
				for (String key : params.keySet()) {
					Object obj = params.get(key);
					HibernateUtils.setParams(query, key, obj);
				}
			}
			t = (T) query.uniqueResult();
		} catch (Exception e) {
			logger.warn("Warn: Unexpected exception.  Cause:" + e);
		}
		return t;

	}

	@Override
	public T save(T t) {
		Assert.notNull(t);
		HibernateUtils.getSession(masterSession(), isCurrent()).save(t);
		return t;
	}

	@Override
	public void saveOrUpdate(T t) {
		Assert.notNull(t);
		HibernateUtils.getSession(masterSession(), isCurrent()).saveOrUpdate(t);
	}

	@Override
	public void update(T t) {
		Assert.notNull(t);
		HibernateUtils.getSession(masterSession(), isCurrent()).merge(t);
	}

	@Override
	public int update(Wrapper wrapper) {
		Assert.notEmpty(wrapper.getSetMap());
		String sqlUpdate = SqlUtils.sqlUpdate(toClass(), wrapper);
		return executeSqlUpdate(sqlUpdate);
	}

	@Override
	public void delete(T t) {
		Assert.notNull(t);
		HibernateUtils.getSession(masterSession(), isCurrent()).delete(t);
	}

	@Override
	public int delete(Wrapper wrapper) {
		String sqlDelete = SqlUtils.sqlDelete(toClass(), wrapper);
		return executeSqlUpdate(sqlDelete);

	}

	@Override
	public boolean insertBatch(List<T> list, int size) {
		Assert.notEmpty(list);
		try {
			Session session = HibernateUtils.getSession(masterSession(), isCurrent());
			for (int i = 0; i < list.size(); i++) {
				session.save(list.get(i));
				if (i % size == 0) {
					session.flush();
					session.clear();
				}
			}
		} catch (Exception e) {
			logger.warn("Warn: Unexpected exception.  Cause:" + e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateBatch(List<T> list, int size) {
		Assert.notEmpty(list);
		try {
			Session session = HibernateUtils.getSession(masterSession(), isCurrent());
			for (int i = 0; i < list.size(); i++) {
				session.update(list.get(i));
				if (i % size == 0) {
					session.flush();
					session.clear();
				}
			}
		} catch (Exception e) {
			logger.warn("Warn: Unexpected exception.  Cause:" + e);
			return false;
		}
		return true;

	}

	@Override
	public <T> List<T> selectList(Wrapper wrapper) {
		List<T> list = Collections.emptyList();
		try {
			String sql = SqlUtils.sqlEntityList(toClass(), wrapper, null);
			Query query = HibernateUtils.getEntitySqlQuery(toClass(), sql, slaveSession(), isCurrent());
			list = query.list();
		} catch (Exception e) {
			logger.warn("Warn: Unexpected exception.  Cause:" + e);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> selectMaps(Wrapper wrapper) {
		List<Map<String, Object>> list = Collections.emptyList();
		try {
			String sql = SqlUtils.sqlList(toClass(), wrapper, null);
			Query query = HibernateUtils.getSqlQuery(sql, slaveSession(), isCurrent()).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		} catch (Exception e) {
			logger.warn("Warn: Unexpected exception.  Cause:" + e);
		}
		return list;
	}

	@Override
	public int selectCount(Wrapper wrapper) {
		int count = 0;
		try {
			String sql = SqlUtils.sqlCount(toClass(), wrapper);
			Query query = HibernateUtils.getSqlQuery(sql, slaveSession(), isCurrent());
			BigInteger bigInteger = (BigInteger) query.uniqueResult();
			count = bigInteger.intValue();
		} catch (Exception e) {
			logger.warn("Warn: Unexpected exception.  Cause:" + e);
		}
		return count;
	}

	@Override
	public Page<Map<String, Object>> selectMapPage(Wrapper wrapper, Page<Map<String, Object>> page) {
		try {
			String sql = SqlUtils.sqlList(toClass(), wrapper, page);
			Query query = HibernateUtils.getSqlQuery(sql, slaveSession(), isCurrent()).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			HibernateUtils.setPage(page.getCurrent(), page.getSize(), query);
			page.setRecords(query.list());
			setPageTotal(sql, page);
		} catch (Exception e) {
			logger.warn("Warn: Unexpected exception.  Cause:" + e);
		}
		return page;
	}

	@Override
	public Page selectPage(Wrapper wrapper, Page page) {
		try {
			String sql = SqlUtils.sqlEntityList(toClass(), wrapper, page);
			Query query = HibernateUtils.getEntitySqlQuery(toClass(), sql, slaveSession(), isCurrent());
			HibernateUtils.setPage(page.getCurrent(), page.getSize(), query);
			page.setRecords(query.list());
			setPageTotal(sql, page);
		} catch (Exception e) {
			logger.warn("Warn: Unexpected exception.  Cause:" + e);
		}
		return page;
	}

	/**
	 * 执行HQL
	 *
	 * @param hql
	 * @return
	 */
	protected int executeHql(String hql) {
		return executeHql(hql, Collections.EMPTY_MAP);
	}

	/**
	 * 执行HQL
	 *
	 * @param hql
	 * @param params
	 * @return
	 */
	protected int executeHql(String hql, Map<String, Object> params) {
		Assert.hasLength(hql);
		Query query = HibernateUtils.getHqlQuery(hql, masterSession(), isCurrent());
		if (MapUtils.isNotEmpty(params)) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				HibernateUtils.setParams(query, key, obj);
			}
		}
		return query.executeUpdate();
	}

	/**
	 * 根据HQL查询列表
	 *
	 * @param hql
	 * @return
	 */
	protected List<T> queryListWithHql(String hql) {
		return queryListWithHql(hql, Collections.EMPTY_MAP);
	}

	/**
	 * 根据HQL查询列表
	 *
	 * @param hql
	 * @param params
	 * @return
	 */
	protected List<T> queryListWithHql(String hql, Map<String, Object> params) {
		return queryListWithHql(hql, params, 0, 0);
	}

	/**
	 * 根据HQL查询列表
	 *
	 * @param hql
	 * @param params
	 * @param page
	 * @param rows
	 * @return
	 */
	protected List<T> queryListWithHql(String hql, Map<String, Object> params, int page, int rows) {
		Assert.hasLength(hql);
		List<T> list = Collections.emptyList();
		try {
			Query query = HibernateUtils.getHqlQuery(hql, slaveSession(), isCurrent());
			setParamMap(params, query);
			HibernateUtils.setPage(page, rows, query);
			list = query.list();
		} catch (Exception e) {
			logger.warn("Warn: Unexpected exception.  Cause:" + e);
		}
		return list;

	}

	/**
	 * 根据HQL查询列表
	 *
	 * @param hql
	 * @param page
	 * @param rows
	 * @return
	 */
	protected List<T> queryListWithHql(String hql, int page, int rows) {
		return queryListWithHql(hql, Collections.EMPTY_MAP, page, rows);
	}

	/**
	 * 根据HQL查询列表
	 *
	 * @param hql
	 * @return
	 */
	protected List<Map<String, Object>> queryMapsWithHql(String hql) {
		return queryMapsWithHql(hql, 0, 0);
	}

	/**
	 * 根据HQL查询列表
	 *
	 * @param hql
	 * @param page
	 * @param rows
	 * @return
	 */
	protected List<Map<String, Object>> queryMapsWithHql(String hql, int page, int rows) {
		Assert.hasLength(hql);
		List<Map<String, Object>> list = Collections.emptyList();
		try {
			Query query = HibernateUtils.getHqlQuery(hql, slaveSession(), isCurrent()).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			HibernateUtils.setPage(page, rows, query);
			list = query.list();

		} catch (Exception e) {
			logger.warn("Warn: Unexpected exception.  Cause:" + e);
		}
		return list;
	}

	/**
	 * 根据HQL查询数量
	 *
	 * @param hql
	 * @return
	 */
	protected int queryCountWithHql(String hql) {
		return queryCountWithHql(hql, Collections.EMPTY_MAP);
	}

	/**
	 * 根据HQL查询数量
	 *
	 * @param hql
	 * @param params
	 * @return
	 */
	protected int queryCountWithHql(String hql, Map<String, Object> params) {
		Assert.hasLength(hql);
		Query query = HibernateUtils.getHqlQuery(hql, slaveSession(), isCurrent());
		if (MapUtils.isNotEmpty(params)) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				HibernateUtils.setParams(query, key, obj);
			}
		}
		BigInteger bigInteger = (BigInteger) query.uniqueResult();
		return bigInteger.intValue();
	}

	/**
	 * 执行SQL
	 *
	 * @param sql
	 * @return
	 */
	protected int executeSqlUpdate(String sql) {
		return executeSqlUpdate(sql, Collections.EMPTY_MAP);
	}

	/**
	 * 执行SQL
	 * <p>
	 * delete from user where username = ?0 and sex = ?1
	 * </p>
	 *
	 * @param sql
	 * @param args
	 * @return
	 */
	protected int executeSqlUpdate(String sql, Object[] args) {
		Assert.hasLength(sql);
		int resultCount = 0;
		try {
			Query query = HibernateUtils.getSqlQuery(sql, masterSession(), isCurrent());
			if (null != args) {
				for (int i = 0; i < args.length; i++) {
					HibernateUtils.setParams(query, StringUtils.toString(i), args[i]);
				}
			}
			resultCount = query.executeUpdate();
		} catch (Exception e) {
			logger.warn("Warn: Unexpected exception.  Cause:" + e);
		}
		return resultCount;
	}

	/**
	 * 执行SQL
	 * <p>
	 * delete from user where username = :username and sex = :sex
	 * </p>
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	protected int executeSqlUpdate(String sql, Map<String, Object> params) {
		Assert.hasLength(sql);
		int resultCount = 0;
		if (StringUtils.isNotBlank(sql)) {
			try {
				Query query = HibernateUtils.getSqlQuery(sql, masterSession(), isCurrent());
				if ((params != null) && !params.isEmpty()) {
					for (String key : params.keySet()) {
						Object obj = params.get(key);
						HibernateUtils.setParams(query, key, obj);
					}
				}
				resultCount = query.executeUpdate();
			} catch (Exception e) {
				logger.warn("Warn: Unexpected exception.  Cause:" + e);
			}
		}
		return resultCount;
	}

	/**
	 * 执行SQL查询数量
	 *
	 * @param sql
	 * @return
	 */
	protected int queryCount(String sql) {
		return queryCount(sql, SelectWrapper.DEFAULT);
	}

	/**
	 * 执行SQL查询数量
	 *
	 * @param sql
	 * @return
	 */
	protected int queryCount(String sql, Wrapper wrapper) {
		Assert.hasLength(sql);
		sql += wrapper.getSqlSegment();
		int count = 0;
		try {
			Query query = HibernateUtils.getSqlQuery(sql, slaveSession(), isCurrent());
			BigInteger bigInteger = (BigInteger) query.uniqueResult();
			bigInteger.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 执行SQL返回单个对象
	 *
	 * @param sql
	 * @param wrapper
	 * @return
	 */
	protected Map<String, Object> queryMapWithSql(String sql, Wrapper wrapper) {
		Assert.hasLength(sql);
		Map<String, Object> map = Collections.emptyMap();
		try {
			sql += wrapper.getSqlSegment();
			Query query = HibernateUtils.getSqlQuery(sql, slaveSession(), isCurrent()).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			map = (Map<String, Object>) query.uniqueResult();
		} catch (Exception e) {
			logger.warn("Warn: Unexpected exception.  Cause:" + e);
		}
		return map;
	}

	/**
	 * 执行SQL返回单个对象
	 *
	 * @param sql
	 * @return
	 */
	protected Map<String, Object> queryMapWithSql(String sql) {
		return queryMapWithSql(sql, SelectWrapper.DEFAULT);
	}

	/**
	 * 执行SQL查询列表
	 *
	 * @param sql
	 * @return
	 */
	protected List<Map<String, Object>> queryMapsWithSql(String sql) {
		return queryMapsWithSql(sql, Collections.EMPTY_MAP);
	}

	/**
	 * 执行SQL查询列表
	 *
	 * @param sql
	 * @param page
	 * @param rows
	 * @return
	 */
	protected List<Map<String, Object>> queryMapsWithSql(String sql, int page, int rows) {
		return queryMapsWithSql(sql, Collections.EMPTY_MAP, page, rows);
	}

	/**
	 * 执行SQL查询列表
	 *
	 * @param sql
	 * @param params
	 * @param page
	 * @param rows
	 * @return
	 */
	protected List<Map<String, Object>> queryMapsWithSql(String sql, Map<String, Object> params, int page, int rows) {
		Assert.hasLength(sql);
		List<Map<String, Object>> list = Collections.emptyList();
		try {
			Query query = HibernateUtils.getSqlQuery(sql, slaveSession(), isCurrent()).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			if (MapUtils.isNotEmpty(params)) {
				for (String key : params.keySet()) {
					Object obj = params.get(key);
					HibernateUtils.setParams(query, key, obj);
				}
			}
			HibernateUtils.setPage(page, rows, query);
			list = query.list();
		} catch (Exception e) {
			logger.warn("Warn: Unexpected exception.  Cause:" + e);
		}
		return list;
	}

	/**
	 * 执行SQL查询列表
	 *
	 * @param sql
	 * @param params
	 * @return
	 */
	protected List<Map<String, Object>> queryMapsWithSql(String sql, Map<String, Object> params) {
		return queryMapsWithSql(sql, params, 0, 0);
	}

	/**
	 * 执行SQL查询列表
	 *
	 * @param sql
	 * @return
	 */
	protected List queryListWithSql(String sql) {
		return this.queryListWithSql(sql, Collections.EMPTY_MAP);
	}

	/**
	 * 执行SQL查询列表
	 *
	 * @param sql
	 * @param page
	 * @param rows
	 * @return
	 */
	protected List queryListWithSql(String sql, int page, int rows) {
		return this.queryListWithSql(sql, Collections.EMPTY_MAP, page, rows);
	}

	/**
	 * 执行SQL查询列表
	 *
	 * @param sql
	 * @param params
	 * @param page
	 * @param rows
	 * @return
	 */
	protected List queryListWithSql(String sql, Map<String, Object> params, int page, int rows) {
		Assert.hasLength(sql);
		List list = Collections.emptyList();
		try {
			Query query = HibernateUtils.getSqlQuery(sql, slaveSession(), isCurrent());
			if (MapUtils.isNotEmpty(params)) {
				for (String key : params.keySet()) {
					Object obj = params.get(key);
					HibernateUtils.setParams(query, key, obj);
				}
			}
			HibernateUtils.setPage(page, rows, query);
			list = query.list();
		} catch (Exception e) {
			logger.warn("Warn: Unexpected exception.  Cause:" + e);
		}
		return list;
	}

	/**
	 * 执行SQL查询列表
	 *
	 * @param sql
	 * @param params
	 * @return
	 */
	protected List queryListWithSql(String sql, Map<String, Object> params) {
		return queryListWithSql(sql, params, 0, 0);
	}

	/**
	 * 查询列表
	 *
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryMaps(Map<String, Object> map) {
		List<Map<String, Object>> list = Collections.emptyList();
		try {
			String hql = HibernateUtils.getListHql(toClass(), map);
			Query query = HibernateUtils.getHqlQuery(hql, slaveSession(), isCurrent()).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			for (String key : map.keySet()) {
				Object obj = map.get(key);
				HibernateUtils.setParams(query, key, obj);
			}
			list = query.list();
		} catch (Exception e) {
			logger.warn("Warn: Unexpected exception.  Cause:" + e);
		}
		return list;

	}

	/**
	 * 设置Page total值
	 *
	 * @param sql
	 * @param page
	 */
	private void setPageTotal(String sql, Page<Map<String, Object>> page) {
		if (page.isSearchCount()) {
			String sqlCount = SqlUtils.sqlCountOptimize(sql);
			Query countQuery = HibernateUtils.getSqlQuery(sqlCount, slaveSession(), isCurrent());
			BigInteger bigInteger = (BigInteger) countQuery.uniqueResult();
			page.setTotal(bigInteger.intValue());
		}
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

	/**
	 * 获取toClass
	 *
	 * @return
	 */
	protected Class<T> toClass() {
		return ReflectionKit.getSuperClassGenricType(getClass(), 0);
	}

	/**
	 * 是否获取当前事务的Session
	 * 
	 * @return
	 */
	protected Boolean isCurrent() {
		return true;
	}

}
