package com.MAS.helpers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.MAS.utils.*;


import android.content.Context;
import android.location.Location;

public class MessageHelper{
	
	private String message_text;
	private String deviceId;
	private Location myLocation;
	private Timestamp timestamp;
	Context context;
	
	
	public List<NameValuePair> createMessage(Context context, String message, String tag)
	{
		this.context = context;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		this.message_text = message;
		
		params.add(new BasicNameValuePair("message", this.message_text));
		
		GPSUtil gps = new GPSUtil();
		this.myLocation = gps.getGPSLocation(this.context);
		double dbl = myLocation.getLatitude();
		int x = (int) (dbl*100.0);
		Double latitude = (double) x/100;
		String lat = latitude.toString();
		params.add(new BasicNameValuePair("latitude",lat));
		dbl = myLocation.getLongitude();
		x = (int) (dbl*100.0);
		Double longitude = (double) x/100;
		String longi = longitude.toString();
		params.add(new BasicNameValuePair("longitude",longi));
		
		
		DeviceUtil device = new DeviceUtil();
		this.deviceId = device.getDeviceId(this.context);
		params.add(new BasicNameValuePair("userid", this.deviceId));
		
		
		TimestampUtil timestamp = new TimestampUtil();
		this.timestamp = timestamp.getTimeStamp();
		params.add(new BasicNameValuePair("timestamp", this.timestamp.toString()));
		
		params.add(new BasicNameValuePair("tag", tag));
		return params;
	}
	
}