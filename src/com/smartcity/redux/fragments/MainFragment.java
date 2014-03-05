package com.smartcity.redux.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
=======
//import com.smartcity.redux.AirMapActivity;
>>>>>>> 05072e795744a3b6c5513d04c2c835ae796ebd08
import com.smartcity.redux.MainActivity;
import com.smartcity.redux.NoticeService;
import com.smartcity.redux.R;

public class MainFragment extends Fragment implements OnItemClickListener {

	// Notifications
	private NotificationManager mNotificationManager;
	int notificationID;
	int numMessages;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_main, null);

		GridView gridView = (GridView) root.findViewById(R.id.squareimagegrid);
		gridView.setAdapter(new SquareImageAdapter(getActivity()));
		gridView.setOnItemClickListener(this);
		
		//LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, new IntentFilter("my-event"));
		
		getActivity().startService(new Intent(getActivity(), NoticeService.class));

		/*
		 * Button testBtn =
		 * (Button)root.findViewById(R.id.testNotificationsButton);
		 * testBtn.setOnClickListener(new View.OnClickListener() {
		 * 
		 * public void onClick(View v) { displayNotification(); } });
		 */

		return root;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		IntentFilter filter = new IntentFilter(NoticeService.BROADCAST);
		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(onNotice, filter);
	}
	
	@Override
	public void onPause() {
		LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(onNotice);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// display view for selected nav drawer item
		// displayView(position);
		Item item = (Item) parent.getAdapter().getItem(position);
		Fragment fragment;
		try {
			fragment = item.fragmentClass.newInstance();
			android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
			getActivity().setTitle(item.name);
			// update selected item and title, then close the drawer
			// mDrawerList.setItemChecked(position, true);
			// mDrawerList.setSelection(position);
			// setTitle(navMenuTitles[position]);
			// mDrawerLayout.closeDrawer(mDrawerList);
		} catch (java.lang.InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void displayNotification() {

		// Invoke notifications service

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				getActivity());

		mBuilder.setContentTitle("New Message");
		mBuilder.setContentText("You've received a new message!");
		mBuilder.setTicker("Ticker text");
		mBuilder.setSmallIcon(R.drawable.notification);

		mBuilder.setNumber(++numMessages);

		// Create intent for activity; TODO: this should trigger Messaging
		// Center activity
		Intent resultIntent = new Intent(getActivity(), MainActivity.class);
		// TaskStackBuilder stackBuilder =
		// TaskStackBuilder.create(getActivity());
		//
		// //Add intent where notification was clicked to top of stack
		// stackBuilder.addParentStack(MainActivity.class);
		// PendingIntent resultPendingIntent =
		// stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
		//
		// mBuilder.setContentIntent(resultPendingIntent);

		mNotificationManager = (NotificationManager) getActivity()
				.getSystemService(Context.NOTIFICATION_SERVICE);

		// Set notification ID

		mNotificationManager.notify(notificationID, mBuilder.build());
	}

	protected void cancelNotification() {
		mNotificationManager.cancel(notificationID);
	}

	protected void updateNotification() {

		// Invoke notification service
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				getActivity());

		mBuilder.setContentTitle("Updated Message");
		mBuilder.setContentText("You have a new message");
		mBuilder.setTicker("This is ticker text");
		mBuilder.setSmallIcon(R.drawable.notification);

		// Increment notification count

		mBuilder.setNumber(++numMessages);

		// Create explicit intent for an Activity in the app; TODO: this should
		// trigger the Messaging Center activity
		Intent resultIntent = new Intent(getActivity(), MainActivity.class);

		// TaskStackBuilder stackBuilder =
		// TaskStackBuilder.create(getActivity());
		// stackBuilder.addParentStack(MainActivity.class);
		//
		// //Add intent where notification was clicked to top of stack
		// stackBuilder.addNextIntent(resultIntent);
		// PendingIntent resultPendingIntent =
		// stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
		//
		// mBuilder.setContentIntent(resultPendingIntent);

		mNotificationManager = (NotificationManager) getActivity()
				.getSystemService(Context.NOTIFICATION_SERVICE);

		// Update existing notification using same notification ID

		mNotificationManager.notify(notificationID, mBuilder.build());

	}
	
//	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//		
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			String message = intent.getStringExtra("message");
//			Log.d("BROADCAST_RCVR","Got message: " + message);
//			Toast.makeText(getActivity().getApplicationContext(), (String)message, 
//					   Toast.LENGTH_LONG).show();
//			//TODO: call addItems with the extras here once it works
//		}
//	};
	
	private BroadcastReceiver onNotice= new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			//notice.setText(new Data().toString());
			Log.d("BROADCAST_RCVR","Got message!");
		}
	};

	private class SquareImageAdapter extends BaseAdapter {
		private List<Item> items = new ArrayList<Item>();
		private LayoutInflater inflater;

		public SquareImageAdapter(Context context) {
			inflater = LayoutInflater.from(context);

			items.add(new Item(R.string.airmapButtonText, R.drawable.blue, AirMapFragment.class));
			//items.add(new Item("Item 2", R.drawable.cyan, ));
			// items.add(new Item("Item 3", R.drawable.yellow));
			// items.add(new Item("Item 4", R.drawable.orange));
			// items.add(new Item("Item 5", R.drawable.magenta));
			// items.add(new Item("Item 6", R.drawable.green));
			// items.add(new Item("Item 7", R.drawable.violet));
		}
		
		public void setItems(Item[] arr)
		{
			items.clear();
			for(int i=0;i<arr.length;i++)
			{
				items.add(arr[i]);
			}
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

			if (v == null) {
				v = inflater.inflate(R.layout.square_image_item, viewGroup,
						false);
				v.setTag(R.id.picture, v.findViewById(R.id.picture));
				v.setTag(R.id.text, v.findViewById(R.id.text));
			}

			picture = (ImageView) v.getTag(R.id.picture);
			name = (TextView) v.getTag(R.id.text);

			Item item = (Item) getItem(i);

			picture.setImageResource(item.drawableId);
			name.setText(item.name);

			return v;
		}

	}
<<<<<<< HEAD

	public class Item {
		final public int name;
		final public int drawableId;
		final public Class<? extends Fragment> fragmentClass;

		// make getters maybe?
		Item(int name, int drawableId,
				Class<? extends Fragment> fragmentClass) {
			this.name = name;
			this.drawableId = drawableId;
			this.fragmentClass = fragmentClass;
		}
	}

	public void startMapActivity(View view) {
		Intent intent = new Intent(getActivity(), AirMapFragment.class);
		getActivity().startActivity(intent);
	}

	public void startSettingsActivity(View view) {
		Intent intent = new Intent(getActivity(), SettingsFragment.class);
=======
	/**
	public void startMapActivity(View view){
		Intent intent = new Intent(getActivity(),AirMapActivity.class);
		getActivity().startActivity(intent);
	}
	**/
	
	public void startSettingsActivity(View view){
		Intent intent = new Intent(getActivity(),SettingsActivity.class);
>>>>>>> 05072e795744a3b6c5513d04c2c835ae796ebd08
		getActivity().startActivity(intent);
	}

}
