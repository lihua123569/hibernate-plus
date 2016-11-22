package com.app.hibernate.production.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author keeny
 * 
 */
public class JDBCTemplate {

	private static Connection connection = null;
	public static String DRIVERCLASS = "com.mysql.jdbc.Driver";
	public static String URL = "jdbc:mysql://";
	public static String USERNAME = "root";
	public static String PASSWORD = "root";

	/**
	 * 
	 * @param driverClass
	 * @param url
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public static Connection openConnection(String driverClass, String url, String userName, String passWord) {

		try {
			if (connection == null || connection.isClosed()) {
				connect(driverClass, url, userName, passWord);
			}
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
		return connection;
	}

	/**
	 * use default config ,path: src/config/config.properties
	 * 
	 * @return Connection
	 */
	public static Connection openConnection() {

		return openConnection(DRIVERCLASS, URL, USERNAME, PASSWORD);
	}

	private static void connect(String driverClass, String url, String userName, String passWord) {
		try {
			Class.forName(driverClass);
			connection = DriverManager.getConnection(url, userName, passWord);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getLocalizedMessage());
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
	}

	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
	}

	public static void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
	}

	public static void close(ResultSet rs, Statement statement, Connection con) {
		close(rs);
		close(statement);
		close(con);
	}

	public static void close(Statement statement, Connection con) {
		close(statement);
		close(con);
	}
}
