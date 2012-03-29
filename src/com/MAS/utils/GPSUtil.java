package com.MAS.utils;

import android.content.Context;
import android.location.*;

public class GPSUtil{

	private LocationManager mgr;
	private String best;
	public Location getGPSLocation(Context context)
	{
		this.mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		this.best = this.mgr.getBestProvider(criteria, true);
		Location location = null;
		while(location==null)
			location = this.mgr.getLastKnownLocation(this.best);
		return location;
	}

	
}