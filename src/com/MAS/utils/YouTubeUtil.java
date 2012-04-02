package com.MAS.utils;

import com.MAS.helpers.Values;

import android.content.Intent;

public class YouTubeUtil{
	public String VIDEO_FILE_NAME= "";
	public void uploadVideo(Intent intent)
	{
		
	
	}
	
	public String createUploadRequest(String AUTH_TOKEN)
	{
		
		/*
		 * POST /resumable/feeds/api/users/default/uploads HTTP/1.1
		Host: uploads.gdata.youtube.com
		Authorization: AuthSub token="DXAA...sdb8"
		GData-Version: 2
		X-GData-Key: key=adf15ee97731bca89da876c...a8dc
		Content-Length: 0
		Slug: my_file.mp4
		 */
		
		String request = "POST /resumable/feeds/api/users/default/uploads HTTP/1.1" + "\n";
		request = request + "Host: uploads.gdata.youtube.com" + "\n";
		request = request + "Authorization: GoogleLogin auth=" + AUTH_TOKEN+ "\n";
		request = request + "GData-Version: 2" + "\n";
		request = request + "X_GData-Key: key=" + Values.YOUTUBE_DEVELOPER_CODE + "\n";
		request = request + "Content-Length: 0" + "\n";
		request = request + "Slug: " + VIDEO_FILE_NAME;
		return request;
	}
	
	
		
	
}