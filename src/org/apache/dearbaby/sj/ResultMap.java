package org.apache.dearbaby.sj;

import java.util.HashMap;

public class ResultMap {
	HashMap m;
	
	public ResultMap(HashMap _m){
		m=_m;
	}
	
	public Object getObject(String alias,String col){
		String key=alias.toUpperCase()+"."+col.toUpperCase();
		return m.get(key);
	}
	
	public Object getAggrObject( String col){
		String key="#."+col.toUpperCase();
		return m.get(key);
	}
}
