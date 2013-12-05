package com.smartcity.redux.jsonmodel;

import com.google.gson.annotations.SerializedName;

public class Problem {
	
	@SerializedName("ProblemID")
	public String ProblemID;
	
	@SerializedName("ProblemDetails") // do we need this? or can ID be matched to name
	public ProblemDetails ProblemDetails;
	
	@SerializedName("Location")
	public Location Location;
	
	@SerializedName("DateTime")
	public DateTime DateTime;
}

