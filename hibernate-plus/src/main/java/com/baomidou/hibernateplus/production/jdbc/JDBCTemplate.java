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
package com.baomidou.hibernateplus.production.jdbc;

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
