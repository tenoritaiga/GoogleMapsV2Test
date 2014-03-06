package com.smartcity.redux.fragments;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.smartcity.redux.R;
//import com.smartcity.redux.EmergencyReportFragment.JsonEmerReportSender;

public class EmergencyReportFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.activity_emergency_report, null);

		setHasOptionsMenu(true);
		setupActionBar();
		
		return root;
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getActivity().getMenuInflater().inflate(R.menu.emergency_report, menu);
//		return true;
//	}

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
	
	public boolean boolCheck(String radValue){
		if(radValue == "Yes"){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Nested class for sending an emergency report through an API call - constructs a JSON 
	 * object with the required data and makes an HTTP POST request to send the data to the server.
	 * @author Class2013
	 *
	 */
	private class JsonEmerReportSender extends AsyncTask<Void,Void,Void> {
		
		@Override
		protected Void doInBackground(Void... params) {
			JSONObject json = new JSONObject();
			JSONObject jsonLocation = new JSONObject();
			
			try {
				json.put("UserID", 1);
				
				double latitude =  40.737165;
			    double longitude = -74.030969;
			    LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
			    Criteria crit = new Criteria();
		        String towers = lm.getBestProvider(crit,false);
		        Location location = lm.getLastKnownLocation(towers);
		        latitude = location.getLatitude();
			    longitude = location.getLongitude();
			    jsonLocation.put("Latitude", latitude);
			    jsonLocation.put("Longitude", longitude);
			    json.put("Location", jsonLocation);
			    
			    json.put("NumberAndStreet", "10 Test Drive");
				json.put("PhoneNumber", "123-867-5309");
				
				final RadioGroup rad_medEme = (RadioGroup)getActivity().findViewById(R.id.rad_report_group);
				final RadioGroup rad_food = (RadioGroup)getActivity().findViewById(R.id.rad_report_group1);
				final RadioGroup rad_water = (RadioGroup)getActivity().findViewById(R.id.rad_report_group2);
				final RadioGroup rad_power = (RadioGroup)getActivity().findViewById(R.id.rad_report_group3);
				final RadioGroup rad_med = (RadioGroup)getActivity().findViewById(R.id.rad_report_group4);
				final RadioGroup rad_heat = (RadioGroup)getActivity().findViewById(R.id.rad_report_group5);
				final RadioGroup rad_charge = (RadioGroup)getActivity().findViewById(R.id.rad_report_group6);
				final RadioGroup rad_lodge = (RadioGroup)getActivity().findViewById(R.id.rad_report_group7);
				final RadioGroup rad_internet = (RadioGroup)getActivity().findViewById(R.id.rad_report_group8);
				final RadioGroup rad_gas = (RadioGroup)getActivity().findViewById(R.id.rad_report_group9);
				
				String medEmeValue = ((RadioButton)getActivity().findViewById(rad_medEme.getCheckedRadioButtonId())).getText().toString();
				String foodValue = ((RadioButton)getActivity().findViewById(rad_food.getCheckedRadioButtonId())).getText().toString();
				String waterValue = ((RadioButton)getActivity().findViewById(rad_water.getCheckedRadioButtonId())).getText().toString();
				String powerValue = ((RadioButton)getActivity().findViewById(rad_power.getCheckedRadioButtonId())).getText().toString();
				String medValue = ((RadioButton)getActivity().findViewById(rad_med.getCheckedRadioButtonId())).getText().toString();
				String heatValue = ((RadioButton)getActivity().findViewById(rad_heat.getCheckedRadioButtonId())).getText().toString();
				String chargeValue = ((RadioButton)getActivity().findViewById(rad_charge.getCheckedRadioButtonId())).getText().toString();
				String lodgeValue = ((RadioButton)getActivity().findViewById(rad_lodge.getCheckedRadioButtonId())).getText().toString();
				String internetValue = ((RadioButton)getActivity().findViewById(rad_internet.getCheckedRadioButtonId())).getText().toString();
				String gasValue = ((RadioButton)getActivity().findViewById(rad_gas.getCheckedRadioButtonId())).getText().toString();
				
				boolean boolPower = boolCheck(powerValue);     // Bool Values that are going be sent to DB
				boolean boolFood = boolCheck(foodValue);
				boolean boolWater = boolCheck(waterValue);
				boolean boolmedEme = boolCheck(medEmeValue);
				boolean boolMed = boolCheck(medValue);
				boolean boolHeat = boolCheck(heatValue);
				boolean boolCharge = boolCheck(chargeValue);
				boolean boolLodge = boolCheck(lodgeValue);
				boolean boolInternet = boolCheck(internetValue);
				boolean boolGas = boolCheck(gasValue);
				
				json.put("MedEmergency", boolmedEme);
				json.put("Food", boolFood);
				json.put("Water", boolWater);
				json.put("Power", boolPower);
				json.put("Medication", boolMed);
				json.put("Heating", boolHeat);
				json.put("Charging", boolCharge);
				json.put("Lodging", boolLodge);
				json.put("Internet", boolInternet);
				json.put("NaturalGas", boolGas);
				
				DefaultHttpClient client = new DefaultHttpClient();
				//HttpPost postRequest = new HttpPost("http://pastebin.com/raw.php?i=QiXs9eZU");
				HttpPost postRequest  = new HttpPost("http://schoboken.cloudapp.net:82/api/GasConsumption");
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
	
	public void sendEmerReport(View view) {
		new JsonEmerReportSender().execute();
	}

}
