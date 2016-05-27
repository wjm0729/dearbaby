package org.apache.dearbaby.util;

import org.apache.dearbaby.impl.sql.compile.AggregateNode;
import org.apache.dearbaby.impl.sql.compile.ColumnReference;
import org.apache.dearbaby.impl.sql.compile.ResultColumn;

public class QueryUtil {
	public static String getAggrColName(ResultColumn t){
		AggregateNode agg=(AggregateNode)t._expression;
		 
		ColumnReference c=(ColumnReference)agg.operand;
		 
		String alias = c._qualifiedTableName.tableName;
		String name=t._underlyingName;
		String cName = c.getColumnName(); 
		String fun=agg.aggregateName; 
		if(name==null){
			name= fun+"("+alias+"."+cName+")";
		}
		
		return name;
	}
}
