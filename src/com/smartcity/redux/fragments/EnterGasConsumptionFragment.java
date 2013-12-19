package com.smartcity.redux.fragments;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smartcity.redux.R;

/**
 * A fragment for controlling the Enter Tank Refill tab of the My Gas Consumption functionality.
 */
public class EnterGasConsumptionFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";

	public EnterGasConsumptionFragment() {
	}

	/**
	 * Function that is called when the "Enter Tank Refill" view is created - the function sets the 
	 * layout for the page and populates the date field with the current date.
	 */
	@SuppressLint("SimpleDateFormat")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_my_gas_dummy,
				container, false);

		TextView dateView = (TextView) rootView.findViewById(R.id.view_date);
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		String currentDate = df.format(c.getTime());
		dateView.setText(currentDate);
		return rootView;
	}
}