package com.smartcity.redux.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartcity.redux.AirMapActivity;
import com.smartcity.redux.MainActivity;
import com.smartcity.redux.R;
import com.smartcity.redux.SettingsActivity;

public class MainFragment extends Fragment {
	
    // Notifications
    private NotificationManager mNotificationManager;
    int notificationID;
    int numMessages;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		//super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		View root = inflater.inflate(R.layout.fragment_main, null);
		
/*		Button testBtn = (Button)root.findViewById(R.id.testNotificationsButton);
		testBtn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				displayNotification();
			}
		});*/
		
		return root;
	}
	
protected void displayNotification(){
		
		//Invoke notifications service
		
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());
		
		mBuilder.setContentTitle("New Message");
		mBuilder.setContentText("You've received a new message!");
		mBuilder.setTicker("Ticker text");
		mBuilder.setSmallIcon(R.drawable.notification);
		
		mBuilder.setNumber(++numMessages);
		
		//Create intent for activity; TODO: this should trigger Messaging Center activity
		Intent resultIntent = new Intent(getActivity(),MainActivity.class);
//		TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
//		
//		//Add intent where notification was clicked to top of stack
//		stackBuilder.addParentStack(MainActivity.class);
//		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
//		
//		mBuilder.setContentIntent(resultPendingIntent);
		
		mNotificationManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
		
		//Set notification ID
		
		mNotificationManager.notify(notificationID,mBuilder.build());
	}
	
	protected void cancelNotification() {
		mNotificationManager.cancel(notificationID);
	}
	
	protected void updateNotification() {
		
		//Invoke notification service
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());
		
		mBuilder.setContentTitle("Updated Message");
		mBuilder.setContentText("You have a new message");
		mBuilder.setTicker("This is ticker text");
		mBuilder.setSmallIcon(R.drawable.notification);
		
		//Increment notification count
		
		mBuilder.setNumber(++numMessages);
		
		//Create explicit intent for an Activity in the app; TODO: this should trigger the Messaging Center activity
		Intent resultIntent = new Intent(getActivity(),MainActivity.class);
		
//		TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
//		stackBuilder.addParentStack(MainActivity.class);
//		
//		//Add intent where notification was clicked to top of stack
//		stackBuilder.addNextIntent(resultIntent);
//		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
//		
//		mBuilder.setContentIntent(resultPendingIntent);
		
		mNotificationManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
		
		//Update existing notification using same notification ID
		
		mNotificationManager.notify(notificationID,mBuilder.build());
		
	}
	
	public void startMapActivity(View view){
		Intent intent = new Intent(getActivity(),AirMapActivity.class);
		getActivity().startActivity(intent);
	}
	
	public void startSettingsActivity(View view){
		Intent intent = new Intent(getActivity(),SettingsActivity.class);
		getActivity().startActivity(intent);
	}

}
