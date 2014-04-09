package com.smartcity.redux.fragments;

import java.util.ArrayList;

import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.smartcity.redux.R;
import com.smartcity.redux.gcm.DatabaseHelper;

public class InboxFragment extends ListFragment {

	private ArrayList<String> results = new ArrayList<String>();
	private SQLiteDatabase newDB;
	ListView messagesList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_inbox,null);

		
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		messagesList = (ListView)getListView();
		
		openAndQueryDatabase();
		displayResultList();
		
	}
	
	private void openAndQueryDatabase() {
		try {
			DatabaseHelper db = new DatabaseHelper(getActivity().getApplicationContext());
			newDB = db.getWritableDatabase();
			Cursor c = newDB.rawQuery("SELECT * FROM messagesTable",null);
			
			if(c != null) {
				if(c.moveToFirst()) {
					do {
						String message = c.getString(c.getColumnIndex("MESSAGE"));
						Log.d("INBOX","Adding message " + message);
						results.add("Message: " + message);
					} while(c.moveToNext());
				}
			}
		} catch(SQLiteException e) {
			Log.e("INBOX","Could not create or open the database! Error: " + e);
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.add(0,1,0,"Delete");
		super.onCreateContextMenu(menu,v,menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);
		
		if(item.getTitle().equals("Delete")) {
			CharSequence text = "Pressed delete";
			int duration = Toast.LENGTH_SHORT;
			
			Toast toast = Toast.makeText(getActivity().getApplicationContext(),text,duration);
			toast.show();
		}
		return true;
	}
	
	private void displayResultList() {
		TextView tv = new TextView(getActivity());
		tv.setText("Data shows up below");
		messagesList.addHeaderView(tv);
		registerForContextMenu(messagesList);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,results);
		setListAdapter(adapter);
		adapter.notifyDataSetChanged();
		messagesList.setTextFilterEnabled(true);
	}

}
