package com.smartcity.redux.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.microsoft.windowsazure.notifications.NotificationsHandler;
import com.smartcity.redux.MainActivity;
import com.smartcity.redux.R;

public class AzureGCMHandler extends NotificationsHandler {
	
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	Context ctx;

	@Override
	public void onReceive(Context context, Bundle bundle) {
		Log.d("GCM","MESSAGE RECEIVED!!!");
	    ctx = context;
	    String nhMessage = bundle.getString("msg");
	    String nhPriority = bundle.getString("priority");

	    sendNotification(nhMessage);
	    
	    Log.d("GCM","Writing message to sqlite database.");
	    DatabaseHelper db = new DatabaseHelper(context);
	    db.insertMsg(nhMessage,nhPriority);
	}

	private void sendNotification(String msg) {
	    mNotificationManager = (NotificationManager)
	              ctx.getSystemService(Context.NOTIFICATION_SERVICE);

	    PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
	          new Intent(ctx, MainActivity.class), 0);

	    NotificationCompat.Builder mBuilder =
	          new NotificationCompat.Builder(ctx)
	          .setSmallIcon(R.drawable.ic_launcher)
	          .setContentTitle("Notification Hub Demo")
	          .setStyle(new NotificationCompat.BigTextStyle()
	                     .bigText(msg))
	          .setContentText(msg);

	     mBuilder.setContentIntent(contentIntent);
	     mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}

}