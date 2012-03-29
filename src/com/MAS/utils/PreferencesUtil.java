package com.MAS.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesUtil
{
	public static final String SETTINGS_FILE_NAME = "com.MAS.Lighthouse";
	public static void loggedin(Activity activity)
	{
		
			Editor editor = getPreferences(activity).edit();
			
			editor.putBoolean("loggedin", true);
			editor.commit();
		
	}

	public static SharedPreferences getPreferences(Context context) {
		// TODO Auto-generated method stub
		return context.getSharedPreferences(SETTINGS_FILE_NAME, context.MODE_PRIVATE);
	}
	
	public static boolean checkLoggedIn(Activity activity)
	{
		return getPreferences(activity).contains("loggedin");
	}
}