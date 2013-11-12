package com.smartcity.redux.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartcity.redux.AirMapActivity;
import com.smartcity.redux.MainActivity;
import com.smartcity.redux.R;
import com.smartcity.redux.SettingsActivity;

public class MainFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		//super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		return inflater.inflate(R.layout.activity_main, null);
	}
	
	public void startMapActivity(View view){
		Intent intent = new Intent(getActivity(),AirMapActivity.class);
		getActivity().startActivity(intent);
	}
	
	public void startSettingsActivity(View view){
		Intent intent = new Intent(getActivity(),SettingsActivity.class);
		getActivity().startActivity(intent);
	}

}
