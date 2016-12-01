/**
 * Copyright (c) 2011-2014, hubin (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baomidou.hibernateplus.enums;

import com.baomidou.hibernateplus.exceptions.HibernatePlusException;
import com.baomidou.hibernateplus.utils.StringUtils;

/**
 * <p>
 * 数据库主从设置枚举
 * </p>
 * 
 * @author Caratacus
 * @Date 2016-12-1
 */
public enum Setting {

	MASTER("master", "主数据库"), SLAVE("slave", "从数据库");

	/** 类型 */
	private final String type;

	/** 描述 */
	private final String desc;

	Setting(final String type, final String desc) {
		this.type = type;
		this.desc = desc;
	}

	public String getType() {
		return this.type;
	}

	public String getDesc() {
		return this.desc;
	}

	public static Setting getSetting(String type) {
		if (StringUtils.isNotBlank(type)) {
			Setting[] dbTypes = Setting.values();
			for (Setting dbType : dbTypes) {
				if (dbType.getType().equalsIgnoreCase(type)) {
					return dbType;
				}
			}
		}
		throw new HibernatePlusException("Error: Master-slave Setting error !");
	}

}
