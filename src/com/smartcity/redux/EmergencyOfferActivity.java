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
import org.json.JSONArray;
import org.json.JSONException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.text.InputType;

public class EmergencyOfferActivity extends Activity {
	
	Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emergency_offer);
		// Show the Up button in the action bar.
		setupActionBar();
		
		activity = this;
		
		new JsonEmerLayoutParser().execute();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.emergency_offer, menu);
		return true;
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
			System.out.println(statusCode);
			
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
	
	private class JsonEmerLayoutParser extends AsyncTask<Void,Void,JSONArray> {
		
		@Override
		protected JSONArray doInBackground(Void... params) {
			//String url = "http://pastebin.com/raw.php?i=up92S6EE";
			String url = "http://smartcity1.cloudapp.net/api/CommunityResourceSharing?necessity_group=0";
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
				JSONArray jsonArray = new JSONArray(jsonString);
				//System.out.println(new JSONObject(jsonObject.getString("HomeAddress")).getString("NumberAndStreet"));
				return jsonArray;
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
		
		@Override
		protected void onPostExecute(JSONArray jsonArray) {
			
			try {
				System.out.println(jsonArray.length());
				System.out.println(jsonArray.getJSONObject(0).getString("NecessityID"));
				
				TableLayout tableLayout;
				TableRow.LayoutParams params;
				TableRow.LayoutParams params2;
				TableRow tableRow;
				TextView text;
				RadioGroup group;
				RadioButton yesButton;
				RadioButton noButton;
				EditText editText;
				
				for (int i=0; i<jsonArray.length(); i++) {
					tableLayout = (TableLayout) findViewById(R.id.emerReportTable);
					tableRow = new TableRow(activity);
					tableRow.setPadding(0, 10, 0, 10);
					tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
					
					params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
					params.setMargins(0, 5, 0, 0);
					params2 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
					params2.setMargins(15, 5, 0, 0);
					
					text = new TextView(activity);
					text.setText(jsonArray.getJSONObject(i).getString("NecessityName"));
					text.setPadding(5, 0, 15, 0);
					text.setTypeface(Typeface.DEFAULT_BOLD);
					text.setTag(Integer.parseInt(jsonArray.getJSONObject(i).getString("NecessityID")));
					text.setLayoutParams(params);
					
					group = new RadioGroup(activity);
					group.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
					group.setOrientation(LinearLayout.HORIZONTAL);
					
					yesButton = new RadioButton(activity);
					yesButton.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
					yesButton.setText("Yes");
					
					noButton = new RadioButton(activity);
					noButton.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
					noButton.setText("No");
					
					group.addView(yesButton);
					group.addView(noButton);
					group.check(noButton.getId());
					
					editText = new EditText(activity);
					editText.setLayoutParams(params2);
					editText.setPadding(20, 0, 0, 0);
					editText.setRawInputType(InputType.TYPE_CLASS_NUMBER);
					
					tableRow.addView(text);
					tableRow.addView(group);
					tableRow.addView(editText);
					tableLayout.addView(tableRow);
					
					System.out.println(text.getTag());
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
