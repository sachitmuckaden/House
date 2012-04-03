package com.MAS.Lighthouse;

/* Java class which has the activity to register an user */


import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import com.MAS.helpers.SignUpHelper;
import com.MAS.helpers.Values;
import com.MAS.utils.HTTPUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
//import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
//import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.Toast;



public class SignUpActivity extends Activity implements View.OnTouchListener{
	private EditText signupname;
	private EditText signupusername;
	private EditText signuppassword;
	private EditText signupconfirmpassword;
	private Button signuppagebutton;
	String details;

	//private static final int SIGNUP_FAILURE = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signuplayout);

		signupname = (EditText) findViewById(R.id.signupname);
		signupusername = (EditText) findViewById(R.id.signupusername);
		signuppassword = (EditText) findViewById(R.id.signuppassword);
		//signupconfirmpassword = (EditText) findViewById(R.id.signupconfirmpassword);
		signuppagebutton = (Button) findViewById(R.id.signuppagebutton);

		signupname.setOnTouchListener(this);
		signupusername.setOnTouchListener(this);
		signuppassword.setOnTouchListener(this);
		//signupconfirmpassword.setOnTouchListener(this);
}

	@Override
	protected void onResume() {
		super.onRestart();
		setContentView(R.layout.signuplayout);

		signupname = (EditText) findViewById(R.id.signupname);
		signupusername = (EditText) findViewById(R.id.signupusername);
		signuppassword = (EditText) findViewById(R.id.signuppassword);
		//signupconfirmpassword = (EditText) findViewById(R.id.signupconfirmpassword);
		signuppagebutton = (Button) findViewById(R.id.signuppagebutton);

		signupname.setOnTouchListener(this);
		signupusername.setOnTouchListener(this);
		signuppassword.setOnTouchListener(this);
		//signupconfirmpassword.setOnTouchListener(this);
	}

	public void createaccount(View v) {

		
		
		SignUpHelper signup = new SignUpHelper();
		List<NameValuePair> params = signup.createMessage(this.getApplicationContext(), signupusername.getText().toString(), signuppassword.getText().toString(), signupname.getText().toString());
		
		HTTPUtil http = new HTTPUtil();
		JSONObject response = null;
		try {
			response = http.Execute(params, Values.SIGNUP_URL);
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//String status = "UNTESTED";

		String success="";
		try {
			success = response.getString(Values.SUCCESS);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			success="0";
		}
    	
		try {
			this.details = response.getString(Values.DETAILS);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			this.details = "Error 2";
		}
    	
    	if(success.equals("1"))
    	{
    		//loginpageloginbutton.setBackgroundResource(R.drawable.loginbuttonclicked);
    		Intent i=new Intent(this, MainActivity.class);
    		//i.putExtra("name", loginVO.getName());
    		//i.putExtra("userid",loginVO.getUserId()+"");
    		startActivity(i);
    	}
    	else
    	{
    		int ERROR_CODE =1;
    		
    		showDialog(ERROR_CODE);
    		
    		
    	}

		
		
	}

	public boolean onTouch(View v, MotionEvent event) {
		EditText focusEditText = (EditText) v;
		String contents = focusEditText.getText().toString();
		if (contents.equals("Nick Name")) {
			focusEditText.setText("");
			return false;
		}
		
		if (contents.equals("Password")) {
			focusEditText.setText("");
			focusEditText.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_PASSWORD);
			return false;
		}
		if (contents.equals("Username")) {
			focusEditText.setText("");
			return false;
		}
		
		return false;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		if (id == 1) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Registration Error");
			builder.setMessage(this.details);
			builder.setIcon(R.drawable.ic_launcher);
			builder.setPositiveButton(R.string.retry,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							return;

						}
					});
			return builder.create();

		}
		return null;
	}

}