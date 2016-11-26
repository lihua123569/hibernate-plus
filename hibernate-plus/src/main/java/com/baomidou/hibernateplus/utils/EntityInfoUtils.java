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
package com.baomidou.hibernateplus.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baomidou.framework.entity.EntityFieldInfo;
import com.baomidou.framework.entity.EntityInfo;
import com.baomidou.hibernateplus.exceptions.HibernatePlusException;
import org.hibernate.SessionFactory;

/**
 * <p>
 * 数据库表反射信息
 * </p>
 * 
 * @author Caratacus
 * @Date 2016-11-23
 */
public class EntityInfoUtils {

	/*
	 * 默认表主键
	 */
	private static final String DEFAULT_ID_NAME = "id";
	/*
	 * 缓存实体信息
	 */
	private static final Map<String, EntityInfo> modelCache = new ConcurrentHashMap<String, EntityInfo>();

	/**
	 * <p>
	 * 获取实体映射信息
	 * <p>
	 *
	 * @param clazz
	 *            反射实体类
	 * @return
	 */
	public static EntityInfo getEntityInfo(Class<?> clazz) {
		return modelCache.get(clazz.getName());
	}

	/**
	 * <p>
	 * 初始化SessionFactory
	 * <p>
	 *
	 * @param sessionFactory
	 * @return
	 */
	public static void initSession(SessionFactory sessionFactory) {
		Iterator<Map.Entry<String, EntityInfo>> iterator = modelCache.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, EntityInfo> entry = (Map.Entry<String, EntityInfo>) iterator.next();
			EntityInfo entityInfo = entry.getValue();
			if (entityInfo.getSessionFactory() == null)
				entityInfo.setSessionFactory(sessionFactory);
		}
	}

	/**
	 * <p>
	 * 实体类反射获取表信息【初始化】
	 * <p>
	 *
	 * @param clazz
	 *            反射实体类
	 * @return
	 */
	public synchronized static EntityInfo initEntityInfo(Class<?> clazz) {
		EntityInfo entityInfo = modelCache.get(clazz.getName());
		if (entityInfo != null) {
			return entityInfo;
		}
		entityInfo = new EntityInfo();

		/* 表名 */
		Table table = clazz.getAnnotation(Table.class);
		String tableName = null;
		if (table != null && StringUtils.isNotBlank(table.name())) {
			tableName = table.name();
		}
		if (tableName == null) {
			throw new HibernatePlusException("Error: Entity @Table Not Found!");
		}
		// 实体字段
		Set<EntityFieldInfo> fieldInfos = entityFieldInfos(clazz, entityInfo);
		// 设置表字段
		entityInfo.setFieldInfos(fieldInfos);
		// 设置默认主键
		if (StringUtils.isBlank(entityInfo.getKeyProperty())) {
			entityInfo.setKeyProperty(DEFAULT_ID_NAME);
			entityInfo.setKeyColumn(DEFAULT_ID_NAME);
		}

		/*
		 * 注入
		 */
		modelCache.put(clazz.getName(), entityInfo);
		return entityInfo;
	}

	/**
	 * 获取实体类所有字段
	 * 
	 * @param clazz
	 * @param entityInfo
	 */
	private static Set<EntityFieldInfo> entityFieldInfos(Class<?> clazz, EntityInfo entityInfo) {
		Set<EntityFieldInfo> fieldInfos = new LinkedHashSet<EntityFieldInfo>();
		Map<String, Method> methodMap = ReflectionKit.hasReturnMethod(clazz);
		Set<Field> fields = getClassFields(clazz);
		for (Field field : fields) {
			Class<?> type = field.getType();
			String fieldName = field.getName();
			String methodName = ReflectionKit.getMethodCapitalize(fieldName);
			Method method = methodMap.get(methodName);
			if (method == null && Boolean.class.isAssignableFrom(type)) {
				method = methodMap.get(StringUtils.concatCapitalize("is", fieldName));
			}
			if (method != null) {
				if (ReflectionKit.hasAnnotation(method, Column.class)) {
					Column column = method.getAnnotation(Column.class);
					String name = column.name();
					EntityFieldInfo entityFieldInfo = new EntityFieldInfo();
					entityFieldInfo.setProperty(fieldName);
					entityFieldInfo.setColumn(StringUtils.isBlank(name) ? fieldName : name);
					fieldInfos.add(entityFieldInfo);
				} else if (ReflectionKit.hasAnnotation(method, Id.class)) {
					entityInfo.setKeyColumn(fieldName);
					entityInfo.setKeyProperty(fieldName);
				}
			} else {
				throw new HibernatePlusException(String.format("Error: Entity Field %s does has get Method!", fieldName));
			}

		}
		return fieldInfos;
	}

	/**
	 * 获取该类的所有属性列表
	 *
	 * @param clazz
	 *            反射类
	 * @return
	 */
	private static Set<Field> getClassFields(Class<?> clazz) {
		Set<Field> result = new LinkedHashSet<Field>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {

			/* 过滤静态属性 */
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}

			/* 过滤 transient关键字修饰的属性 */
			if (Modifier.isTransient(field.getModifiers())) {
				continue;
			}

			/* 过滤注解非表字段属性 */
			Transient annotation = field.getAnnotation(Transient.class);
			if (annotation != null) {
				continue;
			}
			result.add(field);
		}

		/* 处理父类字段 */
		Class<?> superClass = clazz.getSuperclass();
		if (superClass.equals(Object.class)) {
			return result;
		}
		result.addAll(getClassFields(superClass));
		return result;
	}

}
