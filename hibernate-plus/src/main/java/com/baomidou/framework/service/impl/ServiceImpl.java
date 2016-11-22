package com.baomidou.framework.service.impl;

import com.baomidou.framework.entity.PrimaryKey;
import com.baomidou.framework.service.IService;
import com.baomidou.hibernateplus.converter.BeanConverter;
import com.baomidou.hibernateplus.dao.IDao;
import com.baomidou.hibernateplus.page.Page;
import com.baomidou.hibernateplus.query.Condition;
import com.baomidou.hibernateplus.query.Wrapper;
import com.baomidou.hibernateplus.utils.CollectionUtils;
import com.baomidou.hibernateplus.utils.ReflectionKit;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * <p>
 * IService 实现类（ 泛型：T 是数据库实体TO，V 对应数据库实体的VO ）
 * </p>
 *
 * @author Caratacus
 * @date 2016-10-23
 */
public class ServiceImpl<T extends PrimaryKey, V extends PrimaryKey> implements IService<V> {

	protected static final Logger logger = Logger.getLogger("ServiceImpl");

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
	public V selectOne(Wrapper wrapper) {
		List<V> list = selectList(wrapper);
		if (CollectionUtils.isNotEmpty(list)) {
			int size = list.size();
			if (size > 1) {
				logger.warning(String.format("Warn: selectOne Method There are  %s results.", size));
			}
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean insertBatch(List<V> list) {
		return baseDao.insertBatch(BeanConverter.convert(tClass, list), 30);
	}

	@Override
	public boolean updateBatch(List<V> list) {
		return baseDao.updateBatch(BeanConverter.convert(tClass, list), 30);
	}

	@Override
	public boolean insertBatch(List<V> list, int size) {
		return baseDao.insertBatch(BeanConverter.convert(tClass, list), size);
	}

	@Override
	public boolean updateBatch(List<V> list, int size) {
		return baseDao.updateBatch(BeanConverter.convert(tClass, list), size);
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
	public void deleteById(Serializable id) {
		// TODO 添加 deleteById
	}

	@Override
	public Page<V> selectPage(Page<V> page) {
		return baseDao.selectPage(Condition.instance(), vClass, page);
	}

	@Override
	public Page<V> selectPage(Page<V> page, String property, Object value) {
		return selectPage(Condition.instance().where(String.format("%s = {0}", property), value), page);
	}

	public Page<V> selectPage(Wrapper wrapper, Page<V> page) {
		return baseDao.selectPage(wrapper, vClass, page);
	}

	public <E> Page<E> selectPage(Wrapper wrapper, Class<E> clazz, Page<E> page) {
		return baseDao.<E> selectPage(wrapper, clazz, page);
	}

	public List<V> selectList(Wrapper wrapper) {
		return baseDao.selectList(wrapper, vClass);
	}

	public <E> List<E> selectList(Wrapper wrapper, Class<E> clazz) {
		return baseDao.<E> selectList(wrapper, clazz);
	}

	public int selectCount(Wrapper wrapper) {
		return baseDao.selectCount(wrapper);
	}

	@Override
	public boolean delete(Wrapper wrapper) {
		return retBool(baseDao.delete(wrapper));

	}

	@Override
	public boolean update(Map<String, Object> setMap, Wrapper wrapper) {
		return retBool(baseDao.update(setMap, wrapper));
	}

	@Override
	public V get(String property, Object value) {
		return baseDao.get(property, value) == null ? null : baseDao.get(property, value).convert(vClass);

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
}
