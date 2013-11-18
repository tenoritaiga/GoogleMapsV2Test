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
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class ProfileActivity extends Activity {

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
		
		/*String url = "http://pastebin.com/raw.php?i=s4qzrKDF";
		InputStream stream = retrieveStream(url);
		
		try {
			Gson gson = new Gson();
			Reader reader = new InputStreamReader(stream);
			ProfileSearchResponse response = gson.fromJson(reader, ProfileSearchResponse.class);
			Profile profile = response.profiles.get(0);
		}
		catch(NullPointerException npe) {
			Log.e("ERROR","Warning: Null pointer exception while parsing JSON!",npe);
		}*/
		new JsonParser().execute();
	}
	
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
	
	private class JsonParser extends AsyncTask<Void,Void,JSONObject>{

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
				while ((line = bufRead.readLine()) != null)
					builder.append(line);
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
		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(JSONObject jsonObject){
			
			try {
				EditText editText = (EditText) findViewById(R.id.edit_username);
				editText.setText(jsonObject.getString("Username"));
				editText = (EditText) findViewById(R.id.edit_password);
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

}
