package com.smartcity.redux.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.smartcity.redux.R;

public class MapCategoryFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_map_category, null);
		
		Button airMapButton = (Button)root.findViewById(R.id.airmapButton);
		Button trafficMapButton = (Button)root.findViewById(R.id.trafficButton);
		Button directionsButton = (Button)root.findViewById(R.id.directionsButton);
		
		airMapButton.setOnClickListener(onClickListener);
		trafficMapButton.setOnClickListener(onClickListener);
		directionsButton.setOnClickListener(onClickListener);
		
		
		//Button pollutionButton = (Button)root.findViewById(R.id.pollutionButton);
		
//		Button button = (Button)root.findViewById(R.id.airmapButton);
//		
//		button.setOnClickListener(new View.OnClickListener() {
//		    public void onClick(View v) {
//
//	            SupportMapFragment frag = new AirMapFragment();
//	            FragmentTransaction ft = getFragmentManager().beginTransaction();
//	            ft.replace(R.id.frame_container, frag);
//	            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//	            ft.addToBackStack(null);
//	            ft.commit();
//	         }
//		     });
		
		return root;
	}
	
	private OnClickListener onClickListener = new OnClickListener() {
		
		Fragment fragment = null;
		
		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.airmapButton:
				fragment = new AirMapFragment();
				break;
			case R.id.trafficButton:
				fragment = new TrafficMapFragment();
				break;
			case R.id.directionsButton:
				fragment = new DirectionsInputFragment();
				break;
			}
			
			if (fragment != null) {
				android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
			} else {
				// error in creating fragment
				Log.e("MapsCategoryFragment", "Error in creating fragment");
			}
		}
	};
	
}
