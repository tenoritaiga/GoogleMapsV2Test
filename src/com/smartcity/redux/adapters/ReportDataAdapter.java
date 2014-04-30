package com.smartcity.redux.adapters;

import java.util.HashMap;
import java.util.Map;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;
import com.smartcity.redux.R;
import com.smartcity.redux.jsonmodel.Report;

public class ReportDataAdapter implements InfoWindowAdapter {
	LayoutInflater inflater = null;
	private TextView reportMarkerTitle;
	
	public Map <Marker, Report> hashMap = new HashMap <Marker, Report>();

	public ReportDataAdapter(LayoutInflater inflater) {
		this.inflater = inflater;
	}
	
	@Override
	public View getInfoContents(Marker marker) {
		//Not using default Android window style
		return null;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		View v = inflater.inflate(R.layout.marker311, null);
		if (marker != null) {
			Report report = hashMap.get(marker);
			
			reportMarkerTitle = (TextView) v.findViewById(R.id.reportMarkerTitle);
			reportMarkerTitle.setText(marker.getTitle());
			
			TextView ReportedBy = (TextView) v.findViewById(R.id.ReportedBy);
			ReportedBy.setText("Reported by user " + report.UserID);			
			
			TextView TimeOfReport = (TextView) v.findViewById(R.id.TimeOfReport);
			TimeOfReport.setText("reported: " + report.DateReported);
			
			TextView TimeOfProblem = (TextView) v.findViewById(R.id.TimeOfProblem);
			TimeOfProblem.setText("occurred: " + report.DateOccurred);	
			
			TextView Comment = (TextView) v.findViewById(R.id.Comment);
			Comment.setText("Comment: " + report.Comment);
		}
		return (v);
	}
}
