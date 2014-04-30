package com.smartcity.redux.jsonmodel;

import com.google.gson.annotations.SerializedName;

public class Report {
	
	public Report(int rid, int uid, int cid, String cname, String comm, double lat, double lon, String daterep, String dateocc) {
		ReportID = rid;
		UserID = uid;
		CategoryID = cid;
		CategoryName = cname;
		Comment = comm;
		Latitude = lat;
		Longitude = lon;
		DateReported = daterep;
		DateOccurred = dateocc;
	}
	
	@SerializedName("ReportID")
	public int ReportID;
	
	@SerializedName("UserID")
	public int UserID;
	
	@SerializedName("CategoryID")
	public int CategoryID;
	
	@SerializedName("CategoryName")
	public String CategoryName;
	
	@SerializedName("Comment")
	public String Comment;
	
	@SerializedName("Latitude")
	public double Latitude;
	
	@SerializedName("Longitude")
	public double Longitude;
	
	@SerializedName("DateReported")
	public String DateReported;
	
	@SerializedName("DateOccurred")
	public String DateOccurred;
}

