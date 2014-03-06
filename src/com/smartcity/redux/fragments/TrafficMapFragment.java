package com.smartcity.redux.fragments;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.smartcity.redux.R;
import com.smartcity.redux.adapters.ParkingData;
import com.smartcity.redux.jsonmodel.ParkingSearchResponse;
import com.smartcity.redux.jsonmodel.ParkingSensor;

public class TrafficMapFragment extends SupportMapFragment {
	
private GoogleMap googleMap;

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	new JsonParser().execute();
	
	return super.onCreateView(inflater, container, savedInstanceState);
}

@Override
public void onResume() {
	super.onResume();
	if(googleMap == null) {
		googleMap = getMap();
		//googleMap.addMarker(new MarkerOptions().position(new LatLng(0,0)));
	}
}
	
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		//super.onCreate(savedInstanceState);
//		//setContentView(R.layout.activity_traffic_map);
//		View root = inflater.inflate(R.layout.activity_traffic_map, null);
//		
//		setupActionBar();
//		
//		new JsonParser().execute();
//		
//		return root;
//	}
//	
//	@Override
//	public void onResume() {
//		super.onResume();
//		initializeMap();
//	}

	/**
	 * function to load map If map is not created it will create it for you
	 * */
	private void initializeMap() {
		if (googleMap == null) {
			googleMap = ((SupportMapFragment) getFragmentManager()
					.findFragmentById(R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getActivity().getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	private static InputStream retrieveStream(String url) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(url);
		
		try{
			HttpResponse getResponse = client.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();
			
			if(statusCode != HttpStatus.SC_OK){
				//Log.w(getClass().getSimpleName(),
						//"Error " + statusCode + " for URL " + url);
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
	
	private class JsonParser extends AsyncTask<Void,Void,ParkingSearchResponse>{

		@Override
		protected ParkingSearchResponse doInBackground(Void... params) {
			//String url = "http://pastebin.com/raw.php?i=1VnxAK78";
			String url = "http://pastebin.com/raw.php?i=cS9qdnXd";
			//InputStream source = retrieveStream(url);
			InputStream stream = retrieveStream(url);
			Log.d("STREAM", (stream == null) + "");
			
			try{
				Gson gson = new Gson();
				Reader reader = new InputStreamReader(stream);
				ParkingSearchResponse response = gson.fromJson(reader,ParkingSearchResponse.class);
				return response;
			}
			catch(NullPointerException npe){
				Log.e("ERROR","Warning: Null pointer exception while parsing JSON!",npe);
			}
			
			return null;
		}
		@Override
		protected void onPostExecute(ParkingSearchResponse response){

			
			try {

				// Loading map
				initializeMap();

				// Changing map type
				googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				// googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
				// googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
				// googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
				// googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

				// Showing / hiding your current location
				googleMap.setMyLocationEnabled(true);

				// Enable / Disable zooming controls
				googleMap.getUiSettings().setZoomControlsEnabled(false);

				// Enable / Disable my location button
				googleMap.getUiSettings().setMyLocationButtonEnabled(true);

				// Enable / Disable Compass icon
				googleMap.getUiSettings().setCompassEnabled(true);

				// Enable / Disable Rotate gesture
				googleMap.getUiSettings().setRotateGesturesEnabled(true);

				// Enable / Disable zooming functionality
				googleMap.getUiSettings().setZoomGesturesEnabled(true);
				
				googleMap.setTrafficEnabled(true);
				
				//sets the padding over to allow for a left side menu
				//googleMap.setPadding(0, 0, 100, 0);
				
				//Set up custom  info window adapter
				
				ParkingData adapter = new ParkingData(getActivity().getLayoutInflater());
				
				double latitude = 40.745066;
				double longitude = -74.024294;
				
				String res = "markercolor";
				int resID;
				
				for(ParkingSensor sensor : response.ParkingSensors){
					resID = getResources().getIdentifier("parking_r","drawable",getActivity().getPackageName());
					//Log.d("STREAM","We got back: " + response.Sensors);
					Marker marker = googleMap.addMarker(new MarkerOptions()
			        .position(new LatLng(sensor.Location.Latitude,sensor.Location.Longitude))
			        .title(sensor.SensorName)
			        .icon(BitmapDescriptorFactory.fromResource(resID)));
					
					adapter.hashMap.put(marker, sensor);
					
					//marker.setVisible(false);
				}
				
				googleMap.setInfoWindowAdapter(adapter);
				
						// Move the camera to last position with a zoom level
						CameraPosition cameraPosition = new CameraPosition.Builder()
								.target(new LatLng(latitude,
										longitude)).zoom(15).build();

						googleMap.animateCamera(CameraUpdateFactory
								.newCameraPosition(cameraPosition));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
