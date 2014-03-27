package com.smartcity.redux.jsonmodel;

import com.google.gson.annotations.SerializedName;

public class AirSensor {
	@SerializedName("SensorName")
	public String SensorName;

	@SerializedName("SensorType")
	public String SensorType;

	@SerializedName("Readings")
	public Readings Readings;
	
	@SerializedName("Location")
	public Location Location;
	
	@SerializedName("DateTime")
	public DateTime DateTime;
	

}
