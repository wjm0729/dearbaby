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
		sql="SELECT a.doctorName  FROM WorkInforParameter  a , doctorinforparameter b WHERE  a.DoctorId=b.DoctorId  OR a.Id>1380";
		sql="SELECT a.doctorName  FROM WorkInforParameter  a , doctorinforparameter b WHERE  a.doctorName=b.DoctorId  OR a.Id>4380";
		sql="SELECT a.doctorName  FROM WorkInforParameter  a   WHERE  a.DoctorId in (select b.DoctorId from doctorinforparameter b)  ";
		sql="SELECT a.doctorId, sum(a.flag)    FROM WorkInforParameter  a   WHERE  a.DoctorId in (select b.DoctorId from doctorinforparameter b)   ";
		sql="SELECT a.doctorId,  (select b.doctorName From doctorinforparameter b Where a.doctorId=b.doctorId) as name   FROM WorkInforParameter  a   ";
		sql="SELECT a.doctorId   , b.doctorName  FROM WorkInforParameter  a  , (select  c.doctorName , c.doctorId From doctorinforparameter c where c.doctorId='222' ) as b  where    a.doctorId=b.doctorId";
		sql = "SELECT a.workid,c.doctorName FROM WorkInforParameter a  LEFT JOIN (SELECT d.doctorid,d.doctorName FROM  DoctorInforParameter d  WHERE d.id>120 ) c ON a.doctorid=c.doctorid      ";
	//	sql = " SELECT d.doctorid,d.doctorName FROM  DoctorInforParameter d  WHERE d.id>250  AND d.id<70  ";
	
		
		//sql="SELECT a.doctorName from DoctorInforParameter  a group by a.doctorid";
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
