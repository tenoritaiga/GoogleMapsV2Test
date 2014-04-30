package com.smartcity.redux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartcity.redux.jsonmodel.StringWithID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Hoboken311Activity extends Activity implements OnItemSelectedListener {

	private HashMap<String,Integer> tabHash;
	private ArrayList<String> text;
	
	Spinner spn_categoryType;
	Spinner spn_category;
	
	TextView dateText1;
	TextView timeText1;
	
	int categoryTypeSelected; // more general
	int categorySelected = -1; // specific
	
	String categoryNameSelected;
	
	double lat, lon, gpslat, gpslon;
	boolean gpsreading = false;
	
	EditText commentText;
	
	Switch GPStoggle;
	EditText addressText;
	
	Context context;
	
	private LocationManager manager;
	
	ArrayAdapter<CharSequence> currentAdapter;
	ArrayAdapter<CharSequence> currentAdapter2;
	
	String request = null;
	
	String time_reported = null;
	String date_reported = null;
	
	Toast warning, not200, yay200;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		warning = Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT);
		
		not200 = Toast.makeText(this, "Backend didn't like that", Toast.LENGTH_SHORT);
		yay200 = Toast.makeText(this, "Success!", Toast.LENGTH_SHORT);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hoboken311);
		
		context = this;
		
		spn_categoryType = (Spinner)findViewById(R.id.spn_categoryType);
		spn_category = (Spinner)findViewById(R.id.spn_category);
		
		commentText = (EditText)findViewById(R.id.comment_311_text);
		
		GPStoggle = (Switch)findViewById(R.id.switch1);
		addressText = (EditText)findViewById(R.id.address);
		
		dateText1 = (TextView)findViewById(R.id.dateText1);
		timeText1 = (TextView)findViewById(R.id.timeText1);
		
		 manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	     
			if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				buildAlertMessageNoGps();
			}
			
			LocationListener locationListener = new LocationListener() {
				public void onLocationChanged(Location loc) {
					gpslat = loc.getLatitude();
					gpslon = loc.getLongitude();
					gpsreading = true;
				}
				
				public void onStatusChanged(String provider, int status, Bundle extras) {}

				public void onProviderEnabled(String provider) {}

				public void onProviderDisabled(String provider) {}
			};
			
			manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
			
		
		final DatePickerDialog.OnDateSetListener dateOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
				dateText1.setText(new StringBuilder().append(selectedMonth+1).append("/").append(selectedDay).append("/").append(selectedYear));
			}
		};
		
		dateText1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				int monthOfYear = cal.get(Calendar.MONTH);
				int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
				date_reported = new StringBuilder().append(monthOfYear+1).append("/").append(dayOfMonth).append("/").append(year).toString();
				new DatePickerDialog(context, dateOnDateSetListener, year, monthOfYear, dayOfMonth).show();
			}
		});
		
		final TimePickerDialog.OnTimeSetListener timeOnTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				timeText1.setText(new StringBuilder().append(String.format("%02d",hourOfDay)).append(":").append(String.format("%02d",minute)));
			}
		};
		
		timeText1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar cal = Calendar.getInstance();
				int hour = cal.get(Calendar.HOUR_OF_DAY);
				int minute = cal.get(Calendar.MINUTE);
				time_reported = new StringBuilder().append(String.format("%02d",hour)).append(":").append(String.format("%02d",minute)).toString();
				new TimePickerDialog(context, timeOnTimeSetListener, hour, minute, true).show();
			}
		});
		
		commentText.setVisibility(View.GONE);
		timeText1.setVisibility(View.GONE);
		dateText1.setVisibility(View.GONE);
		
		GPStoggle.setVisibility(View.GONE);
		addressText.setVisibility(View.GONE);
		
		spn_categoryType.setOnItemSelectedListener(this);
		
		spn_category.setVisibility(View.GONE);
		spn_category.setOnItemSelectedListener(this);
				
		GPStoggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			 @Override
			 public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				 if(arg1 == true) {
					 addressText.setVisibility(View.GONE);
					 
						//Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						if(gpsreading) {
							lat = gpslat;
							lon = gpslon;
						}
						else {
							GPStoggle.setChecked(false);
							addressText.setVisibility(View.VISIBLE);
							addressText.requestFocus();

						}
						
				 }
				 else {
					 addressText.setVisibility(View.VISIBLE);
					 addressText.requestFocus();
				 }
			 }
		});
		
		addressText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				EditText t = (EditText) v;
				if(t.getText().toString().replaceAll("\\s","").length() > 0) {
					
					Geocoder coder = new Geocoder(getApplicationContext());
					try {
						List<Address> list = coder.getFromLocationName(addressText.getText().toString()+", 07030", 1, 40.7355633,-74.0512853, 40.7628784,-74.0224692);
						//XXX: get rid of the hardcoded 07030
						Address addr = list.get(0);
						lat = addr.getLatitude();
						lon = addr.getLongitude();
					} catch (IOException e) {
						//XXX: this will crash if improper
						addressText.setText("input a proper address");
					}
			}
				return false;
			}
		});
		
addressText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				EditText t = (EditText) v;
				if(hasFocus == false && t.getText().toString().replaceAll("\\s","").length() > 0) {
					
					Geocoder coder = new Geocoder(getApplicationContext());
					try {
						List<Address> list = coder.getFromLocationName(addressText.getText().toString()+", 07030", 1, 40.7355633,-74.0512853, 40.7628784,-74.0224692);
						//XXX: get rid of the hardcoded 07030... and this block of code here twice
						Address addr = list.get(0);
						lat = addr.getLatitude();
						lon = addr.getLongitude();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						addressText.setText("input a full address");
					}
					
			}
		}
	});

		
		new GetCategoryTypes().execute(); // POPULATES initial list
	
		
		Button btn_map = (Button)findViewById(R.id.btn_311_map);
		btn_map.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(context, Hoboken311MapActivity.class);

				//	i.putExtra("REQUEST", request);
					startActivity(i);
					finish();
				
			}
		});
		
		Button btn_send = (Button)findViewById(R.id.btn_311_send);
		btn_send.setOnClickListener(new View.OnClickListener() {
			
			@Override 
			
			public void onClick(View v) { 
				//XXX: and if userID set, also if lat/lon is good?
				if(categorySelected>=0 &&
							commentText.getText().length() > 0 && 
							date_reported != null && time_reported != null &&
							dateText1.getText().toString() != "Choose Date" &&
							timeText1.getText().toString() != "Choose Time"){ 
					new JsonSender().execute();
				}else{
					warning.show();
				}
			}
		});
			
		//general
		spn_categoryType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        StringWithID s = (StringWithID) parentView.getItemAtPosition(position);
		        categoryTypeSelected = s.id;
		        if(s.toString() != "Select Type") {
			        spn_category.setVisibility(View.VISIBLE);
					new GetCategories().execute();
		        }
		        else
		        {
			        spn_category.setVisibility(View.GONE);
			        commentText.setVisibility(View.GONE);
				    timeText1.setVisibility(View.GONE);
				    dateText1.setVisibility(View.GONE);
				    spn_category.setSelection(0); 
		        }
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }
		});
		
		//specific
		spn_category.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        StringWithID s = (StringWithID) parentView.getItemAtPosition(position);
		        categorySelected = s.id;
		        categoryNameSelected = s.toString();
		        if(s.toString() != "Select Category") {
			        commentText.setVisibility(View.VISIBLE);
			        timeText1.setVisibility(View.VISIBLE);
			        dateText1.setVisibility(View.VISIBLE);
			        GPStoggle.setVisibility(View.VISIBLE);
			        addressText.setVisibility(View.VISIBLE);
			       
		        }
		        else
		        {
		        	 commentText.setVisibility(View.GONE);
				     timeText1.setVisibility(View.GONE);
				     dateText1.setVisibility(View.GONE);
				     GPStoggle.setVisibility(View.GONE);
				     addressText.setVisibility(View.GONE);
		        }
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }
		});
	}
	
			
	private class JsonSender extends AsyncTask<Void,Void,Void> {
		@Override
		protected Void doInBackground(Void... params) {
			JSONObject json = new JSONObject();

			int userID = 2; //XXX: actually get userID
			
			try {
			json.put("UserID", userID);
			json.put("CategoryID", categorySelected);
			json.put("Comment", commentText.getText().toString());
			
			json.put("Latitude", lat);
			json.put("Longitude", lon); 
			
			json.put("DateReported", date_reported + " " + time_reported);
			json.put("DateOccurred", dateText1.getText().toString() + " " + timeText1.getText().toString()); 
			
		//	json.put("CategoryName", categoryNameSelected);
			
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost("http://smartcity1.cloudapp.net/api/CityReportingReport");
			StringEntity se = new StringEntity(json.toString().replaceAll("\\\\", "")); 
			System.out.println(json.toString().replaceAll("\\\\", ""));
			se.setContentType("application/json");
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			postRequest.setEntity(se);
			HttpResponse response = client.execute(postRequest);
			int code = response.getStatusLine().getStatusCode();
			System.out.println(code);
			if(code == 200) { 
				yay200.show();
			} else { not200.show(); }
			return null;
			}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	private class GetCategoryTypes extends AsyncTask<Void, Void, String> {
				protected String doInBackground(Void... params) {
					StringBuilder builder = new StringBuilder();
					HttpClient client = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet("http://smartcity1.cloudapp.net/api/CityReportingReportCategoryType");
					try {
						HttpResponse response = client.execute(httpGet);
						StatusLine statusLine = response.getStatusLine();
						int statusCode = statusLine.getStatusCode();
						if (statusCode == 200) {
							HttpEntity entity = response.getEntity();
							InputStream content = entity.getContent();
							BufferedReader reader = new BufferedReader(
									new InputStreamReader(content));
							String line;
							while ((line = reader.readLine()) != null) {
								builder.append(line);
							}
						} else {
							// Failed to download file
						}
					}
					catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return builder.toString();
				}
				
				protected void onPostExecute(String result) {
					System.out.println(result);
					JSONArray jsonArray;
					JSONObject jsonObject;
					try {
						jsonArray = new JSONArray(result);
						ArrayList<StringWithID> list = new ArrayList<StringWithID>();
						list.add(new StringWithID("Select Type", -1));
						for (int i = 0; i < jsonArray.length(); i++) {
						  jsonObject = jsonArray.getJSONObject(i);
						  list.add(new StringWithID(jsonObject.getString("CategoryName"), jsonObject.getInt("CategoryTypeID")));
						}
						ArrayAdapter<StringWithID> adapter = new ArrayAdapter<StringWithID> (
								getApplicationContext(), android.R.layout.simple_spinner_item, list);

						spn_categoryType.setAdapter(adapter);
					}
					catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
			
			private class GetCategories extends AsyncTask<Void, Void, String> {
				protected String doInBackground(Void... params) {
					StringBuilder builder = new StringBuilder();
					HttpClient client = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet("http://smartcity1.cloudapp.net/api/CityReportingReportCategory?reportcategorytype_id="+categoryTypeSelected);
					try {
						HttpResponse response = client.execute(httpGet);
						StatusLine statusLine = response.getStatusLine();
						int statusCode = statusLine.getStatusCode();
						if (statusCode == 200) {
							HttpEntity entity = response.getEntity();
							InputStream content = entity.getContent();
							BufferedReader reader = new BufferedReader(
									new InputStreamReader(content));
							String line;
							while ((line = reader.readLine()) != null) {
								builder.append(line);
							}
						} else {
							// Failed to download file
						}
					}
					catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return builder.toString();
				}
				
				protected void onPostExecute(String result) {
					System.out.println(result);
					JSONArray jsonArray;
					JSONObject jsonObject;
					
					try {
						jsonArray = new JSONArray(result);
						ArrayList<StringWithID> list = new ArrayList<StringWithID>();
						list.add(new StringWithID("Select Category", -1));
						for (int i = 0; i < jsonArray.length(); i++) {
						  jsonObject = jsonArray.getJSONObject(i);
						  list.add(new StringWithID(jsonObject.getString("CategoryName"), jsonObject.getInt("CategoryID")));
						}
						ArrayAdapter<StringWithID> adapter = new ArrayAdapter<StringWithID> (
								getApplicationContext(), android.R.layout.simple_spinner_item, list);
						
						spn_category.setAdapter(adapter);
					}
					catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
			
		
						
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hoboken311, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
    
	 private void buildAlertMessageNoGps() {
		    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
		           .setCancelable(false)
		           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		               public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
		                   startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		               }
		           })
		           .setNegativeButton("No", new DialogInterface.OnClickListener() {
		               public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
		                    dialog.cancel();
		               }
		           });
		    final AlertDialog alert = builder.create();
		    alert.show();
		}
	 
}