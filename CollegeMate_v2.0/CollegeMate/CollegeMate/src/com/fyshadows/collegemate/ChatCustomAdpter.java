package com.fyshadows.collegemate;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fyshadows.collegemate.MyCustomArrayAdapter.ViewHolder;

public class ChatCustomAdpter extends ArrayAdapter<MessageTable>  {

	 private final Activity context;
	 private  static List<MessageTable> list=null;
	 private int Check=0;
	 
	    public ChatCustomAdpter(Activity context, List<MessageTable> list) {
	        super(context, R.layout.sms_row, list);
	        this.context = context;
	        this.list = list;
	    }
	    @Override
	    public int getCount() {
	    	Log.i("a","a"+list.size());
	        return list.size();
	    }
	    
	    public static  MessageTable getMessageTablePosition(int position)
	    {
	        return list.get(position);
	    }
	    
	   
	 
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View view = null;
	        Log.i("a","into get view");
	        if (convertView == null) {
	        	
	            LayoutInflater inflator = context.getLayoutInflater();
	            view = inflator.inflate(R.layout.sms_row, null);
	            final ViewHolder viewHolder = new ViewHolder();
	            viewHolder.text = (TextView) view.findViewById(R.id.message_text);
	            viewHolder.text.setTextColor(Color.BLACK);
	            view.setTag(viewHolder);
	        } else {
	            view = convertView;
	        }
	        ViewHolder holder = (ViewHolder) view.getTag();
	        holder.text.setText(list.get(position).getMessage());

	        if(list.get(position).isMine()==Check)
			{
				holder.text.setBackgroundResource(R.drawable.speech_bubble_green);	
				//lp.gravity = Gravity.RIGHT;
				//holder.text.setTextColor(R.color.Mytextcolor);	
			}
			//If not mine then it is from sender to show orange background and align to left
			else
			{
				holder.text.setBackgroundResource(R.drawable.speech_bubble_orange);
				//lp.gravity = Gravity.RIGHT;
				//holder.text.setTextColor(R.color.Histextcolor);	
			}
	        //holder.text.setLayoutParams(lp);
	        return view;
	    }
	    
	    static class ViewHolder {
	       
			protected TextView text;
	       
	    }
	    
	  
}
