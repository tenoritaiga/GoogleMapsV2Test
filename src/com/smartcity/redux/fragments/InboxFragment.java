package com.smartcity.redux.fragments;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.smartcity.redux.R;
import com.smartcity.redux.gcm.DatabaseHelper;

public class InboxFragment extends ListFragment implements OnItemLongClickListener {

	private ArrayList<String> results = new ArrayList<String>();
	private ArrayList<Integer> resultIDs = new ArrayList<Integer>();
	private SQLiteDatabase newDB;
	ListView messagesList;
	
	
	ArrayAdapter<String> adapter;
	
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
		//Log.d("TEST","# of items: " + adapter.getCount());
		getListView().setOnItemLongClickListener(this);
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	}
	
	private void openAndQueryDatabase() {
		try {
			DatabaseHelper db = new DatabaseHelper(getActivity().getApplicationContext());
			newDB = db.getWritableDatabase();
			Cursor c = newDB.rawQuery("SELECT * FROM messagesTable",null);
			adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,results);
			
			if(c != null) {
				if(c.moveToFirst()) {
					do {
						int id = c.getInt(c.getColumnIndex("ID"));
						//String priority = c.getString(c.getColumnIndex("PRIORITY"));
						String message = c.getString(c.getColumnIndex("MESSAGE"));
						Log.d("INBOX","Adding message " + message);
						results.add(message);
						resultIDs.add(id);
					} while(c.moveToNext());
				}
			}
		} catch(SQLiteException e) {
			Log.e("INBOX","Could not create or open the database! Error: " + e);
		}
	}
	
//	private void deleteFromDatabase(String id) {
//		try {
//			DatabaseHelper db = new DatabaseHelper(getActivity().getApplicationContext());
//			newDB = db.getWritableDatabase();
//			Cursor c = newDB.rawQuery("DELETE FROM messagesTable WHERE ID = " + id,null);
//		} catch(SQLiteException e) {
//			Log.e("INBOX","Could not create or open the database! Error: " + e);
//		}
//	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.add(0,1,0,"Delete");
		super.onCreateContextMenu(menu,v,menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		if(item.getTitle().equals("Delete")) {
			int id = resultIDs.get(info.position);
			try {
				Log.d("INBOX","Attempting to remove item with id " + id);
				adapter.remove(adapter.getItem(id));
				newDB.execSQL("DELETE FROM messagesTable WHERE ID='"+id+"'");
				resultIDs.remove(id);
				adapter.notifyDataSetChanged();
			} catch(SQLiteException e) {
				Log.e("INBOX","Failed to delete from the database!");
			}
		}
		return true;
	}
	
	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int position,
			long id) {
//		
//		DialogFragment dialog = DP.newInstance();
		new AlertDialog.Builder(getActivity())
			.setItems(new CharSequence[] { "Delete" }, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if(which == 0) {
						try {
							long id = resultIDs.get(position);
							Log.d("INBOX","Attempting to remove item with id " + id);
							adapter.remove(adapter.getItem(position));
							
							int ret = newDB.delete("messagesTable", "ID="+id, null);
							Log.d("INBOX","Returned " + ret);
							//newDB.execSQL("DELETE FROM messagesTable WHERE ID='"+id+"'");
							resultIDs.remove(id);
							adapter.notifyDataSetChanged();
						} catch(SQLiteException e) {
							Log.e("INBOX","Failed to delete from the database!");
						}
					}
				}
			})
			.show();
		
		
		// TODO Auto-generated method stub
		return false;
	}
	private void displayResultList() {
//		registerForContextMenu(messagesList);
		setListAdapter(adapter);
		adapter.notifyDataSetChanged();
		messagesList.setTextFilterEnabled(true);
	}

}
