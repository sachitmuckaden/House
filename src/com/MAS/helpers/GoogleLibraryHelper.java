package com.MAS.helpers;



import java.io.IOException;


import android.util.Log;

import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;

public class GoogleLibraryHelper{
	
	public String getUploadUrl(String authToken, String videofilename)  throws IOException {
		Log.d("Entered the GoogleLib Class","Check");
	HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
	
	//InputStreamContent content = new InputStreamContent(null, null);
	//content.inputStream = null;
	
	  	HttpRequest request = requestFactory.buildPostRequest(new GenericUrl(Values.YOUTUBE_URL), null);
		
		
		GoogleHeaders headers = new GoogleHeaders();
		//headers.setGoogleLogin(authToken);
		headers.setContentLength("0");
		headers.setGDataKey("key = " +Values.YOUTUBE_DEVELOPER_CODE);
		headers.setGDataVersion("2");
		headers.setAuthorization("GoogleLogin auth=" + authToken);
		headers.setSlugFromFileName(videofilename);
		HttpResponse initialResponse = null;
		request.setHeaders(headers);
		try {
			initialResponse = request.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String uploadurl = initialResponse.getHeaders().getLocation();
		Log.d("Upload url", uploadurl);
		
		return uploadurl;
		
		
		
	
	//request.headers = headers;
	}
}