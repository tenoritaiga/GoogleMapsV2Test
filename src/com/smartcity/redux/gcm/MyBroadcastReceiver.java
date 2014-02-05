package com.smartcity.redux.gcm;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.smartcity.redux.MainActivity;
import com.smartcity.redux.R;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class MyBroadcastReceiver extends BroadcastReceiver {

	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	Context ctx;

	@Override
	public void onReceive(Context context, Intent intent) {
	GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
	        ctx = context;
	        String messageType = gcm.getMessageType(intent);
	        if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
	            sendNotification("Send error: " + intent.getExtras().toString());
	        } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
	            sendNotification("Deleted messages on server: " + 
	                    intent.getExtras().toString());
	        } else {
	            sendNotification("Received: " + intent.getExtras().toString());
	            
	            //EXPERIMENTAL: Write received message to SQLite database
	            DatabaseHelper db = new DatabaseHelper(context);
	            db.insertMsg(intent.getExtras().toString());
	        }
	        setResultCode(Activity.RESULT_OK);
	}

	private void sendNotification(String msg) {
	mNotificationManager = (NotificationManager)
	              ctx.getSystemService(Context.NOTIFICATION_SERVICE);

	      PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
	          new Intent(ctx, MainActivity.class), 0);

	      NotificationCompat.Builder mBuilder =
	          new NotificationCompat.Builder(ctx)
	          .setSmallIcon(R.drawable.notification)
	          .setContentTitle("Notification Hub Demo")
	          .setStyle(new NotificationCompat.BigTextStyle()
	                     .bigText(msg))
	          .setContentText(msg);

	     mBuilder.setContentIntent(contentIntent);
	     mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}

}
