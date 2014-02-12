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
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Build;
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
import com.smartcity.redux.adapters.ProblemDataAdapter;
import com.smartcity.redux.jsonmodel.Hoboken311SearchResponse;
import com.smartcity.redux.jsonmodel.Problem;

public class Hoboken311MapFragment extends Fragment {
	
	private GoogleMap googleMap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_hoboken311_map);
		View root = inflater.inflate(R.layout.activity_hoboken311_map, null);
		
		setupActionBar();
		
		new JsonParser().execute();
		
		return root;
	}
	
	@Override
	public void onResume() {
			super.onResume();
			initilizeMap();
	}

	/**
	 * function to load map If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager()
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
	
	private class JsonParser extends AsyncTask<Void,Void,Hoboken311SearchResponse>{

		@Override
		protected Hoboken311SearchResponse doInBackground(Void... params) {
			//String url = "http://pastebin.com/raw.php?i=1VnxAK78";
			String url = "http://pastebin.com/raw.php?i=vTEbTCDT";  // TODO URL (and activity.java)
			// http://schoboken.cloudapp.net:82/api/GetProblems?
			//InputStream source = retrieveStream(url);
			InputStream stream = retrieveStream(url);
			Log.d("STREAM", (stream == null) + "");
			
			try{
				Gson gson = new Gson();
				Reader reader = new InputStreamReader(stream);
				Hoboken311SearchResponse response = gson.fromJson(reader,Hoboken311SearchResponse.class);
				return response;
			}
			catch(NullPointerException npe){
				Log.e("ERROR","Warning: Null pointer exception while parsing JSON!",npe);
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Hoboken311SearchResponse response){

			
			try {

				// Loading map
				initilizeMap();

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
				
				//Set up custom info window adapter
				
				ProblemDataAdapter adapter = new ProblemDataAdapter(getActivity().getLayoutInflater());
				// TODO this is where sub-classes are used (below, and in the adapter)
				double latitude = 40.745066;
				double longitude = -74.024294;
				
				String res = "markercolor";
				int resID;
				
				for(Problem problem : response.Problems){
				//	System.out.println(problem.ProblemDetails.Category.toString());
					if(problem.ProblemDetails.Category.toString().equals("Utilities & Flooding") || 
							problem.ProblemDetails.Category.toString().equals("Signs, Signals & Lights") ||
							problem.ProblemDetails.Category.toString().equals("Health & Social Services") ||
							problem.ProblemDetails.Category.toString().equals("Animals"))
					{
						resID = getResources().getIdentifier("alert","drawable",getActivity().getPackageName());
						// RED
					}
					else if(problem.ProblemDetails.Category.toString().equals("Parks & Trees"))
						// GREEN
					{
						resID = getResources().getIdentifier("alert_g","drawable",getActivity().getPackageName());
					}
					else if(problem.ProblemDetails.Category.toString().equals("Business & Construction") ||
							problem.ProblemDetails.Category.toString().equals("Garbage, Recycling & Graffiti") ||
							problem.ProblemDetails.Category.toString().equals("Parking") ||
							problem.ProblemDetails.Category.toString().equals("Transportation, Sidewalks & Streets"))
					{
						resID = getResources().getIdentifier("alert_o","drawable",getActivity().getPackageName());
						// ORANGE
					}
					else
					{
						resID = getResources().getIdentifier("alert_y","drawable",getActivity().getPackageName());
						// DEFAULT CASE, YELLOW
					}
					//Log.d("STREAM","We got back: " + response.Problems);
					Marker marker = googleMap.addMarker(new MarkerOptions()
			        .position(new LatLng(problem.Location.Latitude,problem.Location.Longitude))
			        .title(problem.ProblemDetails.Name)
			        .icon(BitmapDescriptorFactory.fromResource(resID)));
					adapter.hashMap.put(marker, problem);
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
	
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
	*/
	
}
