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

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.smartcity.redux.R;
import com.smartcity.redux.adapters.SensorDataAdapter;
import com.smartcity.redux.jsonmodel.SearchResponse;
import com.smartcity.redux.jsonmodel.Sensor;

public class AirMapFragment extends Fragment {
	
	// Google Map
		private GoogleMap googleMap;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			//super.onCreateView(savedInstanceState);
			//setContentView(R.layout.activity_airmap);
			View root = inflater.inflate(R.layout.activity_airmap, null);
			
			//setupActionBar();
			
			new JsonParser().execute();

			return root;
		}

		@Override
		public void onResume() {
			super.onResume();
			initializeMap();
		}

		/**
		 * function to load map If map is not created it will create it for you
		 * */
		private void initializeMap() {
			if (googleMap == null) {
				googleMap = ((MapFragment) getFragmentManager()
						.findFragmentById(R.id.map)).getMap();

				// check if map is created successfully or not
				if (googleMap == null) {
					Toast.makeText(getActivity(),
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
		
		private class JsonParser extends AsyncTask<Void,Void,SearchResponse>{

			@Override
			protected SearchResponse doInBackground(Void... params) {
				//String url = "http://pastebin.com/raw.php?i=1VnxAK78";
				String url = "http://pastebin.com/raw.php?i=ikLGcHbY";
				//InputStream source = retrieveStream(url);
				InputStream stream = retrieveStream(url);
				Log.d("STREAM", (stream == null) + "");
				
				try{
					Gson gson = new Gson();
					Reader reader = new InputStreamReader(stream);
					SearchResponse response = gson.fromJson(reader,SearchResponse.class);
					return response;
				}
				catch(NullPointerException npe){
					Log.e("ERROR","Warning: Null pointer exception while parsing JSON!",npe);
				}
				
				return null;
			}
			@Override
			protected void onPostExecute(SearchResponse response){

				
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
					
					//Set up custom  info window adapter
					SensorDataAdapter adapter = new SensorDataAdapter(getActivity().getLayoutInflater());
					
					double latitude = 40.745066;
					double longitude = -74.024294;
					
					String res = "markercolor";
					int resID;
					
					for(Sensor sensor : response.Sensors){
						
						if(Float.parseFloat(sensor.Readings.CO2) <= 10)
							resID = getResources().getIdentifier("airsensor_g","drawable",getActivity().getPackageName());
						else if(Float.parseFloat(sensor.Readings.CO2) > 10 && Float.parseFloat(sensor.Readings.CO2) < 20)
							resID = getResources().getIdentifier("airsensor_y","drawable",getActivity().getPackageName());
						else
							resID = getResources().getIdentifier("airsensor_r","drawable",getActivity().getPackageName());
						
						//Log.d("STREAM","We got back: " + response.Sensors);
						Marker marker = googleMap.addMarker(new MarkerOptions()
				        .position(new LatLng(sensor.Location.Latitude,sensor.Location.Longitude))
				        .title(sensor.SensorName)
				        .icon(BitmapDescriptorFactory.fromResource(resID)));
						
						adapter.hashMap.put(marker, sensor);
						
						//marker.setVisible(false);
						
					}
					
					
					
					googleMap.setInfoWindowAdapter(adapter);
					
							
					//pass into the users current location
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
		
		/**
		 * Set up the {@link android.app.ActionBar}.
		 */
	/**
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
		**/

}
