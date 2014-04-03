package com.smartcity.redux.adapters;

import java.util.HashMap;
import java.util.Map;

import com.smartcity.redux.R;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;
import com.smartcity.redux.jsonmodel.ParkingSensor;

public class ParkingData implements InfoWindowAdapter{
	LayoutInflater inflater = null;
	private TextView textViewTitle;
	private TextView textViewType;
	public Map <Marker, ParkingSensor> hashMap = new HashMap <Marker, ParkingSensor>();

	public ParkingData(LayoutInflater inflater) {
		this.inflater = inflater;
	}
	
	@Override
	public View getInfoContents(Marker marker) {
		//Not using default Android window style
		return null;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		View v = inflater.inflate(R.layout.marker, null);
		if (marker != null) {
			ParkingSensor sensor = hashMap.get(marker);
			
			textViewTitle = (TextView) v.findViewById(R.id.textViewTitle);
			textViewTitle.setText(marker.getTitle());
			
			textViewType = (TextView) v.findViewById(R.id.textViewType);
			textViewType.setText("Parking status: " + marker.getSnippet());
			/*
			TextView PM10 = (TextView) v.findViewById(R.id.PM10);
			PM10.setText("PM10: " + sensor.Readings.PM10);
			
			TextView PM2_5 = (TextView) v.findViewById(R.id.PM2_5);
			PM2_5.setText("PM2.5: " + sensor.Readings.PM2_5);
			
			TextView CO = (TextView) v.findViewById(R.id.CO);
			CO.setText("CO: " + sensor.Readings.CO);
			
			TextView CO2 = (TextView) v.findViewById(R.id.CO2);
			CO2.setText("CO2: " + sensor.Readings.CO2);
			
			TextView Noise = (TextView) v.findViewById(R.id.Noise);
			Noise.setText("Noise: " + sensor.Readings.Noise);
			
			TextView DateTime = (TextView) v.findViewById(R.id.DateTime);
			DateTime.setText("Last updated today at: " + sensor.DateTime.Time+":00");
			*/
		}
		return (v);
	}
}
