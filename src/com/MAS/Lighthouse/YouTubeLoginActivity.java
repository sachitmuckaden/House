package com.MAS.Lighthouse;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
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

import com.MAS.helpers.GoogleLibraryHelper;
import com.MAS.helpers.Values;
import com.MAS.helpers.YouTubeAuthHelper;
import com.MAS.helpers.YouTubeHelper;
import com.MAS.utils.HTTPUtil;
import com.MAS.utils.HTTPPutUtil;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.media.MediaFileSource;
import com.google.gdata.data.media.mediarss.MediaCategory;
import com.google.gdata.data.media.mediarss.MediaDescription;
import com.google.gdata.data.media.mediarss.MediaKeywords;
import com.google.gdata.data.media.mediarss.MediaTitle;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
import com.google.gdata.data.youtube.YouTubeNamespace;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;

public class YouTubeLoginActivity extends Activity implements OnTouchListener{
	File videofile = null;
	String videofilename = null;
	EditText usernameedt;
	EditText passwordedt;
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

        usernameedt = (EditText)findViewById(R.id.youtubeusername);
        passwordedt = (EditText)findViewById(R.id.youtubepassword);
        
        usernameedt.setOnTouchListener(this);
        passwordedt.setOnTouchListener(this);
        
	}
	
	public void getAuthToken(View view) throws MalformedURLException, IOException, ServiceException
	{
		//String AuthToken = "";
			
    	String username = usernameedt.getText().toString();
		
    	
    	String password = passwordedt.getText().toString();
    	
    	if(username==null||password==null)
    	{
    		showDialog(0);
    		
    	}
    	
    	/*YouTubeAuthHelper youtubeauth = new YouTubeAuthHelper();
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
		String messageText = sendYouTubeRequest(AuthToken);*/
		
		YouTubeService service = new YouTubeService("LightHouse", Values.YOUTUBE_DEVELOPER_CODE);
		service.setUserCredentials(username, password);
		VideoEntry newEntry = new VideoEntry();

		YouTubeMediaGroup mg = newEntry.getOrCreateMediaGroup();
		mg.setTitle(new MediaTitle());
		mg.getTitle().setPlainTextContent("My First Video");
		mg.addCategory(new MediaCategory(YouTubeNamespace.CATEGORY_SCHEME, "Autos"));
		mg.setKeywords(new MediaKeywords());
		mg.getKeywords().addKeyword("lighthouse");
		mg.getKeywords().addKeyword("live");
		mg.setDescription(new MediaDescription());
		mg.getDescription().setPlainTextContent("Video from LightHouse app");
		mg.setPrivate(false);
		mg.addCategory(new MediaCategory(YouTubeNamespace.DEVELOPER_TAG_SCHEME, "mydevtag"));
		mg.addCategory(new MediaCategory(YouTubeNamespace.DEVELOPER_TAG_SCHEME, "anotherdevtag"));

		//newEntry.setGeoCoordinates(new GeoRssWhere(37.0,-122.0));
		// alternatively, one could specify just a descriptive string
		newEntry.setLocation("Atlanta, GA");

		MediaFileSource ms = new MediaFileSource(videofile, "video/mp4");
		newEntry.setMediaSource(ms);

		String uploadUrl =
		  "http://uploads.gdata.youtube.com/feeds/api/users/default/uploads";

		VideoEntry createdEntry = service.insert(new URL(uploadUrl), newEntry);
		String videoURL = createdEntry.getHtmlLink().toString();
		
		Log.d("videoURL", videoURL);
		
		Intent j  = new Intent();
		j.putExtra(Values.COMING_FROM, Values.COMING_FROM_YOUTUBE);
		j.putExtra("message", videoURL);
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
		GoogleLibraryHelper googlelib = new GoogleLibraryHelper();
		String uploadurl="";
		try {
			uploadurl = googlelib.getUploadUrl(AuthToken, videofilename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String messageText = sendVideo(uploadurl);
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
	
	public boolean onTouch(View v, MotionEvent event) 
	{
		EditText focusEditText=(EditText)v;
		Log.d("On Touch Listener", "true");
		String contents=focusEditText.getText().toString();
		if(contents.equals("YouTube Username"))
		{
			
			focusEditText.setText("");
			return false;
		}
		if(contents.equals("YouTube Password"))
		{
			focusEditText.setText("");
			focusEditText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
			return false;
		}
		return false;
	}
	
}