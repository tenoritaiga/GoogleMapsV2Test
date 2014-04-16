package com.smartcity.redux.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartcity.redux.AirQualityActivity;
import com.smartcity.redux.DirectionsInputActivity;
import com.smartcity.redux.GuideActivity;
import com.smartcity.redux.Hoboken311Activity;
import com.smartcity.redux.InboxActivity;
//import com.smartcity.redux.AirMapActivity;
import com.smartcity.redux.MainActivity;
import com.smartcity.redux.R;
import com.smartcity.redux.SettingsActivity;
import com.smartcity.redux.SustainabilityCategoryActivity;

public class MainFragment extends Fragment implements OnItemClickListener {
	
    // Notifications
    private NotificationManager mNotificationManager;
    int notificationID;
    int numMessages;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		//super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		View root = inflater.inflate(R.layout.fragment_main, null);
		
		GridView gridView = (GridView)root.findViewById(R.id.squareimagegrid);
		gridView.setAdapter(new SquareImageAdapter(getActivity()));
		
		gridView.setOnItemClickListener(this);
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
	
    public static class Item {
        final String name;
        final int drawableId;
        
        public Class<? extends Activity> className;

        Item(String name, int drawableId, Class classToLoad) {
            this.name = name;
            this.drawableId = drawableId;
            this.className = classToLoad;
        }
    }
	
	private static class SquareImageAdapter extends BaseAdapter {
	    private List<Item> items = new ArrayList<Item>();
	    private LayoutInflater inflater;

	    public SquareImageAdapter(Context context) {
	        inflater = LayoutInflater.from(context);

	        items.add(new Item("Directions",       R.drawable.square_maps, DirectionsInputActivity.class));
	        items.add(new Item("City Guide",   R.drawable.square_coffee, GuideActivity.class));
	        items.add(new Item("Sustainability",      R.drawable.square_leaf, SustainabilityCategoryActivity.class));
	        items.add(new Item("Environmental Maps",      R.drawable.square_cloud, AirQualityActivity.class));
	        items.add(new Item("Hoboken 311",      R.drawable.square_flag, Hoboken311Activity.class));
	        items.add(new Item("Alerts",     R.drawable.square_alerts, InboxActivity.class));
	        items.add(new Item("Settings", R.drawable.square_settings, SettingsActivity.class));

	    }

	    @Override
	    public int getCount() {
	        return items.size();
	    }

	    @Override
	    public Object getItem(int i) {
	        return items.get(i);
	    }

	    @Override
	    public long getItemId(int i) {
	        return items.get(i).drawableId;
	    }

	    @Override
	    public View getView(int i, View view, ViewGroup viewGroup) {
	        View v = view;
	        ImageView picture;
	        TextView name;

	        if(v == null) {
	            v = inflater.inflate(R.layout.square_image_item, viewGroup, false);
	            v.setTag(R.id.picture, v.findViewById(R.id.picture));
	            v.setTag(R.id.text, v.findViewById(R.id.text));
	        }

	        picture = (ImageView)v.getTag(R.id.picture);
	        name = (TextView)v.getTag(R.id.text);

	        Item item = (Item)getItem(i);

	        picture.setImageResource(item.drawableId);
	        name.setText(item.name);

	        return v;
	    }


	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		Item item = (Item) parent.getAdapter().getItem(position);
		
		Intent intent = new Intent(getActivity(), item.className);
		startActivity(intent);
	}
	
	public void startSettingsActivity(View view){
		Intent intent = new Intent(getActivity(),SettingsActivity.class);
		getActivity().startActivity(intent);
	}

}
