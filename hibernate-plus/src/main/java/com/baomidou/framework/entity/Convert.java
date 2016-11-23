package com.baomidou.framework.entity;

import com.baomidou.hibernateplus.converter.BeanConverter;
import com.baomidou.hibernateplus.exceptions.HibernatePlusException;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * <p>
 * 统一VO TO互转方法
 * </p>
 *
 * @author Caratacus
 * @date 2016-11-23
 */
@MappedSuperclass
public class Convert implements Serializable {

	/**
	 * 互转方法
	 * 
	 * @param clazz
	 * @param <T>
	 * @return
	 */
    public <T> T convert(Class<T> clazz) {
		try {
			T entity = clazz.newInstance();
			return BeanConverter.convert(entity, this);
		} catch (Exception e) {
			throw new HibernatePlusException("Conversion Object Failed. Cause:" + e);
		}
	}

}
