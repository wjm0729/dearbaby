package org.apache.dearbaby.sj;

import java.util.HashMap;
import java.util.Map;

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
		Map map=new HashMap();
		while (qt.fetch()) {
			 
			if (qt.match()) {
				ResultColumnList resultColumns= qt.getCols(); 
				for (Object o : resultColumns.v) {
					ResultColumn t = (ResultColumn) o;
					if (t._expression instanceof ColumnReference) {
						ColumnReference c=(ColumnReference)t._expression;
						String alias = c._qualifiedTableName.tableName;
						String cName = t.getSourceColumnName(); 
						Object obj =qt.qm.findFetchRow(alias).getCurrCol(cName);
						map.put(alias+"."+cName, obj);
						
					} 
					
				}
				System.out.println("fetch...... "
						+ i
						+ " ,"
						+ qt.qm.fetchRow.get(0).getCurrRow()
								.get("workId".toUpperCase())
						+ "  "
						+ qt.qm.fetchRow.get(0).getCurrRow()
								.get("workDate".toUpperCase()));
				
				i++;
				return map;
			}

			qt.fetchEnd();

		}
		return null;
	}
}
