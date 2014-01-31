package com.smartcity.redux;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;

public class EnergyCalculatorActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_energy_calculator);
		
		 WebView webview = new WebView(this);
		 setContentView(webview);
		 
		 webview.loadUrl("http://c03.apogee.net/calcs/rescalc5x/Profile.aspx");
	}

	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.energy_calculator, menu);
		return true;
	}
	*/

}
