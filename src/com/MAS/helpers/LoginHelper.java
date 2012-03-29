package com.MAS.helpers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.MAS.utils.DeviceUtil;

import android.content.Context;

public class LoginHelper{
	
	private static String username;
	private static String password;
	private static String userid;
	Context context;
	
	public List<NameValuePair> createMessage(Context context,String user, String pass)
	{
		this.context = context;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		DeviceUtil device = new DeviceUtil();
		userid = device.getDeviceId(this.context);
		
		username = user;
		password = pass;
		
		params.add(new BasicNameValuePair("userid", userid));
		params.add(new BasicNameValuePair("email", username));
		params.add(new BasicNameValuePair("password", password));
		
		return params;
	}
	
}