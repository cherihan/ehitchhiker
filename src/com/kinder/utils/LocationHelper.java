/**
 * LocationHelper.java, Created 25 janv. 2011 at 11:01:32
 *
 * Project : ehitchhikerAndroid
 * Package : com.kinder.utils
 * 
 * Author : Christophe Dupont
 * Mail : mail@christophedupont.com
 * 
 */
package com.kinder.utils;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;



public class LocationHelper {

	private static LocationHelper instance;
	private static final int REFRESH_TIME = 1000 * 60 * 2;
	LocationHelperListener listener;
	LocationManager locationManager;
	LocationListener locationListener;
	static Location currentBestLocation;
	static final double EARTH_RADIUS = 6367.45;
	


	private LocationHelper(LocationManager manager) {
		// Acquire a reference to the system Location Manager
		locationManager = (LocationManager) manager;
		

		// Define a listener that responds to location updates
		locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				Log.v("LocationHelper","LocationHelper : onLocationChanded");
				// Called when a new location is found by the network location provider.
				makeUseOfNewLocation(location);
			}

			public void onStatusChanged(String provider, int status, Bundle extras) {}
			public void onProviderEnabled(String provider) {}
			public void onProviderDisabled(String provider) {}
		};
	}


	public static void init(LocationManager manager, LocationHelperListener listen) {
		if(null == instance) {
			try {
				instance.stop();

			}
			catch (Exception e) {

			}
			
			instance = new LocationHelper(manager);

		}

		instance.setListener(listen);
	}

	public static LocationHelper get() {
		if(null == instance) {
			Log.v("LocationHelper", "LocationHelper must be initialized first");

		}
		return instance;
	}




	public void start() {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		String provider = locationManager.getBestProvider(criteria, true);
		
		Log.v("LocationHelper","LocationHelper START : best provider : " + provider);
		
		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
		
		//old position directly, so that we don't wait to receive the first position
		try {
			Location l = locationManager.getLastKnownLocation(provider);
			if(null != l) makeUseOfNewLocation(l);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		locationManager.removeUpdates(locationListener);
	}

	private void setListener(LocationHelperListener listen) {
		Log.v("LocationHelper","LocationHelper has a listerner to send updates to");
		this.listener = listen;
	}



	private void makeUseOfNewLocation(Location location) {
		if(null != listener) {
			if( null == currentBestLocation || isBetterLocation(location)) {
				currentBestLocation = location;
				listener.onBetterLocationReceived(location);
			}
		}
		else {
			stop();
		}
	}

	public static int distanceTo(double latitude, double longitude) {
		
		if(null != currentBestLocation) {
	
			Log.v("Comparator", "between ["+ currentBestLocation.getLatitude()+","+currentBestLocation.getLongitude()+"] et ["+latitude+","+longitude+"]");
			
		    double dLat = Math.toRadians(currentBestLocation.getLatitude()-latitude);
		    double dLng = Math.toRadians(currentBestLocation.getLongitude()-longitude);
		    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		               Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(currentBestLocation.getLatitude())) *
		               Math.sin(dLng/2) * Math.sin(dLng/2);
		    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		    double dist = EARTH_RADIUS * c;
		    
			Log.v("LocationHelper", "Comparator : distance : " + dist);
		    return (int) dist;
		}
		else {
			Log.v("LocationHelper", "best location is null, make sure you started locationHelper before");
			return -1;
		}
	}



	/** Determines whether one Location reading is better than the current Location fix
	 * @param location  The new Location that you want to evaluate
	 * @param currentBestLocation  The current Location fix, to which you want to compare the new one
	 */
	protected boolean isBetterLocation(Location location) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > REFRESH_TIME;
		boolean isSignificantlyOlder = timeDelta < -REFRESH_TIME;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
			return true;
		}
		return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	public interface LocationHelperListener {
		public void onBetterLocationReceived(Location newLocation);
	}
}
