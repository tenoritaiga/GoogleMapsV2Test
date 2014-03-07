package com.smartcity.redux.fragments;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.smartcity.redux.R;
import com.smartcity.redux.adapters.PlacesAutoCompleteAdapter;

public class DirectionsInputFragment extends Fragment implements OnItemClickListener {
	
	private String API_KEY;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_air_quality);
		View root = inflater.inflate(R.layout.activity_directions_input, null);

		 API_KEY = getActivity().getResources().getText(R.string.MapsAPIKey).toString();
		 
		 //Log.d("APIKEY",API_KEY);
		
		setupActionBar();
		
		return root;
	}
	
	@Override
	public void onActivityCreated(Bundle bundle){
		super.onActivityCreated(bundle);
		
		AutoCompleteTextView destinationInput = (AutoCompleteTextView) getActivity().findViewById(R.id.destinationInput);
		AutoCompleteTextView startingPointInput = (AutoCompleteTextView) getActivity().findViewById(R.id.startingPointInput);
		
		if(getActivity() == null)
			Log.d("OOPS","getActivity() returned null");
		
		startingPointInput.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), android.R.layout.test_list_item));
		startingPointInput.setOnItemClickListener(this);
		
		destinationInput.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), android.R.layout.test_list_item));
		destinationInput.setOnItemClickListener(this);
	}
	
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String str = (String) adapterView.getItemAtPosition(position);
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    inflater.inflate(R.menu.directions_input, menu);
	    super.onCreateOptionsMenu(menu,inflater);
	}
	
	public void startDirectionsInfoActivity(View view) {
		Intent intent = new Intent(getActivity(),DirectionsInfoFragment.class);
		
		EditText startingPoint = (EditText) getActivity().findViewById(R.id.startingPointInput);
		EditText destination = (EditText) getActivity().findViewById(R.id.destinationInput);
		Spinner transitSpinner = (Spinner) getActivity().findViewById(R.id.spn_directions_type);
		
		String startingPointText = startingPoint.getText().toString();
		String destinationText = destination.getText().toString();
		String transitType = transitSpinner.getSelectedItem().toString();
		
		intent.putExtra("startingPoint",startingPointText);
		intent.putExtra("destination", destinationText);
		intent.putExtra("transitType",transitType);
		
		DirectionsInputFragment.this.startActivity(intent);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(getActivity());
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
