package com.MAS.Lighthouse;


import java.io.IOException;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;
import com.MAS.utils.*;


import com.MAS.helpers.*;


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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements View.OnTouchListener
{
	private	Button loginpageloginbutton;
	private TextView loginpagesignupbutton;
	private EditText username;
	private EditText password;
	
	private static final int LOGIN_FAILURE=0;

	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	if(PreferencesUtil.checkLoggedIn(this)){
			finish();
			Intent myIntent = new Intent(this, MainActivity.class);
            startActivity(myIntent);
		}
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlayout);
        
        
        loginpageloginbutton=(Button)findViewById(R.id.loginpageloginbutton);
        loginpagesignupbutton=(TextView)findViewById(R.id.link_to_register);
        
        loginpagesignupbutton.setOnClickListener(new View.OnClickListener() {
        	 
            public void onClick(View v) {
                // Switching to Register screen
            	Intent i= new Intent(getApplicationContext(),SignUpActivity.class);
            	startActivity(i);
            }
        });
        
        
        
        
        username=(EditText)findViewById(R.id.loginpageusername);
        password=(EditText)findViewById(R.id.loginpagepassword);
        

        username.setOnTouchListener(this);
        password.setOnTouchListener(this);
       
    }
    
    @Override
    protected void onResume() 
    {
    	super.onResume();
    	Log.d("RESUME","RESUME");
    	//loginpageloginbutton.setBackgroundResource(R.drawable.loginbutton1);
        //loginpagesignupbutton.setBackgroundResource(R.drawable.signupbutton);
        username.setText(R.string.username);
        password.setText(R.string.password);
        
    	
    }
   
    /* Method to perform the login operation */ 
    
    public void login(View view)
    {
    	//loginpageloginbutton.setBackgroundResource(R.drawable.loginbuttonclicked);
    	String userNameText=username.getText().toString().replace(' ','_');
    	if(userNameText.equals(""))
    	{
    		userNameText="null";
    	}
    	String passwordText=password.getText().toString().replace(' ','_');
    	if(passwordText.equals(""))
    	{
    		passwordText="null";
    	}
     	
    	LoginHelper login = new LoginHelper();
    	List<NameValuePair> params = login.createMessage(this.getApplicationContext(), userNameText, passwordText);
    	
    	JSONObject response = new JSONObject();
    	HTTPUtil http = new HTTPUtil();
    	try {
			response = http.Execute(params,Values.LOGIN_URL);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	String success="";
		try {
			success = response.getString(Values.SUCCESS);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			success="0";
		}
    	String error;
		try {
			error = response.getString(Values.ERROR_CODE);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			error = "2";
		}
    	
    	if(success.equals("1"))
    	{
    		PreferencesUtil.loggedin(this);
    		//loginpageloginbutton.setBackgroundResource(R.drawable.loginbuttonclicked);
    		Intent i=new Intent(this, MainActivity.class);
    		//i.putExtra("name", loginVO.getName());
    		//i.putExtra("userid",loginVO.getUserId()+"");
    		startActivity(i);
    	}
    	else
    	{
    		int ERROR_CODE = Integer.parseInt(error);
    		
    		showDialog(ERROR_CODE);
    		
    		
    	}
    }
    
    public void signup(View view)
    {
    	//loginpagesignupbutton.setBackgroundResource(R.drawable.loginbuttonclicked);
    	Intent i=new Intent(this,SignUpActivity.class);
    	startActivity(i);
    }

	public boolean onTouch(View v, MotionEvent event) 
	{
		EditText focusEditText=(EditText)v;
		String contents=focusEditText.getText().toString();
		if(contents.equals("App Username"))
		{
			
			focusEditText.setText("");
			return false;
		}
		if(contents.equals("Password"))
		{
			focusEditText.setText("");
			focusEditText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
			return false;
		}
		return false;
	}
	
	@Override
	protected Dialog onCreateDialog(int id) 
	{
		if(id==1)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Login Error");
			builder.setMessage("Entered password is incorrect!");
			builder.setIcon(R.drawable.ic_launcher);
			builder.setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) 
				{
				return;
					
				}
			});
			return builder.create();
			
			
		}
		else if(id==2)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Login Error");
			builder.setMessage("Entered username does not exist!");
			builder.setIcon(R.drawable.ic_launcher);
			builder.setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) 
				{
				return;
					
				}
			});
			return builder.create();
		}
		return null;
	}
	



}