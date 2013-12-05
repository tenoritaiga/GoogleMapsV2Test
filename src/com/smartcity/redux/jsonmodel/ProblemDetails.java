package com.smartcity.redux.jsonmodel;

import com.google.gson.annotations.SerializedName;

public class ProblemDetails{
	@SerializedName("Problem Category")
	public String Category;
	
	@SerializedName("Problem Name")
	public String Name;
}
