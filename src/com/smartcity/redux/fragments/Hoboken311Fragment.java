package com.smartcity.redux.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.smartcity.redux.AirMapActivity;
import com.smartcity.redux.Hoboken311MapActivity;
import com.smartcity.redux.R;

public class Hoboken311Fragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.activity_hoboken311, null);
	}
	
	public void startHoboken311MapActivity(View view){
		Intent intent = new Intent(getActivity(),Hoboken311MapActivity.class);
		getActivity().startActivity(intent);
	}

}