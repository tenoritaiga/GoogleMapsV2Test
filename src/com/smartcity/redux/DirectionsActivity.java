package com.smartcity.redux;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.smartcity.redux.adapters.SensorDataAdapter;
import com.smartcity.redux.route.Routing;
import com.smartcity.redux.route.Routing.TravelMode;
import com.smartcity.redux.route.RoutingListener;

public class DirectionsActivity extends FragmentActivity implements RoutingListener
{
    private static final String TAG = "DirectionsActivity";
	
	protected GoogleMap map;
    protected LatLng start;
    protected LatLng end;
    
    protected int calories;
    protected int emissions;
    protected String transit;
    protected int dist;
    
    /**
     * This activity loads a map and then displays the route and pushpins on it.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);
        
        Bundle extras = getIntent().getExtras();
        
        String startingPoint = extras.getString("startingPoint");
        String destination = extras.getString("destination");
        String transitType = extras.getString("transitType");
        
        transit = transitType;
        
        double startLat = 0;
        double startLong = 0;
        double destLat = 0;
        double destLong = 0;

        
        Geocoder coder = new Geocoder(this);
        try {
            ArrayList<Address> startingPoints = (ArrayList<Address>) coder.getFromLocationName(startingPoint, 15);
            ArrayList<Address> destPoints = (ArrayList<Address>) coder.getFromLocationName(destination, 15);
            for(Address add : startingPoints){
                //if (statement) {//Controls to ensure it is right address such as country etc.
                    startLong = add.getLongitude();
                    startLat = add.getLatitude();
                //}
            }
            
            for(Address add : destPoints){
                //if (statement) {//Controls to ensure it is right address such as country etc.
                    destLong = add.getLongitude();
                    destLat = add.getLatitude();
                //}
            }

            
            setupActionBar();
            
            SupportMapFragment fm = (SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map);
            map = fm.getMap();

            //Hardcode to center of Hoboken for now
            CameraUpdate center=CameraUpdateFactory.newLatLng(new LatLng(40.745713,-74.033221));
            CameraUpdate zoom=  CameraUpdateFactory.zoomTo(15);

            map.moveCamera(center);
            map.animateCamera(zoom);
            
            start = new LatLng(startLat,startLong);
            end = new LatLng(destLat,destLong);

            Log.d("TRANSIT","Transit type is: " + transitType);
            
            //Default to driving directions
            //TravelMode tm = Routing.TravelMode.DRIVING;
            
            //TEST: default to walking directions
            //TravelMode tm = Routing.TravelMode.WALKING;
            
            TravelMode tm;
            
            if(transitType.compareTo("Walking") == 0)
            {
            	tm = Routing.TravelMode.WALKING;
            }
            else if(transitType.compareTo("Driving") == 0)
            {
            	tm = Routing.TravelMode.DRIVING;            	
            }
            else if(transitType.compareTo("Biking") == 0)
            {
            	tm = Routing.TravelMode.BIKING;
            }
            else if(transitType.compareTo("Public Transit") == 0)
            {
            	tm = Routing.TravelMode.TRANSIT;
            }
            else
            {
            	tm = Routing.TravelMode.DRIVING;
            	Log.d("ELSE2","Entered else statement in Act");
            }

            Routing routing = new Routing(tm);
            routing.registerListener(this);
            routing.execute(start, end);
            
            //dist = routing.getDistanceAgain();

            

            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onRoutingFailure() {
      // The Routing request failed
    }

    @Override
    public void onRoutingStart() {
      // The Routing Request starts
    }

    @Override
    public void onRoutingSuccess(PolylineOptions mPolyOptions, int routeDistance) {	
      
    	calories = 50;
    	emissions = 50;
    	    	    	
    	PolylineOptions polyoptions = new PolylineOptions();
      polyoptions.color(Color.BLUE);
      polyoptions.width(10);
      polyoptions.addAll(mPolyOptions.getPoints());
      map.addPolyline(polyoptions);
      

      
      

      
      // Start marker
      /*
      MarkerOptions options = new MarkerOptions();
      options.position(start);
      options.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue));
      options.title("Route Info");
      */
      
      
      double routeCalc;
      routeCalc = 0;
      int intRoute;
      
      if (routeDistance > 0)
    	  routeCalc = routeDistance / 1000;
      routeCalc = routeCalc / 1.60934;
      
      intRoute = (int) Math.round(routeCalc);
      
      if(transit.compareTo("Walking") == 0)
      {
    	  calories = (int) Math.round(routeCalc * 95);
    	  emissions = 0;
      }
      else if(transit.compareTo("Driving") == 0)
      {
      	calories = 0;
      	emissions = (int) Math.round(routeCalc * .375);
      }
      else if(transit.compareTo("Biking") == 0)
      {
    	  calories = (int) Math.round(routeCalc * 64);
    	  emissions = 0;
      }
      else if(transit.compareTo("Public Transit") == 0)
      {
      	emissions = 0;
      }
      
      String cal = Integer.toString(calories);
      String emi = Integer.toString(emissions);
      //String distanceStr = Integer.toString(routeDistance);
      String distanceStr = Integer.toString(intRoute);
      
      //moved to here
      MarkerOptions options = new MarkerOptions()
      .position(start)
      .title("Route Info")
      .snippet("Calories Burned: "+cal+" | Emissions (kg CO2): "+emi +" | "+distanceStr+"miles")
      .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue));
      
      Marker marker = map.addMarker(options);
      //Log.d("this is my error message ", distanceStr) --> it MIGHT expect a string 
      Log.d(TAG, "THE VALUE OF DISTANCE IS:" + distanceStr);
      
      // End marker
      options = new MarkerOptions();
      options.position(end);
      options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green));  
      map.addMarker(options);
    }
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */

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
