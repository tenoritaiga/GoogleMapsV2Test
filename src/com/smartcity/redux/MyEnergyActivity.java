package com.smartcity.redux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.smartcity.redux.adapters.EnergyPagerAdapter;
import com.smartcity.redux.fragments.DatePickerFragment;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MyEnergyActivity extends FragmentActivity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	EnergyPagerAdapter mEnergyPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_energy);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// Show the Up button in the action bar.
		actionBar.setDisplayHomeAsUpEnabled(true);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mEnergyPagerAdapter = new EnergyPagerAdapter(
				this, getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mEnergyPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mEnergyPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mEnergyPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		
		activity = this;
		
		new JsonEnergyTotalParser().execute();
		new JsonEnergyAvgParser().execute();
		new JsonEnergyRecordParser().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_energy, menu);
		return true;
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

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}
	
	/**
	 * Called when the Change Date button in the "Enter Tank 
	 * Refill" tab is clicked - makes the date selector visible.
	 * @param v
	 */
	public void showDatePickerDialog(View v) {
		DatePickerFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}
	
	/**
	 * Performs an HTTP GET request to retrieve an InputStream containing JSON 
	 * data from the provided URL. If the request is successful, an InputStream 
	 * is returned.
	 * @param url
	 * @return
	 */
	private static InputStream retrieveStream(String url) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(url);
		
		try{
			HttpResponse getResponse = client.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			
			if(statusCode != HttpStatus.SC_OK){
				//Log.w(getClass().getSimpleName(),
						//"Error " + statusCode + " for URL " + url);
				System.out.println(statusCode);
				return null;
			}
			
			HttpEntity getResponseEntity = getResponse.getEntity();
			return getResponseEntity.getContent();
		}
		
		catch(IOException e){
			getRequest.abort();
			//Log.w(getClass().getSimpleName(),"Error for URL " + url, e);
		}
		return null;
	}
	
	/**
	 * Nested class for retrieving average energy usage data through an API call, and populating 
	 * the appropriate fields of the "View Usage Data" tab.
	 * @author Class2013
	 *
	 */
	private class JsonEnergyAvgParser extends AsyncTask<Void,Void,JSONObject> {
		
		/**
		 * Makes the API call to retrieve an InputStream from the desired URL, then parses 
		 * that InputStream to retrieve a JSON object containing average energy usage data.
		 */
		@Override
		protected JSONObject doInBackground(Void... params) {
			//String url = "http://pastebin.com/raw.php?i=hizxN4XD";
			String url = "http://smartcity1.cloudapp.net/api/UserEnergyConsumptionAverages?user_id=1";
			InputStream stream = retrieveStream(url);
			Log.d("STREAM", (stream == null) + "");
			
			try{
				Reader reader = new InputStreamReader(stream);
				BufferedReader bufRead = new BufferedReader(reader);
				StringBuilder builder = new StringBuilder();
				String line;
				while ((line = bufRead.readLine()) != null) {
					builder.append(line);
					System.out.println(line);
				}
				String jsonString = builder.toString();
				JSONObject jsonObject = new JSONObject(jsonString);
				//System.out.println(new JSONObject(jsonObject.getString("HomeAddress")).getString("NumberAndStreet"));
				return jsonObject;
			}
			catch(NullPointerException npe){
				Log.e("ERROR","Warning: Null pointer exception while parsing JSON!",npe);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		
		/**
		 * Parses the retrieved JSON object and populates the fields of the 
		 * "View Usage Data" tab with the contained data on average energy usage.
		 */
		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			
			try {
				TextView textView = (TextView) findViewById(R.id.gas_cons_avg);
				textView.setText(jsonObject.getString("UserAverageGas30Day"));
				textView = (TextView) findViewById(R.id.gas_cons_avg_all);
				textView.setText(jsonObject.getString("CityAverageGas30Day"));
				textView = (TextView) findViewById(R.id.elect_cons_avg);
				textView.setText(jsonObject.getString("UserAverageElectricity30Day"));
				textView = (TextView) findViewById(R.id.elect_cons_avg_all);
				textView.setText(jsonObject.getString("CityAverageElectricity30Day"));
				
				// init example series data
				GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
						new GraphViewData(1, Double.parseDouble(jsonObject.getString("UserAverageGas30Day")))
						, new GraphViewData(2, Double.parseDouble(jsonObject.getString("CityAverageGas30Day")))
						, new GraphViewData(3, Double.parseDouble(jsonObject.getString("UserAverageElectricity30Day")))
						, new GraphViewData(4, Double.parseDouble(jsonObject.getString("CityAverageElectricity30Day")))
				}); 
				BarGraphView graphView = new BarGraphView(
						activity // context
						, "GraphViewDemo" // heading
				);
				graphView.setDrawValuesOnTop(true);
				
				graphView.addSeries(exampleSeries); // data
				
				LinearLayout layout = (LinearLayout) findViewById(R.id.graph_energy);
				layout.addView(graphView);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private class JsonEnergyRecordParser extends AsyncTask<Void,Void,JSONArray> {
		
		@Override
		protected JSONArray doInBackground(Void... params) {
			//String url = "http://pastebin.com/raw.php?i=iarB92kH";
			String url = "http://smartcity1.cloudapp.net/api/UserEnergyConsumptionData?user_id=1";
			InputStream stream = retrieveStream(url);
			Log.d("STREAM", (stream == null) + "");
			
			try{
				Reader reader = new InputStreamReader(stream);
				BufferedReader bufRead = new BufferedReader(reader);
				StringBuilder builder = new StringBuilder();
				String line;
				while ((line = bufRead.readLine()) != null) {
					builder.append(line);
					System.out.println(line);
				}
				String jsonString = builder.toString();
				JSONArray jsonArray = new JSONArray(jsonString);
				//System.out.println(new JSONObject(jsonObject.getString("HomeAddress")).getString("NumberAndStreet"));
				return jsonArray;
			}
			catch(NullPointerException npe){
				Log.e("ERROR","Warning: Null pointer exception while parsing JSON!",npe);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(JSONArray jsonArray) {
			
			try {
				System.out.println(jsonArray.length());
				System.out.println(jsonArray.getJSONObject(0).getString("EnergyConsumptionID"));
				
				TableLayout tableLayout = (TableLayout) findViewById(R.id.energyResultsTable);
				TableRow tableRow;
				TextView text1;
				TextView text2;
				TextView text3;
				//Double total = 0.0;
				//Double total2 = 0.0;
				
				tableLayout.removeViews(1, tableLayout.getChildCount()-1);
				
				for (int i=0; i<jsonArray.length(); i++) {
					tableRow = new TableRow(activity);
					tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
					
					text1 = new TextView(activity);
					text1.setText(jsonArray.getJSONObject(i).getString("Date"));
					text1.setPadding(15, 0, 15, 0);
					text1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
					
					text2 = new TextView(activity);
					text2.setText(jsonArray.getJSONObject(i).getString("Gas"));
					text2.setPadding(15, 0, 15, 0);
					text2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
					
					text3 = new TextView(activity);
					text3.setText(jsonArray.getJSONObject(i).getString("Electricity"));
					text3.setPadding(15, 0, 15, 0);
					text3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
					
					tableRow.addView(text1);
					tableRow.addView(text2);
					tableRow.addView(text3);
					
					tableLayout.addView(tableRow);
					
					//total += (Double.parseDouble(jsonArray.getJSONObject(i).getString("Gas")));
					//total2 += (Double.parseDouble(jsonArray.getJSONObject(i).getString("Electricity")));
				}
				
				//EditText gasTotal = (EditText) findViewById(R.id.gas_cons_total);
				//gasTotal.setText(total.toString());
				//EditText electTotal = (EditText) findViewById(R.id.elect_cons_total);
				//electTotal.setText(total2.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Nested class for retrieving total energy usage data through an API call, and populating 
	 * the appropriate fields of the "View Usage Data" tab.
	 * @author Class2013
	 *
	 */
	private class JsonEnergyTotalParser extends AsyncTask<Void,Void,JSONObject> {
		
		/**
		 * Makes the API call to retrieve an InputStream from the desired URL, then parses 
		 * that InputStream to retrieve a JSON object containing average energy usage data.
		 */
		@Override
		protected JSONObject doInBackground(Void... params) {
			//String url = "http://pastebin.com/raw.php?i=hizxN4XD";
			String url = "http://smartcity1.cloudapp.net/api/UserEnergyConsumptionTotals?user_id=1";
			InputStream stream = retrieveStream(url);
			Log.d("STREAM", (stream == null) + "");
			
			try{
				Reader reader = new InputStreamReader(stream);
				BufferedReader bufRead = new BufferedReader(reader);
				StringBuilder builder = new StringBuilder();
				String line;
				while ((line = bufRead.readLine()) != null) {
					builder.append(line);
					System.out.println(line);
				}
				String jsonString = builder.toString();
				JSONObject jsonObject = new JSONObject(jsonString);
				//System.out.println(new JSONObject(jsonObject.getString("HomeAddress")).getString("NumberAndStreet"));
				return jsonObject;
			}
			catch(NullPointerException npe){
				Log.e("ERROR","Warning: Null pointer exception while parsing JSON!",npe);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		
		/**
		 * Parses the retrieved JSON object and populates the fields of the 
		 * "View Usage Data" tab with the contained data on total energy usage.
		 */
		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			
			try {
				TextView textView = (TextView) findViewById(R.id.gas_cons_total);
				textView.setText(jsonObject.getString("TotalGas"));
				textView = (TextView) findViewById(R.id.elect_cons_total);
				textView.setText(jsonObject.getString("TotalElectricity"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Nested class for sending a energy usage record through an API call - constructs a JSON 
	 * object with the required data and makes an HTTP POST request to send the data to the server.
	 * @author Class2013
	 *
	 */
	private class JsonEnergySender extends AsyncTask<Void,Void,Void> {
		
		@Override
		protected Void doInBackground(Void... params) {
			JSONObject json = new JSONObject();
			
			try {
				json.put("UserID", 1);
				TextView textView = (TextView) findViewById(R.id.view_date);
				json.put("Date", textView.getText().toString());
				EditText editText = (EditText) findViewById(R.id.edit_gas_therms);
				json.put("Gas", Double.parseDouble(editText.getText().toString()));
				editText = (EditText) findViewById(R.id.edit_electricity_kwh);
				json.put("Electricity", Double.parseDouble(editText.getText().toString()));
				
				DefaultHttpClient client = new DefaultHttpClient();
				//HttpPost postRequest = new HttpPost("http://pastebin.com/raw.php?i=QiXs9eZU");
				HttpPost postRequest  = new HttpPost("http://smartcity1.cloudapp.net/api/EnergyConsumption");
				System.out.println(json.toString());
				StringEntity se = new StringEntity(json.toString());
				se.setContentType("application/json");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
				postRequest.setEntity(se);
				HttpResponse response = client.execute(postRequest);
				System.out.println(response.getStatusLine().getStatusCode());
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	public void saveEnergyUsage(View view) {
		new JsonEnergySender().execute();
		new JsonEnergyAvgParser().execute();
		new JsonEnergyTotalParser().execute();
		new JsonEnergyRecordParser().execute();
	}

}
