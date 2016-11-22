/**
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
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

import com.app.hibernate.persist.page.CountOptimize;
import com.app.hibernate.persist.page.Page;
import com.app.hibernate.persist.page.Pagination;
import com.app.hibernate.framework.entity.EntityInfo;
import com.app.hibernate.persist.query.Wrapper;
import com.app.hibernate.persist.exceptions.AppHibernateException;
import org.hibernate.engine.jdbc.internal.BasicFormatterImpl;

/**
 * <p>
 * SqlUtils工具类
 * </p>
 *
 * @author Caratacus
 * @Date 2016-11-13
 */
public class SqlUtils {

	private final static BasicFormatterImpl sqlFormatter = new BasicFormatterImpl();
	private static final String SQL_COUNT = "SELECT COUNT(0) FROM %s %s";
	private static final String SQL_LIST = "SELECT %s FROM %s %s";

	/**
	 * 获取CountOptimize
	 * 
	 * @param originalSql
	 *            需要计算Count SQL
	 * @param isOptimizeCount
	 *            是否需要优化Count
	 * @return CountOptimize
	 */
	public static CountOptimize getCountOptimize(String originalSql, boolean isOptimizeCount) {
		boolean optimize = false;
		CountOptimize countOptimize = CountOptimize.newInstance();
		StringBuffer countSql = new StringBuffer("SELECT COUNT(1) AS TOTAL ");
		if (isOptimizeCount) {
			String tempSql = originalSql.replaceAll("(?i)ORDER[\\s]+BY", "ORDER BY");
			String indexOfSql = tempSql.toUpperCase();
			if (!indexOfSql.contains("DISTINCT")) {
				int formIndex = indexOfSql.indexOf("FROM");
				if (formIndex > -1) {
					// 有排序情况
					int orderByIndex = indexOfSql.lastIndexOf("ORDER BY");
					if (orderByIndex > -1) {
						tempSql = tempSql.substring(0, orderByIndex);
						countSql.append(tempSql.substring(formIndex));
						countOptimize.setOrderBy(false);
						// 无排序情况
					} else {
						countSql.append(tempSql.substring(formIndex));
					}
					// 执行优化
					optimize = true;
				}
			}
		}
		if (!optimize) {
			// 无优化SQL
			countSql.append("FROM (").append(originalSql).append(") A");
		}
		countOptimize.setCountSQL(countSql.toString());
		return countOptimize;
	}

	/**
	 * 查询SQL拼接Order By
	 * 
	 * @param originalSql
	 *            需要拼接的SQL
	 * @param page
	 *            page对象
	 * @param orderBy
	 *            是否需要拼接Order By
	 * @return
	 */
	public static String concatOrderBy(String originalSql, Pagination page, boolean orderBy) {
		if (orderBy && StringUtils.isNotBlank(page.getOrderByField())) {
			StringBuffer buildSql = new StringBuffer(originalSql);
			buildSql.append(" ORDER BY ").append(page.getOrderByField());
			buildSql.append(page.isAsc() ? " ASC " : " DESC ");
			return buildSql.toString();
		}
		return originalSql;
	}

	/**
	 * 格式sql
	 * 
	 * @param boundSql
	 * @param format
	 * @return
	 */
	public static String sqlFormat(String boundSql, boolean format) {
		if (format) {
			return sqlFormatter.format(boundSql);
		} else {
			return boundSql.replaceAll("[\\s]+", " ");
		}
	}

	public static String sqlList(Class clazz, Wrapper wrapper, Page page) {
		String tableName = getTableName(clazz);
		if (wrapper != null) {
			String sqlSelect = wrapper.getSqlSelect();
			if (page != null) {
				wrapper.orderBy(page.getOrderByField(), page.isAsc());
			}
			return String.format(SqlUtils.SQL_LIST, StringUtils.isBlank(sqlSelect) ? "*" : sqlSelect, tableName,
					wrapper.getSqlSegment());
		}
		if (page != null) {
			return concatOrderBy(String.format(SqlUtils.SQL_LIST, "*", tableName), page, true);
		}
		return String.format(SqlUtils.SQL_LIST, "*", tableName);

	}

	public static String sqlCount(Class clazz, Wrapper wrapper) {
		String tableName = getTableName(clazz);
		if (wrapper != null) {
			return String.format(SqlUtils.SQL_COUNT, tableName, wrapper.getSqlSegment());

		}
		return String.format(SqlUtils.SQL_COUNT, tableName);
	}

	public static String getTableName(Class clazz) {
		EntityInfo entityInfo = EntityInfoUtils.getEntityInfo(clazz);
		String tableName = entityInfo.getTableName();
		if (StringUtils.isBlank(tableName)) {
			throw new AppHibernateException("tableName not found!");
		}
		return tableName;
	}

}
