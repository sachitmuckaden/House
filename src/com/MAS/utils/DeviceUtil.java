package com.MAS.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

public class DeviceUtil{
	public String getDeviceId(Context context)
	{
		
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId=telephonyManager.getDeviceId();
		return deviceId;
		
	}
}