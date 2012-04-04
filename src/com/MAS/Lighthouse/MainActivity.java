package com.MAS.Lighthouse;


import com.MAS.helpers.Values;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ImageView post = (ImageView)findViewById(R.id.Post);
        post.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
         // TODO Auto-generated method stub
        	postActivity(v);
       }
       });
        ImageView scan = (ImageView)findViewById(R.id.Scan);
        scan.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
         // TODO Auto-generated method stub
        	scanActivity(v);
       }
       }); 
        ImageView settings = (ImageView)findViewById(R.id.Settings);
        settings.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
         // TODO Auto-generated method stub
        	scanActivity(v);
       }
       }); 
        
}
       
	public void scanActivity(View view){
		Log.d("POSTION", "Entered Scan Activity");
		Intent i=new Intent(this, DisplayMessageActivity.class);
		startActivity(i);
	}
	
	public void postActivity(View view){
		Intent i=new Intent(this , PostMessageActivity.class);
		i.putExtra(Values.COMING_FROM, Values.COMING_FROM_MAIN);
		startActivity(i);
	}
	
	/*protected void settingsActivity(){
		Intent i=new Intent(this , SettingsActivity.class);
		startActivity(i);
	}*/

}