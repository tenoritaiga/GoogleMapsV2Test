package com.smartcity.redux;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MarketplaceActivity extends Activity {
//availablePointsText
	
	private int availablePoints;
	private TextView ap; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_marketplace);
		
		ap = (TextView)findViewById(R.id.availablePointsText);
		
		availablePoints = 450;
		
		String points = Integer.toString(availablePoints);
		ap.setText(points);
		/**
		final Button button1 = (Button) findViewById(R.id.reward1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                subPoints(100);
            }
        });
        **/
	}
	
	public void subPoints(int points)
	{
		/*
		availablePoints = availablePoints - points;
		if (availablePoints <= 0)
		{
			availablePoints = 0;
		}
		String p = Integer.toString(availablePoints);
		ap.setText(p);
		*/
		String p;
		
		if (availablePoints - points < 0)
		{
			//do nothing
			//TODO: alert of some sort
		}
		else if (availablePoints - points == 0)
		{
			availablePoints = 0;
			p = Integer.toString(availablePoints);
			ap.setText(p);
		}
		else
		{
			availablePoints = availablePoints - points;
			p = Integer.toString(availablePoints);
			ap.setText(p);
		}
		
	}
	
	public void clickButton(View v)
	{
		switch(v.getId())
		{
		case R.id.reward1:
			subPoints(100);
			break;
		case R.id.reward2:
			subPoints(150);
			break;
		case R.id.reward3:
			subPoints(200);
			break;
		case R.id.reward4:
			subPoints(300);
			break;
		case R.id.reward5:
			subPoints(400);
			break;
		case R.id.reward6:
			subPoints(500);
			break;
		case R.id.reward7:
			subPoints(600);
			break;
		case R.id.reward8:
			subPoints(700);
			break;
		}
	
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.marketplace, menu);
		return true;
	}

}
