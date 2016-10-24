package de.dhbw.gps;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import java.util.List;

/**
 * Created by Tobias Berner on 24.10.2016.
 */

public class Gps {


    public static String getGPSCoordinates (Context c){
        // Acquire a reference to the system Location Manager

        try {
            Location location = getLastKnownLocation(c);
            //Breitengrad
            double latitude = location.getLatitude();
            //Längengrad
            double longtitude = location.getLongitude();


        }catch (SecurityException e){
            //Keine Erlaubnis für Zugriff auf GPS
        }
        return null;
    }


    private static Location getLastKnownLocation (Context c) throws SecurityException  {
        LocationManager locationManager = (LocationManager) c.getSystemService(c.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

}
