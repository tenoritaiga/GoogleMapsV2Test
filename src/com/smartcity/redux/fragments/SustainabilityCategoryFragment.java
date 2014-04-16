package com.smartcity.redux.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartcity.redux.R;
import com.smartcity.redux.SustainabilityCategoryActivity;

public class SustainabilityCategoryFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Intent intent = new Intent(getActivity(), SustainabilityCategoryActivity.class);
		getActivity().startActivity(intent);
		return inflater.inflate(R.layout.fragment_sustainability_category, null);
	}
}
