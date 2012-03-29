package com.MAS.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.MAS.helpers.Values;

import android.util.Log;

public class HTTPUtil{
	
	static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    
	public JSONObject Execute(List<NameValuePair> params, String method) throws ClientProtocolException, IOException
	{
		
			String url = "http://m.cip.gatech.edu/developer/buzzevents/widget/Lighthouse/api/";
			
			if(method.equals(Values.LOGIN_URL))
			{
				url = url + Values.LOGIN_URL;
			}
			else if(method.equals(Values.SIGNUP_URL))
			{
				url = url + Values.SIGNUP_URL;
			}
			else if(method.equals(Values.POSTMESSAGE_URL))
			{
				url = url + Values.POSTMESSAGE_URL;
			}
			else if(method.equals(Values.GETMESSAGES_URL))
			{
				url = url + Values.GETMESSAGES_URL;
			}
			else
			{
				url = url + "write";
			}
		    try {
	            // defaultHttpClient
	            DefaultHttpClient httpClient = new DefaultHttpClient();
	            HttpPost httpPost = new HttpPost(url);
	            httpPost.setEntity((HttpEntity) new UrlEncodedFormEntity(params));
	 
	            HttpResponse httpResponse = httpClient.execute(httpPost);
	            HttpEntity httpEntity = httpResponse.getEntity();
	            is = httpEntity.getContent();
	            	 
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		    try {
	            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	            is.close();
	            json = sb.toString();
	            Log.e("JSON", json);
	        } catch (Exception e) {
	            Log.e("Buffer Error", "Error converting result " + e.toString());
	        }
	 
	        // try parse the string to a JSON object
	        try {
	            jObj = new JSONObject(json);
	        } catch (JSONException e) {
	            Log.e("JSON Parser", "Error parsing data " + e.toString());
	        }
	 
	        // return JSON String
	        return jObj;
	 
	   }
}