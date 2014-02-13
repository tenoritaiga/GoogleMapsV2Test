package com.smartcity.redux.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.smartcity.redux.MainActivity;
import com.smartcity.redux.R;

public class MainFragment extends Fragment {
	
    // Notifications
    private NotificationManager mNotificationManager;
    int notificationID;
    int numMessages;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_main, null);
		
		GridView gridView = (GridView)root.findViewById(R.id.squareimagegrid);
		gridView.setAdapter(new SquareImageAdapter(getActivity()));
		gridView.setOnItemClickListener(new GridClickListener());
		
/*		Button testBtn = (Button)root.findViewById(R.id.testNotificationsButton);
		testBtn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				displayNotification();
			}
		});*/
		
		return root;
	}
	
	
	//Set a click listener for the grid
	private class GridClickListener implements
	ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
		}
	
	//Swap fragments when grid item clicked
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new MainFragment();
			break;
		case 1:
			//should be InboxFragment, change this back
			fragment = new MainFragment();
			break;
		case 2:
			fragment = new MapCategoryFragment();
			break;
		case 3:
			fragment = new SustainabilityCategoryFragment();
			break;
		case 4:
			fragment = new CityCategoryFragment();
			break;
		case 5:
			fragment = new Hoboken311Fragment();
			break;
		case 6:
			fragment = new ProfileFragment();
			break;
		case 7:
			fragment = new EmergencyCategoryFragment();
		default:
			break;
		}

		if (fragment != null) {
			android.app.FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
			// update selected item and title, then close the drawer
//			mDrawerList.setItemChecked(position, true);
//			mDrawerList.setSelection(position);
//			setTitle(navMenuTitles[position]);
//			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainFragment", "Error in creating fragment");
		}
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
	
	private class SquareImageAdapter extends BaseAdapter {
	    private List<Item> items = new ArrayList<Item>();
	    private LayoutInflater inflater;

	    public SquareImageAdapter(Context context) {
	        inflater = LayoutInflater.from(context);

	        items.add(new Item("Item 1",	R.drawable.blue));
	        items.add(new Item("Item 2",	R.drawable.cyan));
	        items.add(new Item("Item 3",	R.drawable.yellow));
	        items.add(new Item("Item 4",	R.drawable.orange));
	        items.add(new Item("Item 5",	R.drawable.magenta));
	        items.add(new Item("Item 6",	R.drawable.green));
	        items.add(new Item("Item 7",	R.drawable.violet));
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

	    private class Item {
	        final String name;
	        final int drawableId;

	        Item(String name, int drawableId) {
	            this.name = name;
	            this.drawableId = drawableId;
	        }
	    }
	}
	
	public void startMapActivity(View view){
		Intent intent = new Intent(getActivity(),AirMapFragment.class);
		getActivity().startActivity(intent);
	}
	
	public void startSettingsActivity(View view){
		Intent intent = new Intent(getActivity(),SettingsFragment.class);
		getActivity().startActivity(intent);
	}

}
