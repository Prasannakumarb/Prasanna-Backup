package com.fyshadows.collegemate;

public class Masterusertable {
	static String _user_id;
    static String _user_name;
    static String _dob;
    static String _sex;
    static String _mail_ID;
    static String _mob_no;
    static String _city;
    static String _yearofjoining;
    static String _timestamp;
    static String _college_id;
    static String _college_name;
    static String _department_id;
    static String _flag;
    
    // Empty constructor
    public Masterusertable(){
         
    }
    // constructor
    public Masterusertable(String user_id, String name, String dob,String sex,String mail_id,String mob_no,String city,String flag){
        Masterusertable._user_id = user_id;
        Masterusertable._user_name = name;
        Masterusertable._dob = dob;
        Masterusertable._sex=sex;
        Masterusertable._mail_ID=mail_id;
        Masterusertable._city=city;
        Masterusertable._flag=flag;
        Masterusertable._mob_no=mob_no;
    }
    public Masterusertable(String user_id, String user_name, String dob){
    	Masterusertable._user_id = user_id;
    	Masterusertable._user_name = user_name;
    	Masterusertable._dob = dob;
    }
    public Masterusertable(String collegeid, String collegename, String user_id,String nothing){
    	 Masterusertable._college_id=collegeid;
         Masterusertable._college_name=collegename;
     	Masterusertable._user_id = user_id;
    }
 
    public String getuserid(){
        return _user_id;
    }
    
    public   void setuserid(String userid){
         Masterusertable._user_id=userid;
    }
    
   
    
    public static String getusername(){
        return _user_name;
    }
    public static String getdob(){
        return _dob;
    }
    public static String getsex(){
        return _sex;
    }
    public static String getmailid(){
        return _mail_ID;
    }
    public static String getcity(){
        return _city;
    }
    public static String getflag(){
        return _flag;
    }
    public static String getmobno(){
        return _mob_no;
    }
    
}
