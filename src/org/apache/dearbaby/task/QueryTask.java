package org.apache.dearbaby.task;

import org.apache.dearbaby.query.QueryMananger;
import org.apache.dearbaby.query.SinQuery;

public class QueryTask implements Runnable{
	SinQuery sinQuery ;
	QueryMananger qm ;
	public QueryTask(SinQuery _sin,QueryMananger _qm ){
		sinQuery=_sin;
		qm=_qm;
	}
	public void run(){
		try{
			sinQuery.exeSelect();
		}finally{
			qm.getTaskCtrl().finishOne();
		}
		
	}
	
}
