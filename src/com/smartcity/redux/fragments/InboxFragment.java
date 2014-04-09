package com.smartcity.redux.fragments;

import java.util.ArrayList;

import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
	
	private void displayResultList() {
		TextView tv = new TextView(getActivity());
		tv.setText("Data shows up below");
		messagesList.addHeaderView(tv);
		
		setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,results));
		messagesList.setTextFilterEnabled(true);
	}

}
