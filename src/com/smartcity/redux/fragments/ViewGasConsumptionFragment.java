package com.smartcity.redux.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.smartcity.redux.R;

public class ViewGasConsumptionFragment extends Fragment {
	
	public static final String ARG_SECTION_NUMBER = "section_number";

	public ViewGasConsumptionFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_my_gas_result,
				container, false);
		/*TextView dummyTextView = (TextView) rootView
				.findViewById(R.id.section_label);
		dummyTextView.setText(Integer.toString(getArguments().getInt(
				ARG_SECTION_NUMBER)));*/
		// init example series data
		GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
				new GraphViewData(1, 2.0d)
				, new GraphViewData(2, 1.5d)
				, new GraphViewData(3, 2.5d)
				, new GraphViewData(4, 1.0d)
		}); 
		GraphView graphView = new LineGraphView(
				getActivity() // context
				, "GraphViewDemo" // heading
		);
		//GraphView graphView = (GraphView) rootView.findViewById(R.id.graphView);
		graphView.addSeries(exampleSeries); // data
		
		LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.view_cons_results);
		layout.addView(graphView);
		
		TableLayout tableLayout = (TableLayout) rootView.findViewById(R.id.gasResultsTable);
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
		
		tableLayout.addView(tableRow);
		return rootView;
	}
}
