package org.apache.dearbaby.query;

import java.util.ArrayList;
import java.util.Map;

import org.apache.dearbaby.impl.sql.compile.QueryTreeNode;

public class QueryMananger {

	public ArrayList<SinQuery> querys = new ArrayList<SinQuery>();
	public ArrayList<SinQuery> fetchRow = new ArrayList<SinQuery>();
	public IExecutor executor;
	public SinQuery foundQuery(String alias, String table) {
		SinQuery found = null;
		for (SinQuery q : querys) {
			if (q.alias != null && q.alias.equals(alias)) {
				found = q;
				break;

			}
		}
		if (found == null) {
			found = new SinQuery();
			found.alias = alias;
			found.tableName = table;
			querys.add(found);
		}
		return found;
	}

	public SinQuery findQuery(String alias) {
		SinQuery found = null;
		for (SinQuery q : querys) {
			if (q.alias != null && q.alias.equals(alias)) {
				found = q;
				break;

			}
		}
		return found;
	}

	public SinQuery findFetchRow(String alias) {
		SinQuery found = null;
		for (SinQuery q : fetchRow) {
			if (q.alias != null && q.alias.equals(alias)) {
				found = q;
				break;

			}
		}
		if (found == null) {
			return findQuery(alias);
		} else {
			return found;
		}
	}

	public void addCol(String alias, String table, String clo) {

		SinQuery found = foundQuery(alias, table);
		found.columns.add(clo);

	}

	public void addCol(String table, String clo) {
		addCol(table, table, clo);
	}

	public void addCond(String alias, String table, String cond) {
		SinQuery found = foundQuery(alias, table);
		if(!found.isOrCond==false){
			found.andCondition = found.andCondition + " and " + cond;
		}
	}
	

	public void addCond(String table, String cond) {
		addCond(table, table, cond);
	}

	public void orCond(String alias, String table ) {
		SinQuery found = foundQuery(alias, table);
		found.isOrCond=true;
		found.andCondition = " 1=1 ";
		 
	}

	public void orCond(  String table ) {
		orCond(table, table );
	}

	public void addNode(String alias, String table, QueryTreeNode node) {
		SinQuery found = foundQuery(alias, table);
		found.node = node;
		node.qs.querys.add(found); 
	}

	public void addNode(String table, QueryTreeNode node) {
		addNode(table, table, node);
	}

	public void addFetch() {
		fetchRow.clear();
		for (SinQuery q : querys) {
			SinQuery fq = new SinQuery();
			fq.tableName = q.tableName;
			fq.alias = q.alias;
			fq.results.add(q.getCurrRow());
			fetchRow.add(fq);
		}
	}

	public void addFetch(String alias, String tableName, Map m) {
		for (SinQuery q : querys) {
			SinQuery fq = new SinQuery();
			fq.tableName = tableName;
			fq.alias = alias;
			fq.results.add(m);
			fetchRow.add(fq);
		}
	}

	public void addFetch(QueryMananger qm) {
		for(SinQuery sq:qm.fetchRow){
			 for(SinQuery sqi: fetchRow){
				 if(sqi.alias.equals(sq.alias)){
					 fetchRow.remove(sqi);
					 break;
				 }
			 }
		}
		fetchRow.addAll(qm.fetchRow);
	}

	public void initFetch() {
		fetchRow.clear();

	}

}
