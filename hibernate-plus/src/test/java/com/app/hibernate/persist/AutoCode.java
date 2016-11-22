package com.app.hibernate.persist;

import com.app.hibernate.production.Production;
import com.app.hibernate.production.glocal.ClassNames;
import com.app.hibernate.production.jdbc.JDBCTemplate;

public class AutoCode {

	public static void main(String[] args) throws Exception {
		ClassNames[] classNameses = { ClassNames.VO, ClassNames.PO, ClassNames.CONTROLLER, ClassNames.SERVICE,
				ClassNames.SERVICEIMPL };

		JDBCTemplate.DRIVERCLASS = "com.mysql.jdbc.Driver";
		JDBCTemplate.URL = "jdbc:mysql://127.0.0.1:3306/mybatis-plus";
		JDBCTemplate.USERNAME = "root";
		JDBCTemplate.PASSWORD = "521";
		// 代码生成路径
		String projectRoot = "E:\\page";
		String domainName = "user";
		// 表名
		String tableName = "user";
		// 包名
		String packName = "com.app.master";
		// String templateDirectory = projectRoot + "\\src\\template";
		String description = "@author master";
		String encoding = "UTF-8";
		// 是否开启大小写驼峰式转换
		boolean flagOpen = true;

		Production.operater(classNameses, tableName, projectRoot, domainName, packName, null, description, encoding, flagOpen);

		/*
		 * ClassNames[] movePath = { ClassNames.JSP_ADD, ClassNames.JSP_INDEX,
		 * ClassNames.JSP_UPDATE }; copyFile(movePath, packName, projectRoot,
		 * domainName);
		 */

		// Thread.sleep(2000);
		// delete(packName, projectRoot, "page", domainName);
	}

}
