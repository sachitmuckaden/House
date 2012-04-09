package com.MAS.Lighthouse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
 
public class SingleListItemActivity extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.single_list_item);
 
        TextView txtProduct = (TextView) findViewById(R.id.posted_message);
 
        Intent i = getIntent();
        // getting attached intent data
        String messageText = i.getStringExtra("messageText");
        String nick = i.getStringExtra("nickname");
        //String timestamp = i.getStringExtra("timestamp");
        // displaying selected product name
        txtProduct.setText(nick + "\n" + "\n" + messageText);
 
    }
}