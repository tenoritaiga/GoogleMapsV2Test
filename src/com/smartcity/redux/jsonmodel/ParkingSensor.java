package com.smartcity.redux.jsonmodel;

import com.google.gson.annotations.SerializedName;

public class ParkingSensor {

	@SerializedName("SensorName")
	public String SensorName;
	
	@SerializedName("Location")
	public Location Location;
	
	@SerializedName("DateTime")
	public DateTime DateTime;
	
}
