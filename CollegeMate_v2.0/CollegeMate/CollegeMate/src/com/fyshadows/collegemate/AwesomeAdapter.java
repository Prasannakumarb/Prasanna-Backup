package com.fyshadows.collegemate;

import java.util.ArrayList;
import java.util.List;


import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public  class AwesomeAdapter extends BaseAdapter  {
	private Context mContext;
	private List<MessageTable> mMessages;
	



	public AwesomeAdapter(Context context, List<MessageTable> messages) {
		super();
		this.mContext = context;
		this.mMessages = messages;
	}
	@Override
	public int getCount() {
		return mMessages.size();
	}
	@Override
	public Object getItem(int position) {		
		return mMessages.get(position);
	}
	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//Message message = (Message) this.getItem(position);

		ViewHolder holder; 
		if(convertView == null)
		{
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.sms_row, parent, false);
			holder.message = (TextView) convertView.findViewById(R.id.message_text);
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		
		MessageTable msg=new MessageTable();
		Log.i("this is msg",msg.getMessage());
		holder.message.setText(msg.getMessage());

		//LayoutParams lp = (LayoutParams) holder.message.getLayoutParams();
		//check if it is a status message then remove background, and change text color.
		if(MessageTable.isStatusMessage())
		{
			holder.message.setBackgroundDrawable(null);
			holder.gravity = Gravity.RIGHT;
			holder.message.setTextColor(R.color.textFieldColor);
		}
		else
		{		
			int Check = 0;
			//Check whether message is mine to show green background and align to right
			//if(list.get(position).isMine()==Check)
			//{
				//holder.message.setBackgroundResource(R.drawable.speech_bubble_green);
				///holder.gravity = Gravity.RIGHT;
				//holder.message.setTextColor(R.color.Mytextcolor);	
	//		}
			//If not mine then it is from sender to show orange background and align to left
		/**	else
			{
				holder.message.setBackgroundResource(R.drawable.speech_bubble_orange);
				//lp.gravity = Gravity.LEFT;
				holder.message.setTextColor(R.color.Histextcolor);	
			}
			//holder.message.setLayoutParams(lp);
			**/
		}
		return convertView;
	}
	private static class ViewHolder
	{
		public int gravity;
		TextView message;
	}

	@Override
	public long getItemId(int position) {
		//Unimplemented, because we aren't using Sqlite.
		return position;
	}

}
