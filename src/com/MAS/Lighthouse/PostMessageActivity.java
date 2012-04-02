package com.MAS.Lighthouse;

import java.io.File;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.MAS.helpers.MessageHelper;
import com.MAS.helpers.Values;
import com.MAS.utils.HTTPUtil;


public class PostMessageActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	
	public final int ACTION_TAKE_VIDEO = 1;
	public final int ACTION_YOUTUBE_LOGIN = 2;
	File f= null;
	String filename= null;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postlayout);
        
        Intent sender=getIntent();
        String extraData=sender.getExtras().getString(Values.COMING_FROM);
        if(extraData.equals(Values.COMING_FROM_YOUTUBE))
        {
        	String editText = sender.getExtras().getString("message");
        	EditText textbox = (EditText)findViewById(R.id.message_text);
        	textbox.setText(editText, TextView.BufferType.NORMAL);
        }
        //Set up click listeners for all buttons
        View submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);
    }
    public void onClick(View v)
    {
    	EditText editText = (EditText)findViewById(R.id.message_text);
    	String editTextStr = editText.getText().toString();
    	
    	MessageHelper message = new MessageHelper();
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	
    	
    	params = message.createMessage(this.getApplicationContext(), editTextStr);
    	
    	HTTPUtil http = new HTTPUtil();
    	
    	
    	//HttpResponse response = null;
    	JSONObject response = new JSONObject();
    	
    	
    	try {
			response = http.Execute(params, Values.POSTMESSAGE_URL);
    		//senddata.SendJson(params);
    	} catch (ClientProtocolException e) {
    		
			// TODO Auto-generated catch block
			
    		System.out.println("Client protocol error while sending data");
		
    	} catch (IOException e) {
			// TODO Auto-generated catch block
		
    		System.out.println("IO error while sending data");
		}
    	
    	String messageText = "";
    	String timestamp = "";
    	try {
				messageText = response.getString(Values.MESSAGE);
				timestamp = response.getString(Values.TIMESTAMP);
		} catch (JSONException e) {
				messageText = "";
				timestamp = "";
			e.printStackTrace();
		}
    	
    	TextView t=new TextView(this); 
    	t=(TextView)findViewById(R.id.posted_message);
    
    	t.setBackgroundResource(R.color.black);    
    	t.setText(messageText+ "\n" + "                  " + timestamp);
    	TextView p = new TextView(this);
    	p = (TextView)findViewById(R.id.message_text);
    		p.setText("");
    	Log.d(null, response.toString());
    	
    	
    	
    }
    public void startVideo(View view)
    {
    	Intent i = new Intent();
    	i.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
    	
		try {
			f = createVideoFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("Could not create file","video file");
			e.printStackTrace();
		}
    	i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
    	startActivityForResult(i, ACTION_TAKE_VIDEO);
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	
    	if(requestCode == ACTION_TAKE_VIDEO)
    	{
    		Log.d("Video received", "Sending video");
    	}
    	
    	Intent i = new Intent(this, YouTubeLoginActivity.class);
    	i.putExtra("videofile", f);
    	i.putExtra("filename",filename);
    	startActivity(i);
    	
    	
    }
    
       
    public File createVideoFile() throws IOException {
        // Create an image file name
        String timeStamp = 
            new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String videoFileName = Values.MP4_FILE_PREFIX + timeStamp + "_";
        File image = File.createTempFile(
            videoFileName, 
            Values.MP4_FILE_SUFFIX, 
            Environment.getExternalStoragePublicDirectory(
    		        Environment.DIRECTORY_DCIM
    		       )
            
        );
        String mCurrentVideoPath = image.getAbsolutePath();
        Log.d("Video path",mCurrentVideoPath);
        filename = image.getName();
       
        return image;
    }
    
}