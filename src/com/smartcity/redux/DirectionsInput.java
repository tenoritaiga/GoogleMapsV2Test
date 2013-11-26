package com.smartcity.redux;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class DirectionsInput extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_directions_input);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.directions_input, menu);
		return true;
	}
	
	public void startDirectionsActivity(View view) {
		Intent intent = new Intent(DirectionsInput.this,DirectionsActivity.class);
		
		EditText startingPoint = (EditText) findViewById(R.id.startingPointInput);
		EditText destination = (EditText) findViewById(R.id.destinationInput);
		
		String startingPointText = startingPoint.getText().toString();
		String destinationText = destination.getText().toString();
		
		intent.putExtra("startingPoint",startingPointText);
		intent.putExtra("destination", destinationText);
		
		DirectionsInput.this.startActivity(intent);
	}

}
