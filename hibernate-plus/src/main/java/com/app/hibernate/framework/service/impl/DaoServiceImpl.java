package com.app.hibernate.framework.service.impl;

import com.app.hibernate.framework.service.DaoService;
import com.app.hibernate.persist.dao.CRUDDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * DaoService实现类
 *
 * @author Caratacus
 * @version 1.0
 * @date 2016/5/5 0005
 */
@Service("daoService")
public class DaoServiceImpl implements DaoService {
	@Autowired
	private CRUDDao crudDao;

	@Override
	public Long queryCountWithHql(String hql) {
		return crudDao.queryCountWithHql(hql);
	}

	@Override
	public Long queryCountWithHql(String hql, Map<String, Object> params) {
		return crudDao.queryCountWithHql(hql, params);
	}

	@Override
	public int executeHql(String hql) {
		return crudDao.executeHql(hql);
	}

	@Override
	public int executeHql(String hql, Map<String, Object> params) {
		return crudDao.executeHql(hql, params);
	}

	@Override
	public int executeSql(String sql) {
		return crudDao.executeSql(sql);
	}

	@Override
	public int executeSql(String sql, Map<String, Object> params) {
		return crudDao.executeSql(sql, params);
	}

	@Override
	public long queryCountWithSql(String sql) {
		return crudDao.queryCountWithSql(sql);
	}

	@Override
	public long queryCountWithSql(String sql, Map<String, Object> params) {
		return crudDao.queryCountWithSql(sql, params);
	}

	@Override
	public List queryListWithSql(String sql) {
		return crudDao.queryListWithSql(sql);
	}

	@Override
	public Map queryMapWithSql(String sql) {
		return crudDao.queryMapWithSql(sql);
	}

	@Override
	public List queryListWithSql(String sql, Map<String, Object> args) {
		return crudDao.queryListWithSql(sql, args);
	}

	@Override
	public List queryListWithSql(String sql, Map<String, Object> args, int page, int rows) {
		return crudDao.queryListWithSql(sql, args, page, rows);
	}

	@Override
	public List queryListWithSql(String sql, int page, int rows) {
		return crudDao.queryListWithSql(sql, page, rows);
	}

	@Override
	public Map queryMapWithSql(String sql, Map<String, Object> args) {
		return crudDao.queryMapWithSql(sql, args);
	}

	@Override
	public int executeSqlUpdate(String sql) {
		return crudDao.executeSqlUpdate(sql);
	}

	@Override
	public int executeSqlUpdate(String sql, Map<String, Object> args) {
		return crudDao.executeSqlUpdate(sql, args);
	}

	@Override
	public List queryListWithSql(String sql, Object[] args) {
		return crudDao.queryListWithSql(sql, args);
	}

	@Override
	public Map queryMapWithSql(String sql, Object[] args) {
		return crudDao.queryMapWithSql(sql, args);
	}

	@Override
	public int executeSqlUpdate(String sql, Object[] args) {
		return crudDao.executeSqlUpdate(sql, args);
	}

	@Override
	public List queryListWithHql(String hql) {
		return crudDao.queryListWithHql(hql);
	}

	@Override
	public List queryListWithHql(String hql, int page, int rows) {
		return crudDao.queryListWithHql(hql, page, rows);
	}

	public void setCrudDao(CRUDDao crudDao) {
		this.crudDao = crudDao;
	}
}
