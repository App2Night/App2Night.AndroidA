package de.dhbw.app2night;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import de.dhbw.exceptions.GPSUnavailableException;
import de.dhbw.model.Party;
import de.dhbw.utils.Gps;

/**
 * Created by Bro on 25.11.2016.
 */

public class DetailFragment extends Fragment implements View.OnTouchListener{

    public static final String ARG_PARTY = "arg_party";

    View rootView;
    MapView mMapView;
    private GoogleMap googleMap;
    Gps gps;
    Marker userPosition;
    LatLng pos;
    Party partyToDisplay;
    TextView partyName;
    ScrollView scrollViewDetailView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        partyToDisplay = (Party)getArguments().getSerializable("arg_party");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail_view, container, false);
        scrollViewDetailView = (ScrollView)rootView.findViewById(R.id.detail_view_scrollview_main);

        mMapView = (MapView) rootView.findViewById(R.id.detail_view_mapView);
        mMapView.setOnTouchListener(this);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        initializeViews();

        gps = Gps.getInstance();

        try {
            gps.getGPSCoordinates();
        } catch (GPSUnavailableException e) {
            showSettingsAlert();
        }
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

                try{
                    double[] gpsCoords = gps.getGPSCoordinates();
                    pos = new LatLng(gpsCoords[0], gpsCoords[1]);
                    userPosition = googleMap.addMarker(new MarkerOptions().position(pos).title("Ihre Position").snippet(""));
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(12).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                } catch (GPSUnavailableException e) {

                }
            }
        });

        return rootView;
    }

    private void initializeViews() {
        partyName = (TextView)rootView.findViewById(R.id.detail_view_text_party_name);
        partyName.setText(partyToDisplay.getPartyName());
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

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        alertDialog.setTitle("GPS");

        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                getActivity().startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        scrollViewDetailView.requestDisallowInterceptTouchEvent(true);

        int action = motionEvent.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_UP:
                scrollViewDetailView.requestDisallowInterceptTouchEvent(false);
                break;
        }

        return false;
    }

}
