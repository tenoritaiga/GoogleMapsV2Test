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
import android.webkit.WebView;

import com.smartcity.redux.R;

public class PollFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.activity_poll, null);
		setupActionBar();
		
		 WebView webview = new WebView(getActivity());
		 getActivity().setContentView(webview);
		 
		 webview.getSettings().setBuiltInZoomControls(true);
		 webview.getSettings().setJavaScriptEnabled(true);
		 
		 webview.loadUrl("https://docs.google.com/forms/d/1r3HZhsRRTTxGId6ajZt1h5fWh_2le1ezWNgdIThxoMQ/viewform");
		
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
}
