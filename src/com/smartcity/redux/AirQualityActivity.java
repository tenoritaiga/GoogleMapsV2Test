package com.smartcity.redux;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.gson.Gson;
import com.smartcity.redux.adapters.AirQualityAdapter;
import com.smartcity.redux.jsonmodel.AirQualitySearchResponse;
import com.smartcity.redux.jsonmodel.Sensor;
import com.smartcity.redux.jsonmodel.AirSensor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.smartcity.redux.adapters.SensorDataAdapter;
import com.smartcity.redux.jsonmodel.ParkingSearchResponse;
import com.smartcity.redux.jsonmodel.ParkingSensor;
import com.smartcity.redux.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

public class AirQualityActivity extends Activity {
	
	private GoogleMap googleMap;
	
    private CheckBox mPollutionCheckbox;

    private int flag;
        
    
    private List<Marker> air_markers = new ArrayList<Marker>();
    private List<Marker> noise_markers = new ArrayList<Marker>();
    private List<Marker> greenhouse_markers = new ArrayList<Marker>();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_air_quality);
		
		setupActionBar();
		
		new JsonParser().execute();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}
	
	public void onNoisePollutionToggled(View view) {
	        // Is the view now checked?
	        boolean checked = ((CheckBox) view).isChecked();
	        int i;
	        
	        i = 0;
	        
	        // Check which checkbox was clicked
	        switch(view.getId()) {
	            case R.id.noisePollution:
	                if (checked)
	                {
	                	flag = 1;
	                	while(i < noise_markers.size())
	                	{
	                		noise_markers.get(i).setVisible(true);
	                		i++;
	                	}
	   
	                }
	                else
	                {
	                	flag = 0;
	                	while(i < noise_markers.size())
	                	{
	                		noise_markers.get(i).setVisible(false);
	                		i++;
	                	}
	 
	                }
	                break;
	            case R.id.greenhouseGas:
	                if (checked)
	                {
	                	//markers for greenhouse gas
	                	flag = 1;
	                	while(i < greenhouse_markers.size())
	                	{
	                		greenhouse_markers.get(i).setVisible(true);
	                		i++;
	                	}
	                }
	                else
	                {
	                	flag = 0;
	                	while(i < greenhouse_markers.size())
	                	{
	                		greenhouse_markers.get(i).setVisible(false);
	                		i++;
	                	}
	                }
	                break;
	            case R.id.airQuality:
	                if (checked)
	                {
	                	//markers for air quality
	                	flag = 1;
	                	while(i < air_markers.size())
	                	{
	                		air_markers.get(i).setVisible(true);
	                		i++;
	                	}
	                }
	                else
	                {
	                	flag = 0;
	                	while(i < air_markers.size())
	                	{
	                		air_markers.get(i).setVisible(false);
	                		i++;
	                	}
	                }
	                break;
	        }
	    }
	
	//public void showMarkers(Marker marker)
	//{
	//}
	
	/**
	 * function to load map If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager()
					.findFragmentById(R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
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
	
	private class JsonParser extends AsyncTask<Void,Void,AirQualitySearchResponse>{

		@Override
		protected AirQualitySearchResponse doInBackground(Void... params) {
			//String url = "http://pastebin.com/raw.php?i=1VnxAK78";
			//String url = "http://pastebin.com/raw.php?i=ikLGcHbY";
			//String url = "http://pastebin.com/raw.php?i=R18q6rPy";
			//String url = "http://pastebin.com/raw.php?i=tY3u23TF";
			String url = "http://pastebin.com/raw.php?i=Hej6LrLA";
			//InputStream source = retrieveStream(url);
			InputStream stream = retrieveStream(url);
			Log.d("STREAM", (stream == null) + "");
			
			try{
				Gson gson = new Gson();
				Reader reader = new InputStreamReader(stream);
				AirQualitySearchResponse response = gson.fromJson(reader,AirQualitySearchResponse.class);
				return response;
			}
			catch(NullPointerException npe){
				Log.e("ERROR","Warning: Null pointer exception while parsing JSON!",npe);
			}
			
			return null;
		}
		@Override
		protected void onPostExecute(AirQualitySearchResponse response){

			
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
				
				//sets the padding over to allow for a left side menu
				googleMap.setPadding(0, 0, 220, 0);
				
				//Set up custom  info window adapter
				AirQualityAdapter adapter = new AirQualityAdapter(getLayoutInflater());
				
				double latitude = 40.745066;
				double longitude = -74.024294;
				
				String res = "markercolor";
				int resID;
				
				for(AirSensor sensor : response.AirSensors)
				{
					/**
					if(Float.parseFloat(sensor.Readings.CO2) <= 10)
						resID = getResources().getIdentifier("airsensor_g","drawable",getPackageName());
					else if(Float.parseFloat(sensor.Readings.CO2) > 10 && Float.parseFloat(sensor.Readings.CO2) < 20)
						resID = getResources().getIdentifier("airsensor_y","drawable",getPackageName());
					else
						resID = getResources().getIdentifier("airsensor_r","drawable",getPackageName());
						**/
					//resID = getResources().getIdentifier("airsensor_r","drawable",getPackageName());
					
					if (sensor.SensorType.equals("Air"))
					{
						resID = getResources().getIdentifier("airsensor_r","drawable",getPackageName());
					}
					else if (sensor.SensorType.equals("Noise"))
					{
						resID = getResources().getIdentifier("noisesensor","drawable",getPackageName());
					}
					else if (sensor.SensorType.equals("Greenhouse"))
					{
						resID = getResources().getIdentifier("greenhousesensor","drawable",getPackageName());
					}
					else
					{
						resID = getResources().getIdentifier("airsensor_y","drawable",getPackageName());
					}
					
					
					
					//Log.d("STREAM","We got back: " + response.AirSensors);
					
					Marker marker = googleMap.addMarker(new MarkerOptions()
			        .position(new LatLng(sensor.Location.Latitude,sensor.Location.Longitude))
			        .title(sensor.SensorName)
			        .snippet(sensor.SensorType)
			        .icon(BitmapDescriptorFactory.fromResource(resID)));
					
					
					
					if (sensor.SensorType.equals("Air"))
					{
						air_markers.add(marker);
					}
					else if (sensor.SensorType.equals("Noise"))
					{
						noise_markers.add(marker);
					}
					else if (sensor.SensorType.equals("Greenhouse"))
					{
						greenhouse_markers.add(marker);
					}
					
					
					adapter.hashMap.put(marker, sensor);
					
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
