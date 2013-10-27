package info.androidhive.googlemapsv2;

import android.app.Activity;
//import android.graphics.Color;
import android.os.Bundle;
//import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class MainActivity extends Activity {

	// Google Map
	private GoogleMap googleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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

			double latitude = 40.745066;
			double longitude = -74.024294;
			
			LatLng CurlingClub = new LatLng(40.751912,-74.03185);
			LatLng problemSpot = new LatLng(40.746257,-74.036996);
			LatLng epicenter = new LatLng(40.748907,-74.029335);
			
			LatLng coord1 = new LatLng(40.740607823531626, -74.03381824493408);
			LatLng coord2 = new LatLng(40.73816909729785, -74.03467655181885);
			LatLng coord3 = new LatLng(40.7376000483119, -74.0317153930664);
			LatLng coord4 = new LatLng(40.7401526014217, -74.03077125549316);
			
			LatLng coord5 = new LatLng(40.74559880446954, -74.0333890914917);
			LatLng coord6 = new LatLng(40.74340411957342, -74.03589963912964);
			LatLng coord7 = new LatLng(40.741859668270294, -74.03336763381958);
			LatLng coord8 = new LatLng(40.742558740144865, -74.0312647819519);
			
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			googleMap.addMarker(new MarkerOptions()
			        .position(new LatLng(latitude,longitude))
			        .title("This is Stevens!")
			        .snippet("Go Ducks!")
			        .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_g)));
			
			googleMap.addMarker(new MarkerOptions()
	        .position(CurlingClub)
	        .title("Curling Club Apartments")
	        .snippet("1132 Clinton Street")
	        .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_y)));
			
			googleMap.addMarker(new MarkerOptions()
	        .position(problemSpot)
	        .title("There's some sort of problem here.")
	        .snippet("Maybe this shows the details.")
	        .icon(BitmapDescriptorFactory.fromResource(R.drawable.alert)));
			
			 Polygon polygon = googleMap.addPolygon(new PolygonOptions()
		     .add(coord1,coord2,coord3,coord4,coord1)
		     .strokeColor(0x7F2EACFF)
		     .strokeWidth(8)
		     .fillColor(0x7F00FF00));
			 
			 Polygon polygon2 = googleMap.addPolygon(new PolygonOptions()
		     .add(coord5,coord6,coord7,coord8,coord5)
		     .strokeColor(0xB3F3EC24)
		     .strokeWidth(8)
		     .fillColor(0xB3F34724));
			 
			 Circle circle = googleMap.addCircle(new CircleOptions()
		     .center(epicenter)
		     .radius(150)
		     .strokeWidth(5)
		     .strokeColor(0xFFFFFFFF)
		     .fillColor(0xB3F53BF5));

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

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}

	/**
	 * function to load map If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	/*
	 * creating random position around a location for testing purpose only
	 */
	private double[] createRandLocation(double latitude, double longitude) {

		return new double[] { latitude + ((Math.random() - 0.5) / 500),
				longitude + ((Math.random() - 0.5) / 500),
				150 + ((Math.random() - 0.5) * 10) };
	}
}
