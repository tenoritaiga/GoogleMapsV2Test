package com.smartcity.redux;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsletterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.activity_newsletter);
		
		setupActionBar();
		
		 WebView webview = new WebView(this);
		 webview.setWebChromeClient(new WebChromeClient() {
			 public void onProgressChanged(WebView view, int progress) {
				 NewsletterActivity.this.setProgress(progress * 100);
			 }
		 });
		 
		 webview.setWebViewClient(new WebViewClient() {
			 @Override
			 public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				 Log.e("WEBVIEW", "WebView failed with error code " + errorCode + ": " + description);
			 }
			 
			 @Override
			 public boolean shouldOverrideUrlLoading(WebView view, String url) {
				 view.loadUrl(url);
				 return true;
			 } 
		 });
		 
		 setContentView(webview);
		 
		 webview.getSettings().setBuiltInZoomControls(true);
		 webview.getSettings().setJavaScriptEnabled(true);
		 
		 String pdf = "http://www.hobokennj.org/docs/newsletter/Newsletter-Fall-2013.pdf";
		 
		 webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdf);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
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
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
		
	}
/**
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.newsletter, menu);
		return true;
	}
	**/

}
