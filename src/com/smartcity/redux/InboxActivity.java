package com.smartcity.redux;

import java.util.ArrayList;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.smartcity.redux.gcm.DatabaseHelper;

public class InboxActivity extends ListActivity {
	
	private ArrayList<String> results = new ArrayList<String>();
	private String tableName = "messagesTable";
	private SQLiteDatabase newDB;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("INBOX","In Inbox OnCreate");
		openAndQueryDatabase();
		displayResultList();
	}
	
	private void openAndQueryDatabase() {
		try {
			DatabaseHelper db = new DatabaseHelper(this.getApplicationContext());
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
		TextView tv = new TextView(this);
		tv.setText("Data shows up below");
		getListView().addHeaderView(tv);
		
		setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,results));
		getListView().setTextFilterEnabled(true);
	}

}