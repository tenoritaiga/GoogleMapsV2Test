package com.smartcity.redux.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartcity.redux.ProfileActivity;
import com.smartcity.redux.R;

public class ProfileFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Intent intent = new Intent(getActivity(), ProfileActivity.class);
		getActivity().startActivity(intent);
		return inflater.inflate(R.layout.activity_profile, null);
	}
}
