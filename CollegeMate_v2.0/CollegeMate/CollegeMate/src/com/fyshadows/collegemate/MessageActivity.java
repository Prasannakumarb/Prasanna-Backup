package com.fyshadows.collegemate;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;

/**
 * MessageActivity is a main Activity to show a ListView containing Message items
 * 
 * @author Adil Soomro
 *
 */
public class MessageActivity extends ListActivity {
	/** Called when the activity is first created. */

	ArrayList<MessageTable> messages;
	ChatCustomAdpter adapter;
	EditText text;
	static Random rand = new Random();	
	static String sender;
	String userid;
	String username;
	String picpath;
	 Collegemate_DB db = new Collegemate_DB(this);
	List<MessageTable> list = new ArrayList<MessageTable>();
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		 Bundle bundle = getIntent().getExtras();
		 userid = bundle.getString("userid"); 
		 username = bundle.getString("username"); 
		 picpath = bundle.getString("PicPath");
		     
		text = (EditText) this.findViewById(R.id.text);

		//sender = Utility.sender[rand.nextInt( Utility.sender.length-1)];
		this.setTitle(username);
		
		 if (android.os.Build.VERSION.SDK_INT > 9) {
	            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	            StrictMode.setThreadPolicy(policy);
	          }
		
		
		Log.i("a","calling get chat");
        list = db.Getchat(userid);
        
       if(!list.isEmpty())
       {
		adapter = new ChatCustomAdpter(this, list);
		setListAdapter(adapter);
		adapter.notifyDataSetChanged();
       }
	
	}
	public void sendMessage(View v) throws URISyntaxException
	{
		String newMessage = text.getText().toString().trim(); 
		if(newMessage.length() > 0)
		{
			
			//addNewMessage(new MessageTable(newMessage, true));
			 db.addChat(new MessageTable(userid,newMessage,0));
			 String user_id=userid;
			 String Chat_Text=newMessage;
			 int IsMine=0;
			 Date date = new Date();
			 String time_stamp = String.valueOf(date);
			 list.add(get1(user_id,Chat_Text,IsMine,time_stamp));
			 adapter.notifyDataSetChanged();
			//new SendMessage().execute();
			 Chat_Text=newMessage.replaceAll(" ", "%20");
			 String link =  "http://fyshadows.com/CollegeMate/SendChat.php?userid="+user_id+"&message="+Chat_Text;
		        Log.i("a",link);
		        HttpClient client = new DefaultHttpClient();
		        HttpGet request = new HttpGet();
		        request.setURI(new URI(link)); 
		        try {
					HttpResponse response = client.execute(request);
					Log.i("hi","done");
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		}
		
	}
	private MessageTable get1(String user_id,String Chat_Text,int IsMine,String time_stamp) {
		 return new MessageTable(user_id,Chat_Text,IsMine,time_stamp);
	}
	/**private class SendMessage extends AsyncTask<Void, String, String>
	{
		@Override
		protected String doInBackground(Void... params) {
			try {
				Thread.sleep(2000); //simulate a network call
			}catch (InterruptedException e) {
				e.printStackTrace();
			}

			this.publishProgress(String.format("%s started writing", sender));
			try {
				Thread.sleep(2000); //simulate a network call
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.publishProgress(String.format("%s has entered text", sender));
			try {
				Thread.sleep(3000);//simulate a network call
			}catch (InterruptedException e) {
				e.printStackTrace();
			}


			return Utility.messages[rand.nextInt(Utility.messages.length-1)];


		}
		@Override
		public void onProgressUpdate(String... v) {

			if(messages.get(messages.size()-1).isStatusMessage)//check wether we have already added a status message
			{
				messages.get(messages.size()-1).setMessage(v[0]); //update the status for that
				adapter.notifyDataSetChanged(); 
				getListView().setSelection(messages.size()-1);
			}
			else{
				addNewMessage(new MessageTable(true,v[0])); //add new message, if there is no existing status message
			}
		}
		@Override
		protected void onPostExecute(String text) {
			if(messages.get(messages.size()-1).isStatusMessage)//check if there is any status message, now remove it.
			{
				messages.remove(messages.size()-1);
			}

			addNewMessage(new MessageTable(text, false)); // add the orignal message from server.
		}


	}
	void addNewMessage(MessageTable m)
	{
		messages.add(m);
		adapter.notifyDataSetChanged();
		getListView().setSelection(messages.size()-1);
	} **/
}