package com.smartcity.redux.fragments;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.smartcity.redux.R;

public class FormsFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_air_quality);
		View root = inflater.inflate(R.layout.activity_forms, null);
		setupActionBar();
		
		return root;
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(getActivity());
			return true;
		}
		return super.onOptionsItemSelected(item);
		
	}
/**
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forms, menu);
		return true;
	}
	**/
//	public void startParkingPermitActivity(View view) {
//		Intent intent = new Intent(FormsActivity.this,ParkingPermitActivity.class);
//		FormsActivity.this.startActivity(intent);
//	}
//	
//	public void startRecreationFormActivity(View view) {
//		Intent intent = new Intent(FormsActivity.this,RecreationFormActivity.class);
//		FormsActivity.this.startActivity(intent);
//	}
//	
//	public void startVoterRegActivity(View view) {
//		Intent intent = new Intent(FormsActivity.this,VoterRegActivity.class);
//		FormsActivity.this.startActivity(intent);
//	}

}
