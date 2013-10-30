package info.androidhive.googlemapsv2.jsonmodel;

import com.google.gson.annotations.SerializedName;

public class Sensor {
	
	@SerializedName("SensorName")
	public String SensorName;
	
	@SerializedName("Readings")
	public Readings Readings;
	
	@SerializedName("Location")
	public Location Location;
	
	@SerializedName("DateTime")
	public DateTime DateTime;
}

