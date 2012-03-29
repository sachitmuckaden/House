package com.MAS.helpers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.MAS.utils.GPSUtil;

import android.content.Context;
import android.location.Location;

public class ReceiveHelper{
	
	Context context;
	
	public List<NameValuePair> createMessage(Context context)
	{
		this.context = context;
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		GPSUtil gps = new GPSUtil();
		Location myLocation= gps.getGPSLocation(this.context);
		
		double dbl = myLocation.getLatitude();
		int x = (int) (dbl*100.0);
		Double latitude = (double) x/100;
		String lat = latitude.toString();
		
		dbl = myLocation.getLongitude();
		x = (int) (dbl*100.0);
		Double longitude = (double) x/100;
		String longi = longitude.toString();
		
		params.add(new BasicNameValuePair("latitude",lat));
		params.add(new BasicNameValuePair("longitude",longi));
		
		return params;
	}
}