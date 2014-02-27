package com.smartcity.redux.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.smartcity.redux.R;

public class CityCategoryFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View root = inflater.inflate(R.layout.fragment_city_category, null);
		
		Button button = (Button)root.findViewById(R.id.cityEvents);
		
		button.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {

	            Fragment frag = new CityEventsFragment();
	            FragmentTransaction ft = getFragmentManager().beginTransaction();
	            ft.replace(R.id.frame_container, frag);
	            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	            ft.addToBackStack(null);
	            ft.commit();
	         }
		     });
		
		return root;
	}
}
