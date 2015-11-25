package com.qualitativehealthsystems.helpers;

import android.net.Credentials;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JSONfunctions {

	public static int postPoll(String url, String patientID, String moodID) {
		boolean result = false;
		int statusCode = 0;
		HttpClient httpClient = new DefaultHttpClient();
		String message;

		HttpPost p = new HttpPost(url);
		JSONObject object = new JSONObject();
		try {
			object.put("PatientId", patientID);
			object.put("MoodId", moodID);
		} catch (Exception ex) {

		}

		try {
			message = object.toString();

			p.setEntity(new StringEntity(message, "UTF8"));
			p.setHeader("Content-type", "application/json");
			HttpResponse resp = httpClient.execute(p);
			if (resp != null) {
//				if (resp.getStatusLine().getStatusCode() == 200)
//					result = true;
				statusCode = resp.getStatusLine().getStatusCode();
			}

			Log.d("Status line", "" + resp.getStatusLine().getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();

		}

		return statusCode;
	}

	public static JSONObject getJSONfromURL(String url) {
		InputStream is = null;
		String result = "";
		JSONObject jArray = null;

		// Download JSON data from URL
		try {
			HttpClient httpclient = new DefaultHttpClient();
			//HttpPost httppost = new HttpPost(url);
			HttpGet httppost = new HttpGet(url);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

		// Convert response to string
		try {
//			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}

		try {

			jArray = new JSONObject(result);
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}

		return jArray;
	}

	// Perform a POST to the login url with the parameters UserName and Password
	// Capture the response
	// Convert Response to JSON object
	// Return the value of field "Authenticated" as boolean
	public static boolean login(String userName, String password) {

		String url = "http://23.229.20.102/api/User/Login";
		InputStream is = null;
		JSONObject jArray = null;
		boolean result = false;
		int statusCode = 0;
		HttpClient httpClient = new DefaultHttpClient();
		String message;

		HttpPost p = new HttpPost(url);
		JSONObject object = new JSONObject();
		try {
			object.put("UserName", userName);
			object.put("Password", password);
		} catch (Exception ex) {

		}

		try {
			message = object.toString();

			p.setEntity(new StringEntity(message, "UTF8"));
			p.setHeader("Content-type", "application/json");
			HttpResponse resp = httpClient.execute(p);
			HttpEntity entity = resp.getEntity();
			is = entity.getContent();
			if (resp != null) {
//				if (resp.getStatusLine().getStatusCode() == 200)
//					result = true;
				statusCode = resp.getStatusLine().getStatusCode();
			}

			Log.d("Status line", "" + resp.getStatusLine().getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();

		}

		String response = "";

//		// Download JSON data from URL
//		try {
//			HttpClient httpclient = new DefaultHttpClient();
//			//HttpPost httppost = new HttpPost(url);
//			HttpGet httppost = new HttpGet(url);
//			HttpResponse responseHttp = httpclient.execute(httppost);
//			HttpEntity entity = responseHttp.getEntity();
//			is = entity.getContent();
//		} catch (Exception e) {
//			Log.e("log_tag", "Error in http connection " + e.toString());
//		}

		// Convert response to string
		try {
//			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			response = sb.toString();
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}

		try {

			jArray = new JSONObject(response);
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}

		try {
			result = (jArray.getBoolean("IsAuthenticated") == true);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result;

	}


	// Perform a POST to the login url with the parameters UserName and Password
	// Capture the response
	// Convert Response to JSON object
	// Return the value of field "Authenticated" as boolean
	public static JSONObject login(String userName, String password, boolean verbose) {

		String url = "http://23.229.20.102/api/User/Login";
		InputStream is = null;
		JSONObject jsonObject = null;
		boolean result = false;
		boolean isLockedOut = false;
		int statusCode = 0;
		HttpClient httpClient = new DefaultHttpClient();
		String message;

		HttpPost p = new HttpPost(url);
		JSONObject object = new JSONObject();
		try {
			object.put("UserName", userName);
			object.put("Password", password);
		} catch (Exception ex) {

		}

		try {
			message = object.toString();

			p.setEntity(new StringEntity(message, "UTF8"));
			p.setHeader("Content-type", "application/json");
			HttpResponse resp = httpClient.execute(p);
			HttpEntity entity = resp.getEntity();
			is = entity.getContent();
			if (resp != null) {
				statusCode = resp.getStatusLine().getStatusCode();
			}

			Log.d("Status line", "" + resp.getStatusLine().getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();

		}

		String response = "";

		// Convert response to string
		try {
//			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			response = sb.toString();
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}

		try {
			jsonObject = new JSONObject(response);
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}

		try {
			result = (jsonObject.getBoolean("IsAuthenticated") == true);
			isLockedOut = (jsonObject.getBoolean("IsLockedOut") == true);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject;

	}
}
