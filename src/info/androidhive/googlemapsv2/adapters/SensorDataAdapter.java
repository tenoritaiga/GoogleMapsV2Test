package info.androidhive.googlemapsv2.adapters;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import info.androidhive.googlemapsv2.R;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

import info.androidhive.googlemapsv2.jsonmodel.Sensor;

public class SensorDataAdapter implements InfoWindowAdapter {
	LayoutInflater inflater = null;
	private TextView textViewTitle;
	public Map <Marker, Sensor> hashMap = new HashMap <Marker, Sensor>();

	public SensorDataAdapter(LayoutInflater inflater) {
		this.inflater = inflater;
	}
	
	@Override
	public View getInfoWindow(Marker marker) {
		//Return null here to use default Android window style
		return null;
	}

	@Override
	public View getInfoContents(Marker marker) {
		View v = inflater.inflate(R.layout.marker, null);
		if (marker != null) {
			Sensor sensor = hashMap.get(marker);
			
			textViewTitle = (TextView) v.findViewById(R.id.textViewTitle);
			textViewTitle.setText(marker.getTitle());
			
			TextView PM10 = (TextView) v.findViewById(R.id.PM10);
			PM10.setText(sensor.Readings.PM10);
			
			TextView PM2_5 = (TextView) v.findViewById(R.id.PM2_5);
			PM2_5.setText(sensor.Readings.PM2_5);
			
			TextView CO = (TextView) v.findViewById(R.id.CO);
			CO.setText(sensor.Readings.CO);
			
			TextView CO2 = (TextView) v.findViewById(R.id.CO2);
			CO2.setText(sensor.Readings.CO2);
			
			TextView Noise = (TextView) v.findViewById(R.id.Noise);
			Noise.setText(sensor.Readings.Noise);
		}
		return (v);
	}
}
