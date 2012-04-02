package com.MAS.helpers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import android.content.Context;

public class YouTubeHelper{
	
@SuppressWarnings("null")
public  HttpParams createMessage(Context context, String AUTH_TOKEN, String VIDEO_FILE_NAME){
		
		//List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		HttpParams params = new BasicHttpParams();
		
		//StringBuilder request = new StringBuilder();
		
		params.setParameter("Host","uploads.gdata.youtube.com");
		params.setParameter("Authorization", new BasicNameValuePair("GoogleLogin auth",AUTH_TOKEN));
		params.setParameter("GData-Version", "2");
		params.setParameter("X-GData-Key", new BasicNameValuePair("key", Values.YOUTUBE_DEVELOPER_CODE));
		params.setParameter("Content-Length", 0);
		params.setParameter("Slug", VIDEO_FILE_NAME);
		
		
		return params;
	}
}