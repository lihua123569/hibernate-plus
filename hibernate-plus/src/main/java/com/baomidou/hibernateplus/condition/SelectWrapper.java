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
package com.baomidou.hibernateplus.condition;

import com.baomidou.hibernateplus.condition.wrapper.Wrapper;
import com.baomidou.hibernateplus.utils.StringUtils;

/**
 * <p>
 * Select条件构造器
 * </p>
 *
 * @author Caratacus
 * @date 2016-12-2
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class SelectWrapper extends Wrapper {
	/**
	 * DEFAULT
	 */
	public static final SelectWrapper DEFAULT = SelectWrapper.instance();
	/**
	 * SQL 查询字段内容，例如：id,name,age
	 */
	protected String sqlSelect = null;

	public String getSqlSelect() {
		if (StringUtils.isBlank(sqlSelect)) {
			return null;
		}
		return stripSqlInjection(sqlSelect);
	}

	public SelectWrapper setSqlSelect(String sqlSelect) {
		if (StringUtils.isNotBlank(sqlSelect)) {
			this.sqlSelect = sqlSelect;
		}
		return this;
	}

	/**
	 * 获取实例
	 */
	public static SelectWrapper instance() {
		return new SelectWrapper();
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
			return StringUtils.EMPTY;
		}

		return sqlWhere;
	}

}
