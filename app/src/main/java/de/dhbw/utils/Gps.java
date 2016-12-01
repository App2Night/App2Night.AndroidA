package de.dhbw.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import java.util.List;

import de.dhbw.exceptions.GPSUnavailableException;

/**
 * Created by Tobias Berner on 24.10.2016.
 */

public class Gps {

    private static Gps gps = null;

    private Gps(){ }


    public static Gps getInstance(){
        if ( gps == null)
            gps = new Gps();
        return gps;
    }

    /**
     * Gibt die aktuelle GPS Position zurück.
     *
     * @return Array mit 2 Weten. Der erste Wert ist die Latitude, der zweite die Longtitude
     * @throws GPSUnavailableException - falls die GPS Koordinaten aus irgendeinem Grund nicht abgefragt werden können
     */
    public double[] getGPSCoordinates () throws GPSUnavailableException {
        // Acquire a reference to the system Location Manager
        double[] rueckgabe;
        Location location;
        try {
           location = getLastKnownLocation();
            //Breitengrad
            double latitude = location.getLatitude();
            //Längengrad
            double longtitude = location.getLongitude();
            rueckgabe = new double[2];
            rueckgabe[0] = latitude;
            rueckgabe[1] = longtitude;
            return rueckgabe;
        }catch (Exception e){
            throw new GPSUnavailableException();
        }
    }

    private Location getLastKnownLocation () throws SecurityException  {
        Context c = ContextManager.getInstance().getContext();
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
