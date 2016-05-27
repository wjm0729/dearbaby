package org.apache.dearbaby.sj;

import java.util.HashMap;
import java.util.Map;

import org.apache.dearbaby.impl.sql.compile.AggregateNode;
import org.apache.dearbaby.impl.sql.compile.ColumnReference;
import org.apache.dearbaby.impl.sql.compile.ResultColumn;
import org.apache.dearbaby.impl.sql.compile.ResultColumnList;
import org.apache.dearbaby.impl.sql.compile.StatementNode;
import org.apache.dearbaby.query.JdbcExecutor;
import org.apache.dearbaby.query.QueryMananger;
import org.apache.derby.ext.DearContext;
import org.apache.derby.iapi.sql.compile.Parser;

public class DearSelector {
	private StatementNode qt;
   
	public void bootstrap(String sql) {
		try{
			
			Parser ps =  DearContext.getParser();
			qt = (StatementNode) ps.parseStatement(sql);
	
			QueryMananger querys = new QueryMananger();
			querys.executor=new JdbcExecutor();
			qt.genQuery(querys); 
			
			qt.exeQuery();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public  Map  fetch(){
		int i=0;
		int j=0;
	
		while (qt.fetch()) {
			 
			if (qt.match()) {
				Map map=new HashMap();
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
						 
						AggregateNode agg=(AggregateNode)t._expression;
						 
						ColumnReference c=(ColumnReference)agg.operand;
						String name=t._underlyingName;
						if(name==null){
							name=c.getColumnName();
						}
						Object obj =qt.getColVal("#",name);
						map.put("#"+"."+name, obj);
					}
					
				}
				System.out.println("fetch...... "
						+ i
						+ " ,"
						+ map);
				
				i++;
				return map;
			}

			qt.fetchEnd();
 
		}
		return null;
	}
}
