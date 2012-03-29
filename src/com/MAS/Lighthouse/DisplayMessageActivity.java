package com.MAS.Lighthouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import  com.MAS.helpers.ReceiveHelper;
import com.MAS.helpers.Values;
import com.MAS.utils.HTTPUtil;


import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class DisplayMessageActivity extends ListActivity
{
	 @Override
	public void onCreate(Bundle savedInstanceState) {
		
		
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.displaymessagelayout);
		 Log.e("Position", "Entered DisplayMessage");
		
		//setContentView(R.layout.main);----> Important setContentView
		
		// Hashmap for ListView
		ArrayList<HashMap<String, String>> messageList = new ArrayList<HashMap<String, String>>();
		
		// Creating JSON Parser instance
		ReceiveHelper receive = new ReceiveHelper();
		List <NameValuePair> params = receive.createMessage(this.getApplicationContext());
		
		HTTPUtil http = new HTTPUtil();
		JSONObject json;
		try {
			json = http.Execute(params,Values.GETMESSAGES_URL);
		} catch (ClientProtocolException e1) {
			json = null;
			e1.printStackTrace();
		} catch (IOException e1) {
			json = null;
			e1.printStackTrace();
		}
		JSONArray message = new JSONArray();
		
		
		try {
		    // Getting Array of Contacts
		    message = json.getJSONArray(Values.MESSAGELIST);
		
		    // looping through All Contacts
		    for(int i = 0; i < message.length(); i++){
		        JSONObject c = message.getJSONObject(i);
		
		        // Storing each json item in variable
		        String text = c.getString(Values.MESSAGE);
		        String nickname = c.getString(Values.NICKNAME) + " said: ";
		        String timestamp = "         " + c.getString(Values.TIMESTAMP);
		                
		        Log.d("timestamp",timestamp);
		        // creating new HashMap
		        HashMap<String, String> map = new HashMap<String, String>();
		
		        // adding each child node to HashMap key => value
		        map.put(Values.MESSAGE, text);
		        map.put(Values.NICKNAME, nickname);
		        map.put(Values.TIMESTAMP, timestamp);
		        
		
		        // adding HashList to ArrayList
		        messageList.add(map);
		    }
		} catch (JSONException e) {
		    e.printStackTrace();
		}
		
		/**
		 * Updating parsed JSON data into ListView
		 * */
		ListAdapter adapter = new SimpleAdapter(this, messageList,
		        R.layout.displaylist_item,
		        new String[] { Values.MESSAGE, Values.NICKNAME, Values.TIMESTAMP }, new int[] {
		                R.id.messageText_label, R.id.nickname_label, R.id.timestamp_label });
		
		setListAdapter(adapter);
		/*
		// selecting single ListView item
		ListView lv = getListView();
		
		// Launching new screen on Selecting Single ListItem
		lv.setOnItemClickListener(new OnItemClickListener() {
		
		    @Override
		    public void onItemClick(AdapterView<?> parent, View view,
		            int position, long id) {
		        // getting values from selected ListItem
		        String name = ((TextView) view.findViewById(R.id.)).getText().toString();
		        String cost = ((TextView) view.findViewById(R.id.email)).getText().toString();
		        String description = ((TextView) view.findViewById(R.id.mobile)).getText().toString();
		
		        // Starting new intent
		        Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
		        in.putExtra(TAG_NAME, name);
		        in.putExtra(TAG_EMAIL, cost);
		        in.putExtra(TAG_PHONE_MOBILE, description);
		        startActivity(in);
		    }

			
				
			
		});*/
}
}