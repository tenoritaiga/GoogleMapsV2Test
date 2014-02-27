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
import com.smartcity.redux.adapters.OverrideWebviewClient;

public class CityEventsFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.activity_city_events, null);
		
		setupActionBar();
		
		 WebView webview = new WebView(getActivity());
		 getActivity().setContentView(webview);
		 webview.setWebViewClient(new OverrideWebviewClient());	//Set our custom client so we load URLs in the WebView
		 webview.getSettings().setBuiltInZoomControls(true);
		 webview.getSettings().setUserAgentString("Mozilla/5.0 (Android; Mobile; rv:26.0) Gecko/26.0 Firefox/26.0");
		 webview.getSettings().setJavaScriptEnabled(true);
		 webview.loadUrl("http://www.hobokennj.org/calendar/");
		 
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
			NavUtils.navigateUpFromSameTask(getActivity());
			return true;
		}
		return super.onOptionsItemSelected(item);
		
	}

}
