package com.smartcity.redux.jsonmodel;

import com.google.gson.annotations.SerializedName;

public class Readings{
	@SerializedName("PM10")
	public String PM10;
	
	@SerializedName("PM2_5")
	public String PM2_5;
	
	@SerializedName("CO")
	public String CO;
	
	@SerializedName("CO2")
	public String CO2;
	
	@SerializedName("Noise")
	public String Noise;
}
