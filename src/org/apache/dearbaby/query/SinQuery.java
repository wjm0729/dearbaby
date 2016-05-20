package org.apache.dearbaby.query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.dearbaby.impl.sql.compile.QueryTreeNode;
import org.apache.dearbaby.util.MysqlUtil;

public class SinQuery {

	public String tableName;
	public String alias;
	public ArrayList<String> columns = new ArrayList<String>();
	public ArrayList<Map> results = new ArrayList<Map>();

	public String andCondition = "1=1 ";

	public String sql = "";

	public QueryTreeNode node;
	
	public boolean isOrCond=false;

	private int rowId = 0;

	private boolean endOut = false;

	public void genSql() {
		String s = "select ";
		for (int i = 0; i < columns.size(); i++) {
			s = s + columns.get(i) + " ";
			if (i < columns.size() - 1) {
				s = s + ", ";
			} else {
				s = s + " ";
			}
		}
		s = s + " from " + tableName + " where " + andCondition;
		sql = s;
		System.out.println("sql---" + sql);
	}

	public Map getCurrRow() {
		Map m = new HashMap();
		if (results.size() == 0) {
			return null;
		}

		return results.get(rowId);
	}

	public Object getCurrCol(String name) {
		return getCurrRow().get(name);
	}

	public Map nextRow() {
		rowId++;

		if (rowId > results.size() - 1) {
			endOut = true;
			rowId = results.size() - 1;
		}
		return getCurrRow();
	}

	public void init() {
		rowId = 0;
		endOut = false;
	}

	public boolean isEnd() {
		return rowId >= results.size() - 1;
	}

	public boolean isEndOut() {
		return endOut;
	}

	public void exeSelect() {
		try {

			if (sql == null || sql.length() == 0) {
				return;
			}
			Connection conn;
			Class.forName("com.mysql.jdbc.Driver");//

			conn = DriverManager.getConnection(MysqlUtil.url, "root", "123456");

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Map m = new HashMap();
				for (String c : columns) {
					Object o = rs.getObject(c);
					m.put(c, o);
				}
				results.add(m);
			}
			System.out.println("results-size:  " + results.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
