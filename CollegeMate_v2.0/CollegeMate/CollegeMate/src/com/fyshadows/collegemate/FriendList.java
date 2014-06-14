package com.fyshadows.collegemate;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class FriendList extends ListActivity {

	 List<FriendInfoTable> list = new ArrayList<FriendInfoTable>();
	 Collegemate_DB db = new Collegemate_DB(this);
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        setContentView(R.layout.activity_friend_list);
 
       
        list.clear();
       
       
         
        final ProgressDialog dialog = ProgressDialog.show(FriendList.this,"", "Gettting values from DB", true);
        //new    Thread   (new Runnable() {
          //  public void run() {
        List<FriendInfoTable> list;
        list = db.getFriendDetails();
                //list.add(sData);
                 
                dismissDialog(dialog);
                
        /*************************** GOT data from Server ********************************************/
        Log.i("c","into adapter");
        FriendListArrayAdapter adapter = new FriendListArrayAdapter(this,list);
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
        
                ListView lv = getListView();
                
                // listening to single list item on click
                lv.setOnItemClickListener(new OnItemClickListener() {
                  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                	 
                	  FriendInfoTable FriendInfo = FriendListArrayAdapter.getFriendInfoTablePosition(position);
                	   String user_id;
                	   String user_name;
                	   String PicPath;
                	    user_id=FriendInfo.getuserid();
                	    user_name=FriendInfo.getusername();
                	    PicPath=FriendInfo.getuserpicpath();
                	   
              		  //Create the bundle
              		  Bundle bundle = new Bundle(); 
              		  Log.i("bundle",user_name);
              		 bundle.putString("username", user_name); 
              		 bundle.putString("userid", user_id); 
              		 bundle.putString("PicPath", PicPath);
              		Log.i("userid",user_id);
              		Log.i("PicPath",PicPath);
              		
              		Intent i = new Intent(FriendList.this, MessageActivity.class);
              		i.putExtras(bundle);
          		  //Fire that second activity
          		  startActivity(i);

                      }
                	
                  
                });
                
	}
    public void dismissDialog(final ProgressDialog dialog){
        runOnUiThread(new Runnable() {
            public void run() {
                dialog.dismiss();
            }
        });
    }
  
}
