package com.MAS.Lighthouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
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
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postlayout);
        
        
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
    	startActivityForResult(i, ACTION_TAKE_VIDEO);
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	if(requestCode == ACTION_TAKE_VIDEO)
    	{
    		Log.d("Video received", "Sending video");
    	}
    	
    }
}