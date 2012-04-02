package com.MAS.helpers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

public class YouTubeHelper{
	
public List<NameValuePair> createMessage(Context context, String AUTH_TOKEN, String VIDEO_FILE_NAME){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		
		params.add(new BasicNameValuePair("Host","uploads.gdata.youtube.com"));
		params.add(new BasicNameValuePair("Authorization", "GoogleLogin auth=\"" +AUTH_TOKEN+"\""));;
		params.add(new BasicNameValuePair("GData-Version", "2"));
		params.add(new BasicNameValuePair("X-GData-Key", "key=" + Values.YOUTUBE_DEVELOPER_CODE));
		params.add(new BasicNameValuePair("Content-Length", "0"));
		params.add(new BasicNameValuePair("Slug",VIDEO_FILE_NAME));
		return params;
	}
}