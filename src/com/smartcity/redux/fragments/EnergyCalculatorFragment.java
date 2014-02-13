package com.smartcity.redux.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.smartcity.redux.R;

public class EnergyCalculatorFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 View root = inflater.inflate(R.layout.activity_energy_calculator, null);
		
		 setupActionBar();
		
		 WebView webview = new WebView(getActivity());
		 getActivity().setContentView(webview);
		 
		 webview.getSettings().setBuiltInZoomControls(true);
		 webview.getSettings().setJavaScriptEnabled(true);
		 
		 webview.loadUrl("http://c03.apogee.net/calcs/rescalc5x/Profile.aspx");
		 
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
