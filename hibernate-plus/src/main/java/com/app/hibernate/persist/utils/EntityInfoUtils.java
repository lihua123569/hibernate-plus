/**
 * Copyright (c) 2011-2014, hubin (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.app.hibernate.persist.utils;

import com.app.hibernate.framework.entity.EntityInfo;

import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 数据库表反射信息
 * </p>
 * 
 * @author hubin
 * @Date 2016-01-23
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
		String tableName = clazz.getSimpleName();
		if (table != null && StringUtils.isNotBlank(table.name())) {
			tableName = table.name();
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
