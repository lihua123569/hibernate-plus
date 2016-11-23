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
import com.baomidou.framework.entity.Convert;
import com.baomidou.hibernateplus.exceptions.HibernatePlusException;
import com.baomidou.hibernateplus.page.CountOptimize;
import com.baomidou.hibernateplus.page.Page;
import com.baomidou.hibernateplus.page.Pagination;
import com.baomidou.hibernateplus.query.Wrapper;
import org.hibernate.engine.jdbc.internal.BasicFormatterImpl;

import java.util.Iterator;
import java.util.Map;

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
	private static final String SQL_DELETE = "DELETE FROM %s %s";
	private static final String SQL_UPDATE = "UPDATE %s SET %s %s";

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
			throw new HibernatePlusException("tableName not found!");
		}
		return tableName;
	}

	/**
	 * 获取删除sql
	 *
	 * @param clazz
	 * @param wrapper
	 * @param <T>
	 * @return
	 */
	public static <T extends Convert> String sqlDelete(Class<T> clazz, Wrapper wrapper) {
		String tableName = getTableName(clazz);
		if (wrapper != null) {
			return String.format(SqlUtils.SQL_DELETE, tableName, wrapper.getSqlSegment());
		}
		return String.format(SqlUtils.SQL_DELETE, tableName);
	}

	/**
	 * 获取Update SQL
	 * 
	 * @param clazz
	 * @param setMap
	 * @param wrapper
	 * @return
	 */
	public static String sqlUpdate(Class clazz, Map<String, Object> setMap, Wrapper wrapper) {
		String tableName = getTableName(clazz);
		Iterator iterator = setMap.entrySet().iterator();
		int _size = setMap.size();
		int i = 0;
		StringBuilder builder = new StringBuilder();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			builder.append(String.format("%s = %s", key, value));
			if (i + 1 != _size) {
				builder.append(",");
			}
			i++;
		}
		if (wrapper != null) {
			return String.format(SqlUtils.SQL_UPDATE, tableName, builder.toString(), wrapper.getSqlSegment());
		}
		return String.format(SqlUtils.SQL_UPDATE, tableName, builder.toString());
	}
}
