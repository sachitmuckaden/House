package com.MAS.helpers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

public class YouTubeAuthHelper{
	
	public List<NameValuePair> createMessage(Context context, String username, String password){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		//String data = "Email=" + username+ "&Passwd=" + password + "&service=youtube&source=" + Values.YOUTUBE_SOURCE;
		params.add(new BasicNameValuePair("header", Values.YOUTUBE_AUTH_HEADER));
		params.add(new BasicNameValuePair("Email", username));
		params.add(new BasicNameValuePair("Passwd",password));
		params.add(new BasicNameValuePair("service","youtube"));
		params.add(new BasicNameValuePair("source",Values.YOUTUBE_SOURCE));
		return params;
	}
}