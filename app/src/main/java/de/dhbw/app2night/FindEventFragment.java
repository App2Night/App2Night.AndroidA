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

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import de.dhbw.backendTasks.party.GetPartyList;
import de.dhbw.backendTasks.party.GetPartyListTask;
import de.dhbw.exceptions.GPSUnavailableException;
import de.dhbw.model.Party;
import de.dhbw.utils.Gps;

/**
 * Created by Flo on 31.10.2016.
 */
public class FindEventFragment extends Fragment {

    //Variablen
    final static String ARG_PARTYLIST = "arg_partylist";
    ArrayList<Party> partyList;
    MapView mMapView;
    private GoogleMap googleMap;
    Marker userPosition;
    LatLng pos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        partyList = (ArrayList<Party>) getArguments().getSerializable(ARG_PARTYLIST);

    }

    /**
     * Wird aufgerufen, sobald die View erstellt wird
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_findevent, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        // Map initialisieren
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        showPartiesOnMap(partyList);
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
     * Zeig die Position aller übergebenen Parties an.
     *
     * @param parties Anzuzeigende Paries
     */
    public void showPartiesOnMap(final ArrayList<Party> parties){
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                List<Marker> markers = new ArrayList<>();
                googleMap = mMap;
                for (Party party : parties) {
                    pos = new LatLng(party.getLocation().getLatitude(), party.getLocation().getLongitude());
                    userPosition = googleMap.addMarker(new MarkerOptions().
                            position(pos).
                            title(party.getPartyName()).
                            snippet(""));
                    markers.add(userPosition);
                }
                //Berechne richtigen Kamerazoom
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (Marker marker : markers) {
                    builder.include(marker.getPosition());
                }
                //Wenn Gps Verfügbar ist füge eigene Position ein und setzt Markerfarbe auf blau
                try{
                    double[] koord = Gps.getInstance().getGPSCoordinates();
                    pos = new LatLng(koord[0], koord[1]);
                    userPosition = googleMap.addMarker(new MarkerOptions().
                            position(pos).
                            title(getString(R.string.user_position)).
                            snippet("").
                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                    //Damit Zoom auch in Abhängigkeit von User Id angezeigt wird
                    builder.include(userPosition.getPosition());
                }catch (GPSUnavailableException e){
                    //Wenn GPS nicht verfügbar, dann beziehe es nicht mit ein in Berechnung
                }
                LatLngBounds bounds = builder.build();
                int padding = 10; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                googleMap.animateCamera(cu);
            }
        });
    }
}



