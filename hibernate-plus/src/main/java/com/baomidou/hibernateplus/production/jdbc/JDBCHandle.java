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
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.hibernateplus.production.domain.Table;
import com.baomidou.hibernateplus.production.tools.StringUtilsWithDB;

public class JDBCHandle {
	private static final String BASE_SQL = "SELECT * FROM %s";
	private static String COLUMN_SQL = "SELECT COLUMN_NAME, DATA_TYPE, COLUMN_COMMENT FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME ='${tableName}' AND COLUMN_NAME = '${columnName}'";

	public static Table getColumns(Table table, Connection conn, boolean flagOpen) {
		List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
		List<Map<Object, Object>> reList = new ArrayList<Map<Object, Object>>();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(String.format(BASE_SQL, table.getTableName()));
			ResultSetMetaData rsmd = rs.getMetaData();
			Map<Object, Object> fields = null;
			int count = rsmd.getColumnCount();
			for (int i = 1; i <= count; i++) {
				fields = new HashMap<Object, Object>();
				String name = null;
				if (flagOpen) {
					name = StringUtilsWithDB.replaceUnderlineAndfirstToUpper(rsmd.getColumnName(i), "_", "");
				} else {
					name = rsmd.getColumnName(i);
				}

				String dbName = rsmd.getColumnName(i);
				ResultSet resultSet = stmt.executeQuery(COLUMN_SQL.replace("${tableName}", table.getTableName()).replace(
						"${columnName}", dbName));
				String columnComment = "";
				while (resultSet.next())
					columnComment = resultSet.getString("COLUMN_COMMENT");
				String type = change(rsmd.getColumnType(i));
				fields.put("name", name);
				fields.put("dbName", dbName);
				fields.put("type", type);
				fields.put("columnComment", columnComment);
				fields.put("isPK", false);
				fields.put("isFK", false);
				list.add(fields);
			}
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		} finally {
			JDBCTemplate.close(rs);
		}
		DatabaseMetaData dbmd;
		try {
			dbmd = conn.getMetaData();
			ResultSet rPrimaryKey;
			String databaseName = dbmd.getDatabaseProductName().toUpperCase();
			if (databaseName.indexOf("ORACLE") != -1) {
				rPrimaryKey = dbmd.getPrimaryKeys(null, JDBCTemplate.USERNAME.toUpperCase(), table.getTableName());
			} else {
				rPrimaryKey = dbmd.getPrimaryKeys(null, null, table.getTableName());
			}
			while (rPrimaryKey.next()) {

				String pk = rPrimaryKey.getString(4);

				for (int i = 0; i < list.size(); i++) {
					Map<Object, Object> map = (Map<Object, Object>) list.get(i);
					Object pkStr = null;
					if (flagOpen) {
						pkStr = StringUtilsWithDB.replaceUnderlineAllToLowerCase(map.get("name").toString());
					} else {
						pkStr = map.get("name");
					}
					if (pk.equals(pkStr)) {

						Map<Object, Object> mapPK = new HashMap<Object, Object>();
						mapPK.put("name", map.get("name"));
						mapPK.put("dbName", pkStr);
						mapPK.put("type", map.get("type"));
						table.setPkName((String) map.get("name"));
						table.setDbPkName(StringUtilsWithDB.replaceUnderlineAllToLowerCase(map.get("name").toString()));
						mapPK.put("isPK", true);
						mapPK.put("isFK", false);
						reList.add(mapPK);
						list.remove(map);
						i = list.size() + 1;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(stmt, conn);
		}
		reList.addAll(list);
		table.setList(reList);
		return table;
	}

	private static String change(int type) {

		switch (type) {
		case Types.ARRAY:
			return "java.sql.Array";
		case Types.BIGINT:
			return "Long";
		case Types.BINARY:
			return "byte[]";
		case Types.BIT:
			return "boolean";
		case Types.BLOB:
			return "java.sql.Blob";
		case Types.BOOLEAN:
			return "boolean";
		case Types.CHAR:
			return "String";
		case Types.CLOB:
			return "java.sql.Clob";
		case Types.DATALINK:
			return "Float";
		case Types.DATE:
			return "java.sql.Date";
		case Types.DECIMAL:
			return "java.math.BigDecimal";
			// case Types.DISTINCT:
			// return "";
		case Types.DOUBLE:
			return "Float";
		case Types.FLOAT:
			return "Float";
		case Types.INTEGER:
			return "Integer";
			// case Types.JAVA_OBJECT:
			// return "";
		case Types.LONGNVARCHAR:
			return "String";
		case Types.LONGVARBINARY:
			return "byte[]";
		case Types.LONGVARCHAR:
			return "String";
		case Types.NCHAR:
			return "String";
		case Types.NCLOB:
			return "java.sql.NClob";
		case Types.NULL:
			return "";
		case Types.NUMERIC:
			return "java.math.BigDecimal";
		case Types.NVARCHAR:
			return "String";
			// case Types.OTHER:
			// return "";
			// case Types.REAL:
			// return "";
			// case Types.REF:
			// return "";
			// case Types.ROWID:
			// return "";
		case Types.SMALLINT:
			return "short";
			// case Types.SQLXML:
			// return "";
			// case Types.STRUCT:
			// return "";
		case Types.TIME:
			return "java.sql.Time";
		case Types.TIMESTAMP:
			return "Date";
		case Types.TINYINT:
			return "byte";
		case Types.VARBINARY:
			return "byte[]";
		case Types.VARCHAR:
			return "String";
		default:
			return "String";
		}
	}

}
