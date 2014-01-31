package com.smartcity.redux;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;

public class CityEventsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city_events);
		
		 WebView webview = new WebView(this);
		 setContentView(webview);
		 
		 webview.loadUrl("http://www.hobokennj.org/calendar/");
	}
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.city_events, menu);
		return true;
	}
	*/

}
