package com.smartcity.redux.jsonmodel;

import com.google.gson.annotations.SerializedName;

public class Problem {
	
	@SerializedName("UserID")
	public int UserID;
	
	@SerializedName("UserName")
	public String UserName;
	
	@SerializedName("ProblemID")
	public String ProblemID;
	
	@SerializedName("ProblemDetails") // GET RID OF THIS
	public ProblemDetails ProblemDetails;
	
	@SerializedName("Location")
	public Location Location;
	
	@SerializedName("DateTimeReported")
	public DateTime DateTimeReported;
	
	@SerializedName("DateTimeOccurred")
	public DateTime DateTimeOccurred;
}

