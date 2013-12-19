package com.smartcity.redux;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartcity.redux.adapters.PlacesAutoCompleteAdapter;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DirectionsInputActivity extends Activity implements OnItemClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_directions_input);
		
		AutoCompleteTextView destinationInput = (AutoCompleteTextView) findViewById(R.id.destinationInput);
		AutoCompleteTextView startingPointInput = (AutoCompleteTextView) findViewById(R.id.startingPointInput);
		
		startingPointInput.setAdapter(new PlacesAutoCompleteAdapter(this, android.R.layout.test_list_item));
		startingPointInput.setOnItemClickListener(this);
		
		destinationInput.setAdapter(new PlacesAutoCompleteAdapter(this, android.R.layout.test_list_item));
		destinationInput.setOnItemClickListener(this);
		
		
		
		 API_KEY = getResources().getText(R.string.MapsAPIKey).toString();
		 
		 Log.d("APIKEY",API_KEY);
		
		setupActionBar();

	}
	
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String str = (String) adapterView.getItemAtPosition(position);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

	
	private static final String LOG_TAG = "SmartCity";
	private String API_KEY;
	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String OUT_JSON = "/json";

	public ArrayList<String> autocomplete(String input) {
		
	    ArrayList<String> resultList = null;

	    HttpURLConnection conn = null;
	    StringBuilder jsonResults = new StringBuilder();
	    try {
	        StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
	        sb.append("?sensor=false&key=" + API_KEY);
	        sb.append("&components=country:us");
	        sb.append("&input=" + URLEncoder.encode(input, "utf8"));

	        URL url = new URL(sb.toString());
	        conn = (HttpURLConnection) url.openConnection();
	        InputStreamReader in = new InputStreamReader(conn.getInputStream());

	        // Load the results into a StringBuilder
	        int read;
	        char[] buff = new char[1024];
	        while ((read = in.read(buff)) != -1) {
	            jsonResults.append(buff, 0, read);
	        }
	    } catch (MalformedURLException e) {
	        Log.e(LOG_TAG, "Error processing Places API URL", e);
	        return resultList;
	    } catch (IOException e) {
	        Log.e(LOG_TAG, "Error connecting to Places API", e);
	        return resultList;
	    } finally {
	        if (conn != null) {
	        	//Log.d("DISCONNECT","OK, disconnecting from Places API");
	            conn.disconnect();
	        }
	    }

	    try {
	        // Create a JSON object hierarchy from the results
	        JSONObject jsonObj = new JSONObject(jsonResults.toString());
	        //Log.d("JSON", jsonResults.toString());
	        JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

	        // Extract the Place descriptions from the results
	        resultList = new ArrayList<String>(predsJsonArray.length());
	        for (int i = 0; i < predsJsonArray.length(); i++) {
	            resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
	        }
	    } catch (JSONException e) {
	        Log.e(LOG_TAG, "Cannot process JSON results", e);
	    }
	    
	    //Log.d("RESULTS SIZE",Integer.toString(resultList.size()));
	    //Log.d("RESULT 1",resultList.get(0));

	    return resultList;
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.directions_input, menu);
		return true;
	}
	
	public void startDirectionsInfoActivity(View view) {
		Intent intent = new Intent(DirectionsInputActivity.this,DirectionsInfoActivity.class);
		
		EditText startingPoint = (EditText) findViewById(R.id.startingPointInput);
		EditText destination = (EditText) findViewById(R.id.destinationInput);
		Spinner transitSpinner = (Spinner) findViewById(R.id.spn_directions_type);
		
		String startingPointText = startingPoint.getText().toString();
		String destinationText = destination.getText().toString();
		String transitType = transitSpinner.getSelectedItem().toString();
		
		intent.putExtra("startingPoint",startingPointText);
		intent.putExtra("destination", destinationText);
		intent.putExtra("transitType",transitType);
		
		DirectionsInputActivity.this.startActivity(intent);
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

}
