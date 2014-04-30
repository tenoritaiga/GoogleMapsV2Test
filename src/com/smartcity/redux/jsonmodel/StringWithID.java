package com.smartcity.redux.jsonmodel;

public class StringWithID {
	
	public String string;
	public int id;
	
	public StringWithID(String stringPart, int intPart)
	{
		string = stringPart;
		id = intPart;
	}
	
	@Override
	public String toString()
	{
		return string;
	}

}
