package org.apache.dearbaby.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.dearbaby.impl.sql.compile.QueryTreeNode;

public class SinQuery {

	public String tableName;
	public String alias;
	public ArrayList<String> columns = new ArrayList<String>();
	public ArrayList<Map> results = new ArrayList<Map>();
	
	public boolean simpleSelect=true;

	public String andCondition = "1=1 ";

	public String sql = "";

	public IExecutor executor;
	
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
		Map map= getCurrRow();
		return map.get(name);
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
			results=executor.exe(sql, columns);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exeGroupBy(QueryTreeNode qn){
		
	}

}
