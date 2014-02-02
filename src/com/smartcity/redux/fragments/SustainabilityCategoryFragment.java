package com.smartcity.redux.fragments;

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

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.smartcity.redux.R;

public class SustainabilityCategoryFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		new JsonGasAvgParser().execute();
		new JsonEnergyAvgParser().execute();
		new JsonWaterAvgParser().execute();
		return inflater.inflate(R.layout.fragment_sustainability_category, null);
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
	
	/**
	 * Nested class for retrieving average gas consumption data through an API call, and populating 
	 * the appropriate fields of the "View Consumption Data" tab.
	 * @author Class2013
	 *
	 */
	private class JsonGasAvgParser extends AsyncTask<Void,Void,JSONObject> {
		
		/**
		 * Makes the API call to retrieve an InputStream from the desired URL, then parses 
		 * that InputStream to retrieve a JSON object containing average gas consumption data.
		 */
		@Override
		protected JSONObject doInBackground(Void... params) {
			//String url = "http://pastebin.com/raw.php?i=QiXs9eZU";
			String url = "http://schoboken.cloudapp.net:82/api/UserGasConsumptionAverages?user_id=1";
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
		 * "View Consumption Data" tab with the contained data on average gas 
		 * consumption.
		 */
		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			
			try {
				EditText editText = (EditText) getActivity().findViewById(R.id.sust_cons_avg);
				editText.setText(jsonObject.getString("UserAverage30Day"));
				editText = (EditText) getActivity().findViewById(R.id.sust_cons_avg_all);
				editText.setText(jsonObject.getString("CityAverage30Day"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Nested class for retrieving average energy usage data through an API call, and populating 
	 * the appropriate fields of the "View Usage Data" tab.
	 * @author Class2013
	 *
	 */
	private class JsonEnergyAvgParser extends AsyncTask<Void,Void,JSONObject> {
		
		/**
		 * Makes the API call to retrieve an InputStream from the desired URL, then parses 
		 * that InputStream to retrieve a JSON object containing average energy usage data.
		 */
		@Override
		protected JSONObject doInBackground(Void... params) {
			//String url = "http://pastebin.com/raw.php?i=hizxN4XD";
			String url = "http://schoboken.cloudapp.net:82/api/UserEnergyConsumptionAverages?user_id=1";
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
		 * "View Usage Data" tab with the contained data on average energy usage.
		 */
		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			
			try {
				EditText editText = (EditText) getActivity().findViewById(R.id.sust_gas_cons_avg);
				editText.setText(jsonObject.getString("UserAverageGas30Day"));
				editText = (EditText) getActivity().findViewById(R.id.sust_gas_cons_avg_all);
				editText.setText(jsonObject.getString("CityAverageGas30Day"));
				editText = (EditText) getActivity().findViewById(R.id.sust_elect_cons_avg);
				editText.setText(jsonObject.getString("UserAverageElectricity30Day"));
				editText = (EditText) getActivity().findViewById(R.id.sust_elect_cons_avg_all);
				editText.setText(jsonObject.getString("CityAverageElectricity30Day"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Nested class for retrieving average water usage data through an API call, and populating 
	 * the appropriate fields of the "View Usage Data" tab.
	 * @author Class2013
	 *
	 */
	private class JsonWaterAvgParser extends AsyncTask<Void,Void,JSONObject> {
		
		/**
		 * Makes the API call to retrieve an InputStream from the desired URL, then parses 
		 * that InputStream to retrieve a JSON object containing average water usage data.
		 */
		@Override
		protected JSONObject doInBackground(Void... params) {
			//String url = "http://pastebin.com/raw.php?i=suvU0Tbb";
			String url = "http://schoboken.cloudapp.net:82/api/UserWaterConsumptionAverages?user_id=1";
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
		 * "View Usage Data" tab with the contained data on average water usage.
		 */
		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			
			try {
				EditText editText = (EditText) getActivity().findViewById(R.id.sust_water_cons_avg);
				editText.setText(jsonObject.getString("UserAverage30Day"));
				editText = (EditText) getActivity().findViewById(R.id.sust_water_cons_avg_all);
				editText.setText(jsonObject.getString("CityAverage30Day"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
