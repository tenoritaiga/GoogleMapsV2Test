package com.smartcity.redux.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartcity.redux.R;

public class MapCategoryFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_map_category, null);
	}
	
	public void startMapActivity(View view){
		Intent intent = new Intent(getActivity(),AirMapFragment.class);
		getActivity().startActivity(intent);
	}
	
	public void startSettingsActivity(View view){
		Intent intent = new Intent(getActivity(),SettingsFragment.class);
		getActivity().startActivity(intent);
	}

}
