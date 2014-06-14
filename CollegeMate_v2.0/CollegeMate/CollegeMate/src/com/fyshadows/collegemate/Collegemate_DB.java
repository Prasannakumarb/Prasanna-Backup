package com.fyshadows.collegemate;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Collegemate_DB extends SQLiteOpenHelper  {
	public Collegemate_DB(Context context) {
        super(context, "collegemate_db", null, 1);
    }

	@Override
	public void onCreate(SQLiteDatabase myDB) {
		// TODO Auto-generated method stub
	

		  /* Create a Database. */
		
		   myDB.execSQL("CREATE TABLE cm_mob_masteruserdetails(user_id varchar(100) PRIMARY KEY,GCM_registration_id TEXT,user_NAME TEXT,dob varchar(100),sex CHAR(50),"
				+"mail_id varchar(100),mob_no varchar(100),city varchar(100),yearofjoining varchar(100),college_id varchar(100),college_name varchar(200),"
				+ "department_id varchar(100),flag char(10),timestamp varchar(100));");	
		   
		   myDB.execSQL( "CREATE TABLE cm_mob_Friendinfo(user_id varchar(100) PRIMARY KEY,"
				   +"user_NAME varchar(100)   , image_path  varchar(250),"
				+"online varchar(10),timestamp varchar(100)"
				+");");
		   myDB.execSQL("CREATE TABLE cm_mob_chat(id INTEGER PRIMARY KEY, user_id varchar(100) ,Chat_Text varchar,Mine int,"
					+ "timestamp varchar(100));");	
		               
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
    // Adding new contact
public void registeradmin(Masterusertable Masterusertable) {
	Log.d("a: ", "into registration .."); 
    SQLiteDatabase db = this.getWritableDatabase();
 
    ContentValues values = new ContentValues();
    
    values.put("user_id" , Masterusertable.getuserid()); 
    values.put("user_NAME",com.fyshadows.collegemate.Masterusertable.getusername());
    values.put("dob",com.fyshadows.collegemate.Masterusertable.getdob());
    values.put("sex",com.fyshadows.collegemate.Masterusertable.getsex());
    values.put("mob_no",com.fyshadows.collegemate.Masterusertable.getmobno());
    values.put("mail_id",com.fyshadows.collegemate.Masterusertable.getmailid());
    values.put("city",com.fyshadows.collegemate.Masterusertable.getcity());
    values.put("flag",com.fyshadows.collegemate.Masterusertable.getflag());
    
 
    // Inserting Row
    db.insert("cm_mob_masteruserdetails", null, values);
    Log.d("a: ", "done registration ..");
    db.close(); // Closing database connection
}

//updating the college name and college table once he registered for college
public void updatecollegedetails(Masterusertable Masterusertable) {
	
    SQLiteDatabase db = this.getWritableDatabase();
SQLiteDatabase db1 = this.getWritableDatabase();

ContentValues values = new ContentValues();
values.put("college_id", com.fyshadows.collegemate.Masterusertable._college_id);
values.put("college_name", com.fyshadows.collegemate.Masterusertable._college_name);

// updating row
 db.update("cm_mob_masteruserdetails", values, "user_id" + " = ?",
        new String[] { String.valueOf(Masterusertable.getuserid()) });
 Log.d("a: ", "done updation ..");
 db.close(); // Closing database connection
}
//Getting single contact
Masterusertable getuserdetails(String user_id) {
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursor = db.query("cm_mob_masteruserdetails", new String[] { "user_id",
    		"user_NAME", "dob","college_id","college_name" }, "user_id" + "=?",
            new String[] { String.valueOf(user_id) }, null, null, null, null);
    if (cursor != null)
        cursor.moveToFirst();

    Masterusertable userdetails = new Masterusertable(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(2));
    // return contact
    return userdetails;
}


//Storing friend info details
public void addfriend(FriendInfoTable FriendInfoTable) {
	 String CheckID=CheckId(FriendInfoTable.getuserid());
	if(CheckID=="True" )
	{
		Log.i("a","True");
    SQLiteDatabase db = this.getWritableDatabase();
 
    ContentValues values = new ContentValues();
    Log.i("a",FriendInfoTable.getuserid());
    Log.i("a",FriendInfoTable.getusername());
    Log.i("a",FriendInfoTable.getuserpicpath());
    values.put("user_id" , FriendInfoTable.getuserid()); 
    values.put("user_NAME",FriendInfoTable.getusername());
    values.put("image_path",FriendInfoTable.getuserpicpath());    
 
    // Inserting Row
   long rowid= db.insert("cm_mob_Friendinfo", null, values);
   Log.d("a: ", Long.toString(rowid));
    Log.d("a: ", "done Storing ..");
    db.close(); // Closing database connection
    }
}

//Storing chat table into mobile db
public void addChat(MessageTable MessageTable) {

		Log.i("a","True");
  SQLiteDatabase db = this.getWritableDatabase();

  ContentValues values = new ContentValues();
 
  values.put("user_id" , MessageTable.getuserid()); 
  values.put("Chat_Text",MessageTable.getMessage());
  values.put("Mine",MessageTable.isMine());    

  // Inserting Row
 long rowid= db.insert("cm_mob_chat", null, values);
 Log.d("a: ", Long.toString(rowid));
  Log.d("a: ", "done Storing ..");
  db.close(); // Closing database connection
  }


//Getting ID value if present
public String CheckId(String id){

  String selectQuery = "SELECT user_NAME FROM cm_mob_Friendinfo WHERE  user_id="+id; 

  SQLiteDatabase db = this.getReadableDatabase();
  Cursor cursor = db.rawQuery(selectQuery, null);

  if (null != cursor && cursor.moveToFirst()) {
  	return "False";
  }
  else
  {
  	return "True";
  }
  }
//Getting the chat from table of the user
public List<MessageTable>  Getchat(String id){
	List<MessageTable> list  = new ArrayList<MessageTable>();
	  String selectQuery = "SELECT user_id ,Chat_Text,Mine,timestamp FROM cm_mob_chat where user_id='"+id+"' order by timestamp asc"; 
Log.i("into","into get chat");
Log.i("into",selectQuery);
	  SQLiteDatabase db = this.getReadableDatabase();
	  Cursor cursor = db.rawQuery(selectQuery, null);
  
	  if (cursor.moveToFirst())
	  {
	Log.i("f","not null");
	  int userid = cursor.getColumnIndex("user_id");
	  int ChatText = cursor.getColumnIndex("Chat_Text");
	  int Mine = cursor.getColumnIndex("Mine");
	  int timestamp = cursor.getColumnIndex("timestamp");
	  
	 
	 // cursor.moveToFirst();
	  if (cursor != null) {
	      do {
	    	  Log.i("f","into cursor");
	    	  Log.i("f",cursor.getString(userid));
	    	  Log.i("f",cursor.getString(ChatText));
	    	  Log.i("f",String.valueOf(cursor.getInt(Mine)));
	          String user_id = cursor.getString(userid);
	          String Chat_Text = cursor.getString(ChatText);
	          int IsMine= cursor.getInt(Mine);
	          String time_stamp= cursor.getString(timestamp);  
	          list.add(get1(user_id,Chat_Text,IsMine,time_stamp));
	        
	         
	      } while (cursor.moveToNext());
	  }
	
	  
	  
	  }
	   
	  return list;
}
//Getting friend info from the mobile database
List<FriendInfoTable>  getFriendDetails() {
	List<FriendInfoTable> list  = new ArrayList<FriendInfoTable>();

  SQLiteDatabase db = this.getReadableDatabase();

  Cursor cursor = db.rawQuery("select user_id,user_NAME,image_path from cm_mob_Friendinfo", null) ;   
  if (cursor != null)
      cursor.moveToFirst();
Log.i("f","not null");
  int userid = cursor.getColumnIndex("user_id");
  int username = cursor.getColumnIndex("user_NAME");
  int Imagepath = cursor.getColumnIndex("image_path");
  
 
 // cursor.moveToFirst();
  if (cursor != null) {
      do {
    	 
          String id = cursor.getString(userid);
          String name = cursor.getString(username);
          String path= cursor.getString(Imagepath);
          Log.i("userid",id);
          Log.i("username",name);   
          Log.i("path",path); 
          list.add(get(id,name,path));
        
         
      } while (cursor.moveToNext());
  }
  db.close(); 
  return list;
}

private FriendInfoTable get(String id, String name, String path) {
	 return new FriendInfoTable(id, name,path);
}

private MessageTable get1(String user_id,String Chat_Text,int IsMine,String time_stamp) {
	 return new MessageTable(user_id,Chat_Text,IsMine,time_stamp);
}

}
