package org.apache.dearbaby.sj;

import java.util.Map;

public class DearTest {

	 
/*
	String sql = "";
	 
	sql = "select a.name,a.iss from news a ,comany c where a.id in(select b.iid from cust b) and a.ikey=c.ikey";
	sql = "select a.workid as wid,(select b.kid from cust b where b.id=a.id ) as kid FROM WorkInforParameter  a ";
	sql = "select a.workid,a.doctorName from WorkInforParameter a ,DoctorInforParameter c where a.doctorid=c.doctorid  ";
	sql = "select a.workid,c.doctorName from WorkInforParameter a left join DoctorInforParameter c on a.doctorid=c.doctorid   ";
	sql = "SELECT a.workid FROM WorkInforParameter  a , OrderListParameter e WHERE a.id IN(SELECT b.id FROM DoctorInforParameter b WHERE a.DoctorId=b.DoctorId AND b.DeptId IN(SELECT c.DeptId FROM DeptInforParameter c WHERE c.DeptName=a.DeptName)) or e.WorkId=a.WorkId ";
*/
	public static void run3() {
		String sql="";
		sql = "SELECT  a.doctorName FROM DoctorInforParameter a WHERE a.doctorid IN (SELECT c.doctorid FROM WorkInforParameter c)";
		sql="SELECT a.doctorName FROM WorkInforParameter  a , doctorinforparameter b WHERE  a.DoctorId=b.DoctorId  OR a.Id>1380";
		//sql = "SELECT a.workid FROM WorkInforParameter  a , OrderListParameter e WHERE a.id IN(SELECT b.id FROM DoctorInforParameter b WHERE a.DoctorId=b.DoctorId AND b.DeptId IN(SELECT c.DeptId FROM DeptInforParameter c WHERE c.DeptName=a.DeptName)) or e.WorkId=a.WorkId ";
		
		DearSelector selector =new DearSelector();
		 selector.bootstrap(sql);
		 while(true){
			 Map map=selector.fetch();
			 if(map==null){
				 break;
			 }
			 System.out.println("map....  "+map);
		 }
	}

	public static void main(String[] args) {
		run3();
	}

}
