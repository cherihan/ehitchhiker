/**
 * WebClient.java, Created 25 janv. 2011 at 10:53:09
 *
 * Project : ehitchhikerAndroid
 * Package : com.kinder.utils
 * 
 * Author : Christophe Dupont
 * Mail : mail@christophedupont.com
 * 
 */
package com.kinder.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;



public class WebClient {
	//added for monitoring input/output
	//values are overriden by GlobalApp
	public static boolean debug = false;
	public static String  debugLabel = "RestClient";

	private static String convertStreamToString(InputStream is) throws IOException {

		/*
		 * To convert the InputStream to String we use the BufferedReader.readLine()
		 * method. We iterate until the BufferedReader return null which means
		 * there's no more data to read. Each line will appended to a StringBuilder
		 * and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String input = sb.toString();

		if(debug) {
			Log.v(debugLabel, "<="+input);
		}

		return input;
	}

	public static JSONObject getJSONFromUrl(String url) throws IOException, JSONException
	{
		JSONObject json = null;
		HttpClient httpclient = new DefaultHttpClient();

		// Prepare a request object
		HttpGet httpget = new HttpGet(url); 

		if(debug) {
			Log.v(debugLabel, "=>"+url);
		}

		// Execute the request
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			// Examine the response status
			if(debug) {
				Log.v(debugLabel,"<="+response.getStatusLine().toString());
			}

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need
			// to worry about connection release
			

			if (entity != null) {

				// A Simple JSON Response Read
				InputStream instream = entity.getContent();
				String result= convertStreamToString(instream);
				instream.close();


				json = new JSONObject(result);
			}
		} catch (ClientProtocolException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (JSONException e) {
			throw e;
		}
		return json;
	}


	public static JSONArray getJSONArrayFromUrl(String url) throws IOException, JSONException
	{
		JSONArray tabJson = null;
		HttpClient httpclient = new DefaultHttpClient();

		// Prepare a request object
		HttpGet httpget = new HttpGet(url); 

		if(debug) {
			Log.v(debugLabel, "=>:"+url);
		}

		// Execute the request
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			// Examine the response status
			if(debug) {
				Log.v(debugLabel,"<="+response.getStatusLine().toString());
			}

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need
			// to worry about connection release

			if (entity != null) {

				// A Simple JSON Response Read
				InputStream instream = entity.getContent();
				String result= convertStreamToString(instream);
				instream.close();

				tabJson = new JSONArray(result);
			}
		} catch (ClientProtocolException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (JSONException e) {
			throw e;
		}
		return tabJson;
	}

	
	public static void postJSON(JSONObject json, String url) throws IOException, JSONException{
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);

		
		
		StringEntity se = new StringEntity(json.toString());
		httpost.setEntity(se);
		httpost.setHeader("Accept", "application/json");
		httpost.setHeader("Content-type", "application/json");
		
		
		ResponseHandler responseHandler = new BasicResponseHandler();
		
		try {
		httpclient.execute(httpost, responseHandler);
		}
		catch (Exception e) {
			e.printStackTrace();
			Log.e("WebClient", "Pb httpclient.execute...");
		}
		Log.v(debugLabel, "=>\n"+json.toString(4));
	}
	
	
	public static String getStringFromPostJSONToUrl(JSONObject json, String url) throws IOException, JSONException{
		String result = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);

		
		
		StringEntity se = new StringEntity(json.toString());
		httpost.setEntity(se);
		httpost.setHeader("Accept", "application/json");
		httpost.setHeader("Content-type", "application/json");
		
		
		HttpResponse response;
		try {
			response = httpclient.execute(httpost);
			
			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need
			// to worry about connection release

			if (entity != null) {

				InputStream instream = entity.getContent();
				result= convertStreamToString(instream);

				instream.close();

			}
		}
		catch (Exception e) {
			e.printStackTrace();
			Log.e("WebClient", "Pb httpclient.execute...");
		}
		Log.v(debugLabel, "=>\n"+json.toString(4));
		
		
		return result;
	}
	
	
	
	public static JSONObject getJSONObjectFromPostJSONToUrl(JSONObject json, String url) throws IOException, JSONException{
		String result = null;
		JSONObject obj = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);

		
		
		StringEntity se = new StringEntity(json.toString());
		httpost.setEntity(se);
		httpost.setHeader("Accept", "application/json");
		httpost.setHeader("Content-type", "application/json");
		
		
		HttpResponse response;
		try {
			response = httpclient.execute(httpost);
			
			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need
			// to worry about connection release

			if (entity != null) {

				InputStream instream = entity.getContent();
				result= convertStreamToString(instream);

				instream.close();
				
				obj  =  new JSONObject(result);

			}
		}
		catch (Exception e) {
			e.printStackTrace();
			Log.e("WebClient", "Pb httpclient.execute...");
		}
		Log.v(debugLabel, "=>\n"+json.toString(4));
		
		
		return obj;
	}



	public static String getStringFromUrl(String url) throws IOException
	{
		String result = null;
		HttpClient httpclient = new DefaultHttpClient();

		// Prepare a request object
		HttpGet httpget = new HttpGet(url); 

		if(debug) {
			Log.v(debugLabel, "=>"+url);
		}

		// Execute the request
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			// Examine the response status
			if(debug) {
				Log.v(debugLabel,"<="+response.getStatusLine().toString());
			}


			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need
			// to worry about connection release

			if (entity != null) {

				InputStream instream = entity.getContent();
				result= convertStreamToString(instream);

				instream.close();

			}

		} catch (ClientProtocolException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}

		return result;
	}

}

