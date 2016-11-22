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
package com.baomidou.hibernateplus.query;

import com.baomidou.hibernateplus.utils.ReflectionKit;
import com.baomidou.hibernateplus.utils.StringUtils;

/**
 * <p>
 * Entity 对象封装操作类，定义T-SQL语法
 * </p>
 *
 * @author hubin , yanghu , Dyang , Caratacus
 * @Date 2016-11-7
 */
@SuppressWarnings("serial")
public class EntityWrapper<T> extends Wrapper<T> {

	/**
	 * 数据库表映射实体类
	 */
	protected T entity = null;

	public EntityWrapper() {
		/* 注意，传入查询参数 */
	}

	public EntityWrapper(T entity) {
		this.entity = entity;
	}

	public EntityWrapper(T entity, String sqlSelect) {
		this.entity = entity;
		this.sqlSelect = sqlSelect;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	/**
	 * SQL 片段
	 */
	@Override
	public String getSqlSegment() {
		/*
		 * 无条件
		 */
		String sqlWhere = sql.toString();
		if (StringUtils.isBlank(sqlWhere)) {
			return null;
		}

		/*
		 * 根据当前实体判断是否需要将WHERE替换成 AND 增加实体不为空但所有属性为空的情况
		 */
		sqlWhere = ReflectionKit.checkFieldValueNotNull(entity) ? sqlWhere.replaceFirst("WHERE", "AND") : sqlWhere;
		return sqlWhere;
	}

}
