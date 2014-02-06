package com.smartcity.redux.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartcity.redux.R;

/**
 * Fragment for controlling the View Consumption Data tab of the My Gas Consumption functionality.
 * @author Class2013
 *
 */
public class ViewGasConsumptionFragment extends Fragment {
	
	public static final String ARG_SECTION_NUMBER = "section_number";

	public ViewGasConsumptionFragment() {
	}

	/**
	 * Function that is called when the "View Consumption Data" view is created - it sets the 
	 * layout for the page. Currently this function is also being used setting up the bar graph that will 
	 * eventually be used for displaying average consumption data.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_my_gas_result,
				container, false);
		
		/*TableLayout tableLayout = (TableLayout) rootView.findViewById(R.id.gasResultsTable);
		TableRow tableRow = new TableRow(getActivity());
		tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
		
		TextView text1 = new TextView(getActivity());
		text1.setText("11/30/2013");
		text1.setPadding(15, 0, 15, 0);
		text1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
		
		TextView text2 = new TextView(getActivity());
		text2.setText("45");
		text2.setPadding(15, 0, 15, 0);
		text2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
		
		TextView text3 = new TextView(getActivity());
		text3.setText("$98.45");
		text3.setPadding(15, 0, 15, 0);
		text3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
		
		tableRow.addView(text1);
		tableRow.addView(text2);
		tableRow.addView(text3);
		
		tableLayout.addView(tableRow);*/
		return rootView;
	}
}
