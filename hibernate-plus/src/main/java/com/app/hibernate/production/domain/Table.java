package com.app.hibernate.production.domain;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Keeny (2012-11-9)
 **/
public class Table {
	/** 类包名 **/
	private String packageName;
	/** 类说明 **/
	private String description;
	private Connection connection;
	/** 字段集合 **/
	private List<Map<Object, Object>> list;
	/** 模板路径 **/
	private String templateDirectory;
	/** 模板名字 **/
	private String templateName;
	/** 输出路径 **/
	private String outFilePath;
	/** 编码格式 **/
	private String encoding;
	/** 表对项目实体名字 **/
	private String domainName;
	/** 项目根目录 **/
	private String projectRoot;
	/** 表名 **/
	private String tableName;
	/** 创建的文件夹,如：dao,action .. **/
	private String pak;
	/** MODEL主键字段 **/
	private String pkName;
	/** 数据库主键字段 **/
	private String dbPkName;
	/** VO serialVersionUID **/
	private Long serialVId = new Random().nextLong();
	/** TO serialVersionUID **/
	private Long serialTId = new Random().nextLong();

	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public String getPak() {
		return pak;
	}

	public void setPak(String pak) {
		this.pak = pak;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Map<Object, Object>> getList() {
		return list;
	}

	public void setList(List<Map<Object, Object>> list) {
		this.list = list;
	}

	public String getTemplateDirectory() {
		return templateDirectory;
	}

	public void setTemplateDirectory(String templateDirectory) {
		this.templateDirectory = templateDirectory;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getOutFilePath() {
		return outFilePath;
	}

	public void setOutFilePath(String outFilePath) {
		this.outFilePath = outFilePath;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getProjectRoot() {
		return projectRoot;
	}

	public void setProjectRoot(String projectRoot) {
		this.projectRoot = projectRoot;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDbPkName() {
		return dbPkName;
	}

	public void setDbPkName(String dbPkName) {
		this.dbPkName = dbPkName;
	}

	public Long getSerialVId() {
		return serialVId;
	}

	public void setSerialVId(Long serialVId) {
		this.serialVId = serialVId;
	}

	public Long getSerialTId() {
		return serialTId;
	}

	public void setSerialTId(Long serialTId) {
		this.serialTId = serialTId;
	}
}
