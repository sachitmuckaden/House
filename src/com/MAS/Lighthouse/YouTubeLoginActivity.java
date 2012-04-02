package com.MAS.Lighthouse;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import com.MAS.helpers.Values;
import com.MAS.helpers.YouTubeAuthHelper;
import com.MAS.helpers.YouTubeHelper;
import com.MAS.utils.HTTPUtil;
import com.MAS.utils.HTTPPutUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class YouTubeLoginActivity extends Activity{
	File videofile = null;
	String videofilename = null;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtubeloginlayout);
        Bundle extras = getIntent().getExtras(); 
        if(extras !=null)
        {
        	videofile = (File)extras.get("videofile");
        	videofilename = (String) extras.getString("filename");
        }

        
	}
	
	public void getAuthToken(View view)
	{
		String AuthToken = "";
		EditText usernameedt = (EditText)findViewById(R.id.youtubeusername);
    	String username = usernameedt.getText().toString();
		
    	EditText passwordedt = (EditText)findViewById(R.id.youtubepassword);
    	String password = passwordedt.getText().toString();
    	
    	if(username==null||password==null)
    	{
    		showDialog(0);
    		
    	}
    	
    	YouTubeAuthHelper youtubeauth = new YouTubeAuthHelper();
    	List<NameValuePair> params = youtubeauth.createMessage(this.getApplicationContext(), username, password);
		HTTPUtil http = new HTTPUtil();
		
		JSONObject response;
		try {
			response = http.Execute(params, Values.YOUTUBE_AUTH);
		} catch (ClientProtocolException e) {
			Log.e("YOUTUBE AUTH","No response");
			Log.e("Exception Code", "1");
			e.printStackTrace();
			response = null;
		} catch (IOException e) {
			Log.e("YOUTUBE AUTH","No response");
			Log.e("Exception Code", "2");
			// TODO Auto-generated catch block
			e.printStackTrace();
			response = null;
		}
		
		if(response!=null)
		{
			try {
				AuthToken = response.getString("AUTH");
				Log.d("Auth Token", AuthToken);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e("Error", "JSONException");
				e.printStackTrace();
			}
		}
		else
		{
			AuthToken = "";
		}
		String messageText = sendYouTubeRequest(AuthToken);
		Intent j  = new Intent();
		j.putExtra(Values.COMING_FROM, Values.COMING_FROM_YOUTUBE);
		j.putExtra("message", messageText);
		startActivity(j);
		finish();
		
	}
	
	protected Dialog onCreateDialog(int id) 
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Invalid Entry");
		builder.setMessage("Both fields are required");
		builder.setIcon(R.drawable.youtubeimage);
		builder.setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) 
			{
			return;
				
			}
		});
		return builder.create();
	}
	private String sendYouTubeRequest(String AuthToken)
	{
		YouTubeHelper youtube = new YouTubeHelper();
		HttpParams params = youtube.createMessage(this.getApplicationContext(), AuthToken, videofilename);
		HTTPUtil http = new HTTPUtil();
		JSONObject response = null;
		try {
			response = http.Execute(params, Values.YOUTUBE_URL);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String uploadurl = "";
		try {
			uploadurl = response.getString("Location");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String messageText="";
		messageText = sendVideo(uploadurl);
		return messageText;
	}
	
	public String sendVideo(String uploadurl)
	{
		
		Long FILE_LENGTH = videofile.length();
		String VIDEO_FILE_LENGTH = FILE_LENGTH.toString();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		/*Host: uploads.gdata.youtube.com
		Content-Type: <video_content_type>
		Content-Length: <content_type>

		<Binary_file_data>*/
		
		params.add(new BasicNameValuePair("Host", Values.YOUTUBE_UPLOAD_HOST));
		params.add(new BasicNameValuePair("Content-Type", Values.YOUTUBE_CONTENT_TYPE));
		params.add(new BasicNameValuePair("Content-Length",VIDEO_FILE_LENGTH));
		params.add(new BasicNameValuePair("",videofile.toString()));
		
		HTTPPutUtil http = new HTTPPutUtil();
		try {
			http.Execute(params, uploadurl);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String messageText = getActualUrl(uploadurl);
		return messageText;
		
	}
	
	public String getActualUrl(String uploadurl)
	{
		String actualurl = "http://www.youtube.com/watch?v=";
		String code = uploadurl.substring(uploadurl.lastIndexOf('/')+1);
		actualurl = actualurl + code;
		return actualurl;
	}
	
}