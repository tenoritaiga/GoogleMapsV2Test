package com.smartcity.redux.fragments;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smartcity.redux.R;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 */
public class EnterGasConsumptionFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";

	public EnterGasConsumptionFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_my_gas_dummy,
				container, false);
		/*TextView dummyTextView = (TextView) rootView
				.findViewById(R.id.section_label);
		dummyTextView.setText(Integer.toString(getArguments().getInt(
				ARG_SECTION_NUMBER)));*/
		TextView dateView = (TextView) rootView.findViewById(R.id.view_date);
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		String currentDate = df.format(c.getTime());
		dateView.setText(currentDate);
		return rootView;
	}
}