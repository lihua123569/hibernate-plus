package com.app.hibernate.framework.entity;

import com.app.hibernate.persist.converter.BeanConverter;
import com.app.hibernate.persist.exceptions.AppHibernateException;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * <p>
 * 统一表主键
 * </p>
 *
 * @author Caratacus
 * @date 2016-10-23
 */
@MappedSuperclass
public class PrimaryKey implements Serializable {

    /**
     * 获取自动转换后的JavaBean对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T convert(Class<T> clazz) {
        try {
            T t = clazz.newInstance();
            return BeanConverter.convert(t, this);
        } catch (Exception e) {
            throw new AppHibernateException("转换对象失败", e);
        }
    }

}
