package org.apache.dearbaby.sj;

import java.util.HashMap;
import java.util.Map;

import org.apache.dearbaby.impl.sql.compile.AggregateNode;
import org.apache.dearbaby.impl.sql.compile.ColumnReference;
import org.apache.dearbaby.impl.sql.compile.ResultColumn;
import org.apache.dearbaby.impl.sql.compile.ResultColumnList;
import org.apache.dearbaby.impl.sql.compile.StatementNode;
import org.apache.dearbaby.impl.sql.compile.SubqueryNode;
import org.apache.dearbaby.query.JdbcExecutor;
import org.apache.dearbaby.query.QueryMananger;
import org.apache.dearbaby.util.QueryUtil;
import org.apache.derby.ext.DearContext;
import org.apache.derby.iapi.sql.compile.Parser;

public class DearSelector {
	private StatementNode qt;
   
	public void query(String sql) {
		try{
			
			Parser ps =  DearContext.getParser();
			qt = (StatementNode) ps.parseStatement(sql);
	
			QueryMananger qm = new QueryMananger();
			qm.executor=new JdbcExecutor();
			qm.sql=sql;
			qt.genQuery(qm); 
			qm.readyMutlTask();
			qt.exeQuery();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public  ResultMap  fetch(){
		int i=0;
		int j=0;
	
		while (qt.fetch()) {
			 
			if (qt.match()) {
				HashMap map=new HashMap();
				ResultColumnList resultColumns= qt.getCols(); 
				for (Object o : resultColumns.v) {
					ResultColumn t = (ResultColumn) o;
					if (t._expression instanceof ColumnReference) {
						ColumnReference c=(ColumnReference)t._expression;
						String alias = c._qualifiedTableName.tableName;
						String cName = t.getSourceColumnName(); 
						Object obj =qt.getColVal(alias,cName);
						map.put(alias+"."+cName, obj);
						
					} else if (t._expression instanceof AggregateNode) {
						 
						 
						String name=QueryUtil.getAggrColName(t);
						 
						Object obj =qt.getColVal("#",name);
						map.put("#"+"."+name, obj);
					}else if (t._expression instanceof SubqueryNode) {
						 
						SubqueryNode subQ=(SubqueryNode)t._expression;
						Object obj=subQ.getColVal();
						String name=QueryUtil.getSubSelColName(t);
						 
						//Object obj =qt.getColVal("#",name);
						map.put("#"+"."+name, obj);
					}
					
				}
				System.out.println("fetch...... "
						+ i
						+ " ,"
						+ map);
				ResultMap m=new ResultMap(map);
				i++;
				return m;
			}

			qt.fetchEnd();
 
		}
		return null;
	}
}
