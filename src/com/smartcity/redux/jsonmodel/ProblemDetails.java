package com.smartcity.redux.jsonmodel;

import com.google.gson.annotations.SerializedName;

public class ProblemDetails{
	@SerializedName("Category")
	public String Category;
	
	@SerializedName("Name")
	public String Name;
	
	@SerializedName("Comment")
	public String Comment;
}
