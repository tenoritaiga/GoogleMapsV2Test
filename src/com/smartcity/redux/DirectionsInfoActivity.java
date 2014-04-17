package com.smartcity.redux;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.smartcity.redux.route.GoogleParser;
import com.smartcity.redux.route.Route;
import com.smartcity.redux.route.Routing;
import com.smartcity.redux.route.Routing.TravelMode;
import com.smartcity.redux.route.Segment;


public class DirectionsInfoActivity extends FragmentActivity
{
    private static final String TAG = "DirectionsInfoActivity";
	
	protected GoogleMap map;
    protected LatLng start;
    protected LatLng end;
    
    protected int calories;
    protected int emissions;
    protected float gas;
    protected String transit;
    protected int dist;
    
    String startingPoint;
    String destination;
    String transitType;
    
    /**
     * This activity loads a map and then displays the route and pushpins on it.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions_info);
        
        Bundle extras = getIntent().getExtras();
        
        startingPoint = extras.getString("startingPoint");
        destination = extras.getString("destination");
        transitType = extras.getString("transitType");
        
        transit = transitType;
        
        double startLat = 0;
        double startLong = 0;
        double destLat = 0;
        double destLong = 0;
        
        ArrayList<String> dirlist = new ArrayList<String>();

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
            
            
            start = new LatLng(startLat,startLong);
            end = new LatLng(destLat,destLong);

            //Log.d("TRANSIT","Transit type is: " + transitType);
            
            //Default to driving directions
            //TravelMode tm = Routing.TravelMode.DRIVING;
            
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
            	//Log.d("ELSE","Entered else statement in InfoAct");
            }

            Routing routing = new Routing(tm);
            routing.execute(start, end);
            
            Route route = routing.get();
            
            List<Segment> seg = route.getSegments();
            
            //Log.d("SEG",seg.toString());
            
            for (Segment segment : seg) {
            	//Log.d("OKAY",segment.getInstruction());
            	dirlist.add(segment.getInstruction());
              }
            
            int routeDistance = route.getTotalDistance();
            
            Spanned[] htmldirections = new Spanned[dirlist.size()];
            for(int i = 0 ; i < dirlist.size(); i++) {
            htmldirections[i] = Html.fromHtml(dirlist.get(i));
            }
            
            ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_list_item_1,htmldirections);
            
            ListView lv = (ListView)findViewById(R.id.directionsList);
            TextView et = (TextView)findViewById(R.id.emissionsTextView);
            TextView ct = (TextView)findViewById(R.id.caloriesTextView);
            TextView td = (TextView)findViewById(R.id.totalDistanceTextView);
            TextView gt = (TextView)findViewById(R.id.gasTextView);

            lv.setAdapter(arrayAdapter);
            
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
          	  gas = 0;
            }
            else if(transit.compareTo("Driving") == 0)
            {
            	calories = 0;
            	emissions = (int) Math.round(routeCalc * .375);
            	gas = (float) (Math.round((routeCalc / 25)*10))/10; //assuming the average gas mileage is 25 mpg
            }
            else if(transit.compareTo("Biking") == 0)
            {
          	  calories = (int) Math.round(routeCalc * 64);
          	  emissions = 0;
          	  gas = 0;
            }
            else if(transit.compareTo("Public Transit") == 0)
            {
            	emissions = 0;
            	gas = 0;
            }

            String cal = Integer.toString(calories);
            String emi = Integer.toString(emissions);
            String gasString = Float.toString(gas);
            String distanceStr = Integer.toString(intRoute);
            
            ct.setText(cal + " cal");
            et.setText(emi + " kg CO2");
            td.setText("Total Distance: " + distanceStr + " mi");
            gt.setText(gasString + "gal");
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    
    
	public void startDirectionsActivity(View view) {
		Intent intent = new Intent(DirectionsInfoActivity.this,DirectionsActivity.class);
		
		intent.putExtra("startingPoint",startingPoint);
		intent.putExtra("destination", destination);
		intent.putExtra("transitType",transitType);
		
		DirectionsInfoActivity.this.startActivity(intent);
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
