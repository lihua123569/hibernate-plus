package com.baomidou.hibernateplus.spring;

import com.baomidou.hibernateplus.HibernateSpringSessionFactoryBean;
import com.baomidou.hibernateplus.spring.dao.DemoDao;
import com.baomidou.hibernateplus.spring.dao.DemoDaoImpl;
import com.baomidou.hibernateplus.query.Condition;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

/**
 * <p>
 * Hibernate Plus 单元测试
 * </p>
 *
 * @author Caratacus
 * @date 2016-12-2
 */
public class SpringHibernatePlusExam {

	private DemoDao demoDao = new DemoDaoImpl();

	static {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/hibernate-plus?characterEncoding=UTF-8");
		dataSource.setUsername("root");
		dataSource.setPassword("521");

		HibernateSpringSessionFactoryBean sessionFactoryBean = new HibernateSpringSessionFactoryBean();
		sessionFactoryBean.setType("master");
		sessionFactoryBean.setDataSource(dataSource);
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		hibernateProperties.put("hibernate.show_sql", false);
		hibernateProperties.put("hibernate.format_sql", false);
		hibernateProperties.put("hibernate.use_sql_comments", true);
		sessionFactoryBean.setHibernateProperties(hibernateProperties);
		sessionFactoryBean.setPackagesToScan("com.baomidou.spring.spring.po");
		try {
			sessionFactoryBean.afterPropertiesSet();
		} catch (Exception e) {
			System.out.println("Hibernate-Plus出错啦!");
		}
	}

	@Test
	public void test1() {
		List list = demoDao.selectList(Condition.instance());
		System.out.println(list);
	}
}
