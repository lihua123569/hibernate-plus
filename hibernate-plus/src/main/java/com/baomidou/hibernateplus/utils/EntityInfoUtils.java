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

import com.baomidou.framework.entity.EntityInfo;
import com.baomidou.hibernateplus.exceptions.HibernatePlusException;

import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        if (null == modelCache.get(clazz.getName())) {
            initEntityInfo(clazz);
        }
		return modelCache.get(clazz.getName());
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
		if (tableName == null){
			throw new HibernatePlusException("Error: Entity @Table Not Found!");
		}
		entityInfo.setTableName(tableName);
		/* 实体字段 */
		Field[] fields = clazz.getDeclaredFields();
		entityInfo.setFields(fields);
		/*
		 * 注入
		 */
		modelCache.put(clazz.getName(), entityInfo);
		return entityInfo;
	}

}
