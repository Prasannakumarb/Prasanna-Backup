package com.fyshadows.collegemate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import java.util.List;
import android.view.View;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ChatHomeScreen    extends ListActivity  {
	 final List<Model> list = new ArrayList<Model>();
	 Collegemate_DB db = new Collegemate_DB(this);
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        setContentView(R.layout.chatlist);
 
       
        list.clear();
       
        final GetDataFromDB getvalues = new GetDataFromDB();
         
        final ProgressDialog dialog = ProgressDialog.show(ChatHomeScreen.this,
                "", "Gettting values from DB", true);
        //new    Thread   (new Runnable() {
          //  public void run() {
                String response = getvalues.getImageURLAndDesciptionFromDB();
                System.out.println("Response : This is  " + response);
                 
                dismissDialog(dialog);
                if (!response.equalsIgnoreCase("")) {
                    if (!response.equalsIgnoreCase("error")) {
                        dismissDialog(dialog);
                         
                        // Got the response, now split it to get the image Urls and description
                        String all[] = response.split("##"); 
                        for(int k = 0; k < all.length; k++){
                            String urls_and_desc[] = all[k].split(","); //  urls_and_desc[0] contains image url and [1] -> description.
                            Log.i("p","adding image to listview");
                            Log.i("p",urls_and_desc[1]);
                            Log.i("p",urls_and_desc[0]);
                            list.add(get(urls_and_desc[1],urls_and_desc[0],urls_and_desc[2]));
                      //  }
                    //}
                     
               // } else {
                //    dismissDialog(dialog);
                }
            }
      //  }).start();
        /*************************** GOT data from Server ********************************************/
        Log.i("c","into adapter");
        ArrayAdapter<Model> adapter = new MyCustomArrayAdapter(this, list);
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
        
       
        
    }
                
                ListView lv = getListView();
                lv.setOnItemClickListener(new OnItemClickListener() {
                  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {              	  
                	  Model model = MyCustomArrayAdapter.getModelPosition(position);
                	  String userid;
                	  String Username;
                	  String Picpath;
                	  userid=model.getUser_id();
                	  Username=model.getName();
                	  Picpath=model.getURL();     
                	  
 //Storing the image of the friend into Sdcard
                	  ///to get the pic name
                	 int pathlength= Picpath.length();
                	  String picname = Picpath.substring(46, pathlength); ;
                	  Log.i("picname baby",picname );
                      URL url = null;
					try {
						url = new URL (Picpath);
					} catch (MalformedURLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
                      InputStream input = null;
					try {
						input = url.openStream();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
                      try {
                          //The sdcard directory e.g. '/sdcard' can be used directly, or 
                          //more safely abstracted with getExternalStorageDirectory()
                          File storagePath = Environment.getExternalStorageDirectory();
                          OutputStream output = new FileOutputStream (new File(storagePath + "/collegemate/Profilepic/FriendsProfPic" + File.separator ,picname));
                          try {
                              int aReasonableSize=50;
							byte[] buffer = new byte[aReasonableSize];
                              int bytesRead = 0;
                              while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                                  output.write(buffer, 0, bytesRead);
                              }
                          } catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
                              try {
								output.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                          }
                      } catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
                          try {
							input.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                      }
                      Log.i("a","saved");
  //--------------------------------                  	  
                	  
                	  
                	  db.addfriend(new FriendInfoTable(userid,Username,picname));
                	  
                	//  Log.d("a",FriendInfoTable._user_id);
          			//Log.d("a",FriendInfoTable._user_name);
          		
                	  
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
    private Model get(String s, String url, String user_id) {
        return new Model(s, url,user_id);
    }
}
