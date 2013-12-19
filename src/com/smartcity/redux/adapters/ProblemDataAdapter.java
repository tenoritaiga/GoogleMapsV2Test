package com.smartcity.redux.adapters;

import java.util.HashMap;
import java.util.Map;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;
import com.smartcity.redux.R;
import com.smartcity.redux.jsonmodel.Problem;

public class ProblemDataAdapter implements InfoWindowAdapter {
	LayoutInflater inflater = null;
	private TextView problemMarkerTitle;
	
	public Map <Marker, Problem> hashMap = new HashMap <Marker, Problem>();

	public ProblemDataAdapter(LayoutInflater inflater) {
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
			Problem problem = hashMap.get(marker);
			
			problemMarkerTitle = (TextView) v.findViewById(R.id.problemMarkerTitle);
			problemMarkerTitle.setText(marker.getTitle());
			
			TextView ReportedBy = (TextView) v.findViewById(R.id.ReportedBy);
			ReportedBy.setText("Reported by: " + problem.UserName);			
			
		/*	TextView TimeOfReport = (TextView) v.findViewById(R.id.TimeOfReport);
			TimeOfReport.setText("at: " + problem.DateTimeReported); */	
			
			TextView TimeOfProblem = (TextView) v.findViewById(R.id.TimeOfProblem);
			TimeOfProblem.setText("from: " + problem.DateTimeOccurred.Date.toString() + " at " + problem.DateTimeOccurred.Time);	
			
			TextView Comment = (TextView) v.findViewById(R.id.Comment);
			Comment.setText("Comment: " + problem.ProblemDetails.Comment);
		}
		return (v);
	}
}
