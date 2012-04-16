package com.MAS.Lighthouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.MAS.helpers.Values;
import com.MAS.utils.HTTPUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
 
public class SingleListItemActivity extends Activity{
    
	Button like;
	TextView txtProduct;
	String messageText;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.single_list_item);
 
        txtProduct = (TextView) findViewById(R.id.posted_message);
        like = (Button) findViewById(R.id.submit_button);
 
        Intent i = getIntent();
        // getting attached intent data
        messageText = i.getStringExtra("messageText");
        String nick = i.getStringExtra("nickname");
        //String timestamp = i.getStringExtra("timestamp");
        // displaying selected product name
        txtProduct.setText(nick + "\n" + "\n" + messageText);
        
    }
	public void addLike(View v){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(Values.MESSAGE,messageText));
		
		HTTPUtil http = new HTTPUtil();
		Log.d("message", messageText);
    	
    	
    	//HttpResponse response = null;
    	JSONObject response = new JSONObject();
    	
    	
    	try {
			response = http.Execute(params, Values.LIKE_URL);
    		//senddata.SendJson(params);
    	} catch (ClientProtocolException e) {
    		
			// TODO Auto-generated catch block
			
    		System.out.println("Client protocol error while sending data");
		
    	} catch (IOException e) {
			// TODO Auto-generated catch block
		
    		System.out.println("IO error while sending data");
		}
	}
}