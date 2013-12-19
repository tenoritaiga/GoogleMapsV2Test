package com.smartcity.redux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Class for controlling the My Profile activity in the app.
 * @author Class2013
 *
 */
public class ProfileActivity extends Activity {

	public int userID = 0;
	
	/**
	 * Called when the My Profile activity is created - sets the layout and populates the 
	 * Age Range dropdown menu with values.
	 */
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		// Show the Up button in the action bar.
		setupActionBar();
		Spinner spinner = (Spinner) findViewById(R.id.age_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.age_ranges, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		new JsonParser().execute();
	}
	
	/**
	 * Performs an HTTP GET request to retrieve an InputStream containing JSON 
	 * data from the provided URL. If the request is successful, an InputStream 
	 * is returned.
	 * @param url
	 * @return
	 */
	private static InputStream retrieveStream(String url) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(url);
		
		try{
			HttpResponse getResponse = client.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();
			
			if(statusCode != HttpStatus.SC_OK){
				//Log.w(getClass().getSimpleName(),
						//"Error " + statusCode + " for URL " + url);
				System.out.println(statusCode);
				return null;
			}
			
			HttpEntity getResponseEntity = getResponse.getEntity();
			return getResponseEntity.getContent();
		}
		
		catch(IOException e){
			getRequest.abort();
			//Log.w(getClass().getSimpleName(),"Error for URL " + url, e);
		}
		return null;
	}
	
	/**
	 * Nested class for retrieving profile data through an API call, and populating 
	 * the appropriate fields of the profile with the data.
	 * @author Class2013
	 *
	 */
	private class JsonParser extends AsyncTask<Void,Void,JSONObject>{

		/**
		 * Makes the API call to retrieve an InputStream from the desired URL, then parses 
		 * that InputStream to retrieve a JSON object containing profile data.
		 */
		@Override
		protected JSONObject doInBackground(Void... params) {
			//String url = "http://pastebin.com/raw.php?i=1VnxAK78";
			//String url = "http://pastebin.com/raw.php?i=s4qzrKDF";
			String url = "http://50.17.51.160/api/Users?user_name=dschuler";
			//InputStream source = retrieveStream(url);
			InputStream stream = retrieveStream(url);
			Log.d("STREAM", (stream == null) + "");
			
			try{
				Reader reader = new InputStreamReader(stream);
				BufferedReader bufRead = new BufferedReader(reader);
				StringBuilder builder = new StringBuilder();
				String line;
				while ((line = bufRead.readLine()) != null) {
					builder.append(line);
					System.out.println(line);
				}
				String jsonString = builder.toString();
				JSONObject jsonObject = new JSONObject(jsonString);
				//System.out.println(new JSONObject(jsonObject.getString("HomeAddress")).getString("NumberAndStreet"));
				return jsonObject;
			}
			catch(NullPointerException npe){
				Log.e("ERROR","Warning: Null pointer exception while parsing JSON!",npe);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		
		/**
		 * Parses the retrieved JSON object and populates the fields of the 
		 * profile with the contained profile data.
		 */
		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(JSONObject jsonObject){
			
			try {
				userID = Integer.parseInt(jsonObject.getString("UserID"));
				EditText editText = (EditText) findViewById(R.id.edit_username);
				editText.setText(jsonObject.getString("Username"));
				editText = (EditText) findViewById(R.id.edit_password);
				editText.setText(jsonObject.getString("Password"));
				editText = (EditText) findViewById(R.id.edit_password_confirm);
				editText.setText(jsonObject.getString("Password"));
				editText = (EditText) findViewById(R.id.edit_firstname);
				editText.setText(jsonObject.getString("FirstName"));
				editText = (EditText) findViewById(R.id.edit_lastname);
				editText.setText(jsonObject.getString("LastName"));
				editText = (EditText) findViewById(R.id.edit_home_address);
				editText.setText(new JSONObject(jsonObject.getString("HomeAddress")).getString("NumberAndStreet"));
				editText = (EditText) findViewById(R.id.edit_home_zip);
				editText.setText(new JSONObject(jsonObject.getString("HomeAddress")).getString("ZipCode"));
				editText = (EditText) findViewById(R.id.edit_work_address);
				editText.setText(new JSONObject(jsonObject.getString("WorkAddress")).getString("NumberAndStreet"));
				editText = (EditText) findViewById(R.id.edit_work_zip);
				editText.setText(new JSONObject(jsonObject.getString("WorkAddress")).getString("ZipCode"));
				editText = (EditText) findViewById(R.id.edit_gender);
				editText.setText(jsonObject.getString("Gender"));
				Spinner spinner = (Spinner) findViewById(R.id.age_spinner);
				ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
				int pos = adapter.getPosition(jsonObject.getString("AgeRange"));
				spinner.setSelection(pos);
				CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox_swift911);
				if (jsonObject.getBoolean("SignupSwift") == true)
					checkBox.setChecked(true);
				else
					checkBox.setChecked(false);
				//System.out.println(profile.signup_swift);
				editText = (EditText) findViewById(R.id.edit_phone);
				editText.setText(jsonObject.getString("PhoneNumber"));
				editText = (EditText) findViewById(R.id.edit_email);
				editText.setText(jsonObject.getString("EmailAddress"));
				checkBox = (CheckBox) findViewById(R.id.checkbox_submit_gps);
				if (jsonObject.getBoolean("SubmitGPSLocation") == true)
					checkBox.setChecked(true);
				else
					checkBox.setChecked(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Nested class for sending an updated profile record through an API call - constructs a JSON 
	 * object with the required data and makes an HTTP PUT request to send the data to the server.
	 * @author Class2013
	 *
	 */
	private class JsonSender extends AsyncTask<Void,Void,Void> {
		
		
		@Override
		protected Void doInBackground(Void... params) {
			JSONObject json = new JSONObject();
			JSONObject jsonAddress = new JSONObject();

			try {
				json.put("UserID", userID);
				EditText editText = (EditText) findViewById(R.id.edit_username);
				json.put("Username", editText.getText().toString());
				editText = (EditText) findViewById(R.id.edit_password);
				json.put("Password", editText.getText().toString());
				editText = (EditText) findViewById(R.id.edit_firstname);
				json.put("FirstName", editText.getText().toString());
				editText = (EditText) findViewById(R.id.edit_lastname);
				json.put("LastName", editText.getText().toString());
				editText = (EditText) findViewById(R.id.edit_home_address);
				jsonAddress.put("NumberAndStreet", editText.getText().toString());
				editText = (EditText) findViewById(R.id.edit_home_zip);
				jsonAddress.put("ZipCode", editText.getText().toString());
				json.put("HomeAddress", jsonAddress);
				jsonAddress = new JSONObject();
				editText = (EditText) findViewById(R.id.edit_work_address);
				jsonAddress.put("NumberAndStreet", editText.getText().toString());
				editText = (EditText) findViewById(R.id.edit_work_zip);
				jsonAddress.put("ZipCode", editText.getText().toString());
				json.put("WorkAddress", jsonAddress);
				editText = (EditText) findViewById(R.id.edit_gender);
				json.put("Gender", editText.getText().toString());
				Spinner spinner = (Spinner) findViewById(R.id.age_spinner);
				json.put("AgeRange", spinner.getSelectedItem().toString());
				CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox_swift911);
				if (checkBox.isChecked() == true)
					json.put("SignupSwift", true);
				else
					json.put("SignupSwift", false);
				//System.out.println(profile.signup_swift);
				editText = (EditText) findViewById(R.id.edit_phone);
				json.put("PhoneNumber", editText.getText().toString());
				editText = (EditText) findViewById(R.id.edit_email);
				json.put("EmailAddress", editText.getText().toString());
				checkBox = (CheckBox) findViewById(R.id.checkbox_submit_gps);
				if (checkBox.isChecked() == true)
					json.put("SubmitGPSLocation", true);
				else
					json.put("SubmitGPSLocation", false);
				
				DefaultHttpClient client = new DefaultHttpClient();
				HttpPut putRequest = new HttpPut("http://50.17.51.160/api/Users");
				System.out.println(json.toString());
				StringEntity se = new StringEntity(json.toString());
				se.setContentType("application/json");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
				putRequest.setEntity(se);
				HttpResponse response = client.execute(putRequest);
				System.out.println(response.getStatusLine().getStatusCode());
				return null;
			}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Called when any of the save buttons in the profile are clicked. If the values of the 
	 * Password and Confirm Password fields do not match, an alert dialog will pop up prompting 
	 * the user to correct the problem. Otherwise the nested JsonSender class will be invoked 
	 * to send the updated profile data to the server.
	 * @param view
	 */
	public void saveProfileData(View view) {
		EditText password1 = (EditText) findViewById(R.id.edit_password);
		EditText password2 = (EditText) findViewById(R.id.edit_password_confirm);
		if (!(password1.getText().toString().equals(password2.getText().toString()))) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.profile_password_mismatch_title)
					.setMessage(R.string.profile_password_mismatch_message)
					.setPositiveButton(R.string.profile_button_OK, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
			builder.show();
		}
		else {
			new JsonSender().execute();
		}
	}
}
