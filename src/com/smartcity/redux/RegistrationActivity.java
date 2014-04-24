package com.smartcity.redux;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class RegistrationActivity extends Activity {

	public int userID = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		Spinner spinner = (Spinner) findViewById(R.id.age_rg_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.age_ranges, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
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
				EditText editText = (EditText) findViewById(R.id.edit_rg_username);
				json.put("Username", editText.getText().toString());
				editText = (EditText) findViewById(R.id.edit_rg_password);
				json.put("Password", editText.getText().toString());
				editText = (EditText) findViewById(R.id.edit_rg_firstname);
				json.put("FirstName", editText.getText().toString());
				editText = (EditText) findViewById(R.id.edit_rg_lastname);
				json.put("LastName", editText.getText().toString());
				editText = (EditText) findViewById(R.id.edit_rg_home_address);
				jsonAddress.put("NumberAndStreet", editText.getText().toString());
				editText = (EditText) findViewById(R.id.edit_rg_home_zip);
				jsonAddress.put("ZipCode", editText.getText().toString());
				json.put("HomeAddress", jsonAddress);
				jsonAddress = new JSONObject();
				editText = (EditText) findViewById(R.id.edit_rg_work_address);
				jsonAddress.put("NumberAndStreet", editText.getText().toString());
				editText = (EditText) findViewById(R.id.edit_rg_work_zip);
				jsonAddress.put("ZipCode", editText.getText().toString());
				json.put("WorkAddress", jsonAddress);
				editText = (EditText) findViewById(R.id.edit_rg_gender);
				json.put("Gender", editText.getText().toString());
				Spinner spinner = (Spinner) findViewById(R.id.age_rg_spinner);
				json.put("AgeRange", spinner.getSelectedItem().toString());
				CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox_rg_swift911);
				if (checkBox.isChecked() == true)
					json.put("SignupSwift", true);
				else
					json.put("SignupSwift", false);
				//System.out.println(profile.signup_swift);
				editText = (EditText) findViewById(R.id.edit_rg_phone);
				json.put("PhoneNumber", editText.getText().toString());
				editText = (EditText) findViewById(R.id.edit_rg_email);
				json.put("EmailAddress", editText.getText().toString());
				checkBox = (CheckBox) findViewById(R.id.checkbox_rg_submit_gps);
				if (checkBox.isChecked() == true)
					json.put("SubmitGPSLocation", true);
				else
					json.put("SubmitGPSLocation", false);
				
				DefaultHttpClient client = new DefaultHttpClient();
				//HttpPut putRequest = new HttpPut("http://pastebin.com/raw.php?i=s4qzrKDF");
				HttpPost postRequest = new HttpPost("http://smartcity1.cloudapp.net/api/Users");
				System.out.println(json.toString());
				StringEntity se = new StringEntity(json.toString());
				se.setContentType("application/json");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
				postRequest.setEntity(se);
				HttpResponse response = client.execute(postRequest);
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
	 * Called when any of the save buttons in the profile are clicked. If the values of the 
	 * Password and Confirm Password fields do not match, an alert dialog will pop up prompting 
	 * the user to correct the problem. Otherwise the nested JsonSender class will be invoked 
	 * to send the updated profile data to the server.
	 * @param view
	 */
	public void saveProfileData(View view) {
		EditText password1 = (EditText) findViewById(R.id.edit_rg_password);
		EditText password2 = (EditText) findViewById(R.id.edit_rg_password_confirm);
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
