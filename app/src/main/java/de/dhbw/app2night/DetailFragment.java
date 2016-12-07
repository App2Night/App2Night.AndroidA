package de.dhbw.app2night;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import de.dhbw.backendTasks.party.DeletePartyById;
import de.dhbw.backendTasks.party.DeletePartyByIdTask;
import de.dhbw.exceptions.GPSUnavailableException;
import de.dhbw.model.Party;
import de.dhbw.utils.CustomMapView;
import de.dhbw.utils.Gps;

/**
 * Created by Bro on 25.11.2016.
 */

public class DetailFragment extends Fragment implements View.OnClickListener, DeletePartyById{

    public static final String ARG_PARTY = "arg_party";

    View rootView;
    CustomMapView mMapView;
    private GoogleMap googleMap;
    Gps gps;
    Marker userPosition;
    LatLng pos;
    Party partyToDisplay;
    TextView tvPartyName, tvStreetName, tvHouseNumber, tvZipCode, tvCityName, tvCountryName, tvDate, tvTime, tvPartyType, tvMusicGenre, tvDescription;
    ScrollView scrollViewDetailView;
    Button buttonEdit, buttonParticipate, buttonVote, buttonCancelParticipation, buttonCancelEvent;

    Status status;
    private enum Status{
        Host, Participant, NotParticipant, Bookmarked, Unknown
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        partyToDisplay = (Party)getArguments().getSerializable("arg_party");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail_view, container, false);
        scrollViewDetailView = (ScrollView)rootView.findViewById(R.id.detail_view_scrollview_main);

        mMapView = (CustomMapView) rootView.findViewById(R.id.detail_view_mapView);
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
        tvPartyName = (TextView)rootView.findViewById(R.id.detail_view_text_party_name);
        tvPartyName.setText(partyToDisplay.getPartyName());
        tvStreetName = (TextView)rootView.findViewById(R.id.detail_view_text_street_name);
        tvStreetName.setText(partyToDisplay.getLocation().getStreetName());
        tvHouseNumber = (TextView)rootView.findViewById(R.id.detail_view_text_house_number);
        tvHouseNumber.setText(partyToDisplay.getLocation().getHouseNumber());
        tvZipCode = (TextView)rootView.findViewById(R.id.detail_view_text_zipcode);
        tvZipCode.setText(partyToDisplay.getLocation().getZipcode());
        tvCityName = (TextView)rootView.findViewById(R.id.detail_view_text_city_name);
        tvCityName.setText(partyToDisplay.getLocation().getCityName());
        tvCountryName = (TextView)rootView.findViewById(R.id.detail_view_text_country_name);
        tvCountryName.setText(partyToDisplay.getLocation().getCountyName());

        tvDate = (TextView)rootView.findViewById(R.id.detail_view_text_party_date);
        tvDate.setText(partyToDisplay.getPartyDate());
        tvTime = (TextView)rootView.findViewById(R.id.detail_view_text_party_time);
        tvTime.setText(partyToDisplay.getPartyDate());

        tvPartyType = (TextView)rootView.findViewById(R.id.detail_view_text_party_type);
        tvPartyType.setText(Integer.toString(partyToDisplay.getPartyType()));
        tvMusicGenre = (TextView)rootView.findViewById(R.id.detail_view_text_music_genre);
        tvMusicGenre.setText(Integer.toString(partyToDisplay.getMusicGenre()));
        tvDescription = (TextView)rootView.findViewById(R.id.detail_view_text_description);
        tvDescription.setText(partyToDisplay.getDescription());

        buttonEdit = (Button)rootView.findViewById(R.id.detail_view_button_edit);
        buttonEdit.setOnClickListener(this);
        buttonParticipate =(Button)rootView.findViewById(R.id.detail_view_button_participate);
        buttonParticipate.setOnClickListener(this);
        buttonVote = (Button)rootView.findViewById(R.id.detail_view_button_vote);
        buttonVote.setOnClickListener(this);
        buttonCancelParticipation = (Button)rootView.findViewById(R.id.detail_view_button_cancel_participation);
        buttonCancelParticipation.setOnClickListener(this);
        buttonCancelEvent = (Button)rootView.findViewById(R.id.detail_view_button_cancel_event);
        buttonCancelEvent.setOnClickListener(this);

        if(partyToDisplay.isHostedByUser()){
            status = Status.Host;
        }else if(partyToDisplay.getUserCommitmentState().equals("2")) {
            status = Status.NotParticipant;
        }else if(partyToDisplay.getUserCommitmentState().equals("1")) {
            status = Status.Bookmarked;
        }else if(partyToDisplay.getUserCommitmentState().equals("0")) {
            status = Status.Participant;
        }else{
            status = Status.Unknown;
        }

        initializeButtons();

    }

    private void initializeButtons() {
        switch(status){
            case Participant:
                buttonVote.setVisibility(View.VISIBLE);
                buttonCancelParticipation.setVisibility(View.VISIBLE);
                break;
            case NotParticipant:
                buttonParticipate.setVisibility(View.VISIBLE);
                break;
            case Host:
                buttonCancelEvent.setVisibility(View.VISIBLE);
                break;
            case Bookmarked:
                buttonParticipate.setVisibility(View.VISIBLE);
                buttonCancelParticipation.setVisibility(View.VISIBLE);
                break;
            case Unknown:
                //Ein unbekannter Status fuehrt dazu, dass keine Buttons angezeigt werden
                break;
        }
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
    public void onClick(View v) {
        int id = v.getId();

        //TODO: Events auslösen
        switch (id){
            case R.id.detail_view_button_edit:
                //Öffne EditFragement
                break;
            case R.id.detail_view_button_participate:
                //Übertrage neuen Status, verstecke Teilnahmebutton, zeige Absagebutton und Votebutton, sobald Party beginnt
                buttonParticipate.setVisibility(View.GONE);
                buttonCancelParticipation.setVisibility(View.VISIBLE);
                //TODO: Abfrage, ob Party bereits angefangen hat
                buttonVote.setVisibility(View.VISIBLE);
                break;
            case R.id.detail_view_button_vote:
                //Zeige Votedialog
                break;
            case R.id.detail_view_button_cancel_participation:
                //Übertrage neuen Status, verstecke Absagebutton und Votebutton und zeige Teilnehmebutton
                buttonCancelParticipation.setVisibility(View.GONE);
                buttonVote.setVisibility(View.GONE);
                buttonParticipate.setVisibility(View.VISIBLE);
                break;
            case R.id.detail_view_button_cancel_event:
                new DeletePartyByIdTask(this, partyToDisplay.getPartyId());

        }
    }

    @Override
    public void onSuccessDeletePartyById(boolean result) {

    }

    @Override
    public void onFailDeletePartyById(boolean result) {

    }
}
