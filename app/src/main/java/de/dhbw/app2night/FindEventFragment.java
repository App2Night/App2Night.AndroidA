package de.dhbw.app2night;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import de.dhbw.backendTasks.party.GetPartyList;
import de.dhbw.backendTasks.party.GetPartyListTask;
import de.dhbw.exceptions.GPSUnavailableException;
import de.dhbw.model.Party;
import de.dhbw.utils.Gps;

/**
 * Created by Flo on 31.10.2016.
 */
public class FindEventFragment extends Fragment implements GetPartyList {

    //Variablen
    MapView mMapView;
    private GoogleMap googleMap;
    Gps gps;
    Marker userPosition;
    LatLng pos;
    private double latitudeUser;
    private double longtitudeUser;

    /**
     * Wird aufgerufen, sobald die View erstellt wird
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_findevent, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        gps = Gps.getInstance();

        // Koordinaten des Users holen, falls es fehlschlägt Alert Dialog anzeigen
        try {
            double[] gpsKoord = gps.getGPSCoordinates();
            latitudeUser = gpsKoord[0];
            longtitudeUser = gpsKoord[1];
            new GetPartyListTask(this, latitudeUser, longtitudeUser);
        } catch (GPSUnavailableException e) {
            showSettingsAlert();
        }
        // Map initialisieren
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }



    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    /**
     * Alert Dialog, der dem Nutzer mitteilt, dass das GPS nicht aktiviert ist. Bietet die Möglichkeit direkt zu den Settings zu wechseln oder dies erst Später zu ändern.
     */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        alertDialog.setTitle("GPS nicht verfügbar");

        alertDialog.setMessage("App2Night benötigt deinen derzeitgen Aufenthaltsort, um dir Partys in deiner Nähe anzuzeigen.");

        alertDialog.setPositiveButton("Zu den Einstellungen", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                getActivity().startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Später", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    /**
     * Wird aufgerufen, wenn die PartyListe erfolgreich geladen werden konnte. Updated die Position des Users, der Kamera und zeigt die Parties auf der Map an.
     * @param parties: Alle Parties, die geladen wurden
     */
    @Override
    public void onSuccessGetPartyList(final Party[] parties) {
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button

                //googleMap.setMyLocationEnabled(true); //TODO: Fixen von setMyLocationEnabled

                // For dropping a marker at a point on the Map


                   for (Party p : parties) {
                       pos = new LatLng(p.getLocation().getLatitude(), p.getLocation().getLongitude());
                       userPosition = googleMap.addMarker(new MarkerOptions().position(pos).title(p.getPartyName()).snippet(""));
                   }
                pos = new LatLng(latitudeUser, longtitudeUser);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(11).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                }
        });
    }

    @Override
    public void onFailGetPartyList() {

    }
}



