package com.fyshadows.collegemate;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;







import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Admin_home_screen extends Activity {

     private DrawerLayout mDrawerLayout;
     private ListView mDrawerList;
     private ActionBarDrawerToggle mDrawerToggle;

     private CharSequence mDrawerTitle;
     private CharSequence mTitle;
     CustomDrawerAdapter adapter;

     List<DrawerItem> dataList;

  // Creating JSON Parser object
   
  
     ArrayList<HashMap<String, String>> productsList;
  
     // url to get all products list
     private static String url_all_products = "http://api.androidhive.info/android_connect/get_all_products.php";
  
     // JSON Node names
     private static final String TAG_SUCCESS = "success";
     private static final String TAG_PRODUCTS = "products";
     private static final String TAG_PID = "pid";
     private static final String TAG_NAME = "name";
  
   
     
     
     @SuppressLint("NewApi")
	@Override
     protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_admin_home_screen);

           // Initializing
           dataList = new ArrayList<DrawerItem>();
           mTitle = mDrawerTitle = getTitle();
           mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
           mDrawerList = (ListView) findViewById(R.id.left_drawer);

         

           // Add Drawer Item to dataList
           dataList.add(new DrawerItem("Home", R.drawable.ic_home));
           dataList.add(new DrawerItem("Chat", R.drawable.ic_chat));
           dataList.add(new DrawerItem("Profile", R.drawable.ic_profile));
           dataList.add(new DrawerItem("Notification", R.drawable.ic_notification));
           dataList.add(new DrawerItem("Discussion forum", R.drawable.ic_discussion));
           dataList.add(new DrawerItem("Settings", R.drawable.ic_action_settings));
           dataList.add(new DrawerItem("Help", R.drawable.ic_action_help));

           adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
                       dataList);

           mDrawerList.setAdapter(adapter);

          // mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

           getActionBar().setDisplayHomeAsUpEnabled(true);
           getActionBar().setHomeButtonEnabled(true);

           mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                       R.drawable.ic_drawer, R.string.drawer_open,
                       R.string.drawer_close) {
                 public void onDrawerClosed(View view) {
                       getActionBar().setTitle(mTitle);
                       invalidateOptionsMenu(); // creates call to
                                                                 // onPrepareOptionsMenu()
                 }

                 public void onDrawerOpened(View drawerView) {
                       getActionBar().setTitle(mDrawerTitle);
                       invalidateOptionsMenu(); // creates call to
                                                                 // onPrepareOptionsMenu()
                 }
           };

           mDrawerLayout.setDrawerListener(mDrawerToggle);

           if (savedInstanceState == null) {
                 SelectItem(0);
           }

     }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
           // Inflate the menu; this adds items to the action bar if it is present.
           getMenuInflater().inflate(R.menu.admin_home_screen, menu);
           return true;
     }

     public void SelectItem(int possition) {

           Fragment fragment = null;
           Bundle args = new Bundle();
           Intent intent;
           switch (possition) {
           case 0:
                 fragment = new FragmentOne();
                 args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
                             .getItemName());
                 args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(possition)
                             .getImgResID());
                 break;
           case 1:
        	   Intent a = new Intent(this, ChatHomeScreen.class);
               startActivity(a);
               break;
           case 2:
                 fragment = new FragmentThree();
                 args.putString(FragmentThree.ITEM_NAME, dataList.get(possition)
                             .getItemName());
                 args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList.get(possition)
                             .getImgResID());
                 break;
           case 3:
                 fragment = new FragmentOne();
                 args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
                             .getItemName());
                 args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(possition)
                             .getImgResID());
                 break;
           case 4:
                 fragment = new FragmentTwo();
                 args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
                             .getItemName());
                 args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(possition)
                             .getImgResID());
                 break;
           case 5:
                 fragment = new FragmentThree();
                 args.putString(FragmentThree.ITEM_NAME, dataList.get(possition)
                             .getItemName());
                 args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList.get(possition)
                             .getImgResID());
                 break;
           case 6:
                 fragment = new FragmentOne();
                 args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
                             .getItemName());
                 args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(possition)
                             .getImgResID());
                 break;
           case 7:
                 fragment = new FragmentTwo();
                 args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
                             .getItemName());
                 args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(possition)
                             .getImgResID());
                 break;
           case 8:
                 fragment = new FragmentThree();
                 args.putString(FragmentThree.ITEM_NAME, dataList.get(possition)
                             .getItemName());
                 args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList.get(possition)
                             .getImgResID());
                 break;
           case 9:
                 fragment = new FragmentOne();
                 args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
                             .getItemName());
                 args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(possition)
                             .getImgResID());
                 break;
           case 10:
                 fragment = new FragmentTwo();
                 args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
                             .getItemName());
                 args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(possition)
                             .getImgResID());
                 break;
           case 11:
                 fragment = new FragmentThree();
                 args.putString(FragmentThree.ITEM_NAME, dataList.get(possition)
                             .getItemName());
                 args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList.get(possition)
                             .getImgResID());
                 break;
           case 12:
                 fragment = new FragmentOne();
                 args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
                             .getItemName());
                 args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(possition)
                             .getImgResID());
                 break;
           default:
                 break;
           }

           fragment.setArguments(args);
           FragmentManager frgManager = getFragmentManager();
           frgManager.beginTransaction().replace(R.id.content_frame, fragment)
                       .commit();

           mDrawerList.setItemChecked(possition, true);
           setTitle(dataList.get(possition).getItemName());
           mDrawerLayout.closeDrawer(mDrawerList);

     }

     @Override
     public void setTitle(CharSequence title) {
           mTitle = title;
           getActionBar().setTitle(mTitle);
     }

     @Override
     protected void onPostCreate(Bundle savedInstanceState) {
           super.onPostCreate(savedInstanceState);
           // Sync the toggle state after onRestoreInstanceState has occurred.
           mDrawerToggle.syncState();
     }

     @Override
     public void onConfigurationChanged(Configuration newConfig) {
           super.onConfigurationChanged(newConfig);
           // Pass any configuration change to the drawer toggles
           mDrawerToggle.onConfigurationChanged(newConfig);
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
           // The action bar home/up action should open or close the drawer.
           // ActionBarDrawerToggle will take care of this.
           if (mDrawerToggle.onOptionsItemSelected(item)) {
                 return true;
           }

           return false;
     }

      // private class DrawerItemClickListener implements
        //         ListView.OnItemClickListener {
          // @Override
         //  public void onItemClick(AdapterView<?> parent, View view, int position,
           //            long id) {
             //    SelectItem(position);

        //   }
     //}
       public void Chat(View view)  {
	    	
   		Intent i = new Intent(this, ChatHomeScreen.class);
   		  startActivity(i);
	    }
       
       
      
       
     
}