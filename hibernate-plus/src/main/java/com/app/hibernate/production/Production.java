package com.app.hibernate.production;

import com.app.hibernate.production.domain.Table;
import com.app.hibernate.production.factory.BaseFactory;
import com.app.hibernate.production.glocal.ClassNames;
import com.app.hibernate.production.glocal.GlobalParam;
import com.app.hibernate.production.jdbc.JDBCHandle;
import com.app.hibernate.production.jdbc.JDBCTemplate;
import com.app.hibernate.production.tools.AssistTools;

import java.io.File;
import java.sql.Connection;

public class Production {
	static final String webroot = "webapp";

	public static void copyFile(ClassNames[] classNameses, String packName, String projectRoot, String domainName) {
		for (ClassNames classNames : classNameses) {
			String sourcePath = projectRoot + File.separator + "src" + File.separator + packName.replace(".", File.separator)
					+ File.separator + domainName + File.separator + classNames.getPakName();
			String toFilePath = projectRoot + File.separator + webroot + File.separator + "WEB-INF" + File.separator
					+ classNames.getPakName() + File.separator + domainName;
			AssistTools.copyFile(new File(sourcePath), new File(toFilePath));
		}
	}

	public static void delete(String packName, String projectRoot, String directory, String domainName) {
		String sourcePath = projectRoot + File.separator + "src" + File.separator + packName.replace(".", File.separator)
				+ File.separator + domainName + File.separator + directory;
		System.out.println(sourcePath);
		AssistTools.deleteAllFile(new File(sourcePath));
	}

	public static void operater(ClassNames[] classNameses, String tableName, String projectRoot, String domainName,
			String packName, String templateDirectory, String description, String encoding, boolean flagOpen) throws Exception {

		for (ClassNames classNames : classNameses) {
			Connection connection = JDBCTemplate.openConnection();

			ClassNames names = classNames;

			String packNames = packName + File.separator + domainName;
			String pak = names.getPakName();

			Table table = new Table();
			table.setTableName(tableName);
			table = JDBCHandle.getColumns(table, connection, flagOpen);
			table.setDescription(description);
			table.setPackageName(packNames.replace(File.separator, "."));
			table.setDomainName(domainName);
			table.setEncoding(encoding);
			table.setProjectRoot(projectRoot);
			table.setTemplateDirectory(templateDirectory);
			table.setTemplateName(GlobalParam.getTemplateName(names.getVal()));

			String filePrex = AssistTools.toUppercase(domainName);
			if (names.getMethodName().endsWith("jsp")) {
				filePrex = domainName;
			}
			String outFilePath = projectRoot + File.separator + "src" + File.separator + packNames.replace(".", File.separator)
					+ File.separator + pak.replace(".", File.separator) + File.separator
					+ getFileName(names.getPakName(), filePrex) + AssistTools.toUppercase(names.getMethodName());

			table.setOutFilePath(outFilePath);
			table.setPak(pak);
			table.setConnection(connection);

			BaseFactory actionFactory = new BaseFactory();
			actionFactory.create(table);
		}

	}

	public static String getFileName(String name, String fileName) {
		if (name.equalsIgnoreCase("vo")) {
			fileName = "V" + fileName.substring(0, 1).toLowerCase() + fileName.substring(1);
		} else if (name.equalsIgnoreCase("po")) {
			fileName = "T" + fileName.substring(0, 1).toLowerCase() + fileName.substring(1);
		}
		return fileName;
	}

}
