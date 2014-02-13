package com.smartcity.redux.fragments;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.smartcity.redux.R;
import com.smartcity.redux.route.Route;
import com.smartcity.redux.route.Routing;
import com.smartcity.redux.route.Routing.TravelMode;
import com.smartcity.redux.route.Segment;

public class DirectionsInfoFragment extends Fragment {
	
private static final String TAG = "DirectionsInfoActivity";
	
	protected GoogleMap map;
    protected LatLng start;
    protected LatLng end;
    
    protected int calories;
    protected int emissions;
    protected String transit;
    protected int dist;
    
    String startingPoint;
    String destination;
    String transitType;
    
    /**
     * This activity loads a map and then displays the route and pushpins on it.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_directions_info);
    	View root = inflater.inflate(R.layout.activity_directions_info, null);
        
        Bundle extras = getActivity().getIntent().getExtras();
        
        startingPoint = extras.getString("startingPoint");
        destination = extras.getString("destination");
        transitType = extras.getString("transitType");
        
        transit = transitType;
        
        double startLat = 0;
        double startLong = 0;
        double destLat = 0;
        double destLong = 0;
        
        ArrayList<String> dirlist = new ArrayList<String>();

        Geocoder coder = new Geocoder(getActivity());
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

            Log.d("TRANSIT","Transit type is: " + transitType);
            
            //Default to driving directions
            TravelMode tm = Routing.TravelMode.DRIVING;
            
            if(transitType == "Walking")
            {
            	tm = Routing.TravelMode.WALKING;
            }
            else if(transitType == "Driving")
            {
            	tm = Routing.TravelMode.DRIVING;            	
            }
            else if(transitType == "Biking")
            {
            	tm = Routing.TravelMode.BIKING;
            }
            else if(transitType == "Public Transit")
            {
            	tm = Routing.TravelMode.TRANSIT;
            }

            Routing routing = new Routing(tm);
            routing.execute(start, end);
            
            Route route = routing.get();
            
            List<Segment> seg = route.getSegments();
            
            Log.d("SEG",seg.toString());
            
            for (Segment segment : seg) {
            	Log.d("OKAY",segment.getInstruction());
            	dirlist.add(segment.getInstruction());
              }
            
            int routeDistance = route.getTotalDistance();
            
            Spanned[] htmldirections = new Spanned[dirlist.size()];
            for(int i = 0 ; i < dirlist.size(); i++) {
            htmldirections[i] = Html.fromHtml(dirlist.get(i));
            }
            
            ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<CharSequence>(getActivity(),android.R.layout.simple_list_item_1,htmldirections);
            
            ListView lv = (ListView)getActivity().findViewById(R.id.directionsList);
            TextView et = (TextView)getActivity().findViewById(R.id.emissionsTextView);
            TextView ct = (TextView)getActivity().findViewById(R.id.caloriesTextView);
            TextView td = (TextView)getActivity().findViewById(R.id.totalDistanceTextView);

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
            String distanceStr = Integer.toString(intRoute);
            
            ct.setText(cal + " cal");
            et.setText(emi + " gal");
            td.setText("Total Distance: " + distanceStr + " mi");
  
        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;
    }

	public void startDirectionsActivity(View view) {
		Intent intent = new Intent(getActivity(),DirectionsFragment.class);
		
		intent.putExtra("startingPoint",startingPoint);
		intent.putExtra("destination", destination);
		intent.putExtra("transitType",transitType);
		
		getActivity().startActivity(intent);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */

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
			NavUtils.navigateUpFromSameTask(getActivity());
			return true;
		}
		return super.onOptionsItemSelected(item);
		
	}

}
