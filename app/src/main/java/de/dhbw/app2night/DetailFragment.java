package de.dhbw.app2night;

import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.dhbw.backendTasks.party.DeletePartyById;
import de.dhbw.backendTasks.party.DeletePartyByIdTask;
import de.dhbw.backendTasks.userparty.SetCommitmentState;
import de.dhbw.backendTasks.userparty.SetCommitmentStateTask;
import de.dhbw.model.CommitmentState;
import de.dhbw.model.MusicGenre;
import de.dhbw.model.Party;
import de.dhbw.model.PartyType;
import de.dhbw.utils.CustomMapView;
import de.dhbw.utils.DateUtil;
import de.dhbw.utils.Gps;

/**
 * Created by Bro on 25.11.2016.
 */

public class DetailFragment extends Fragment implements View.OnClickListener, DeletePartyById, SetCommitmentState {

    public interface OnChangePartyListener {
        void onClickChangePartyButton(Party partyToChange);
    }

    public interface OpenVoteDialog {
        void openVoteDialog(String partyId);
    }

    public interface ReturnToHomeFragment {
        void returnToHomeFragment();
    }

    public static final String ARG_PARTY = "arg_party";

    OnChangePartyListener mCallback;
    OpenVoteDialog mVoteCallback;
    ReturnToHomeFragment mReturnHomeCallback;
    View rootView;
    CustomMapView mMapView;
    private GoogleMap googleMap;
    Gps gps;
    Marker userPosition;
    LatLng pos;
    Party partyToDisplay;
    TextView tvPartyName, tvStreetName, tvHouseNumber,
            tvZipCode, tvCityName, tvCountryName, tvDate, tvTime, tvPartyType, tvMusicGenre, tvDescription,
            tvVotingGeneral, tvVotingLocation, tvVotingPrice, tvVotingMood, tvPreis;
    ScrollView scrollViewDetailView;
    Button buttonEdit, buttonParticipate, buttonVote, buttonCancelParticipation, buttonCancelEvent;

    Status status;

    private enum Status{
        Host, Participant, NotParticipant, Bookmarked, Unknown
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Darzustellende Party wird aus mitgegebenen Argumenten ausgelesen
        partyToDisplay = (Party)getArguments().getSerializable(ARG_PARTY);

        try {
            //Callbackverbindung zum Öffnen des ChangePartyFragments
            mCallback = (DetailFragment.OnChangePartyListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement onChangePartyListener");
        }

        try {
            //Callbackverbindung zum Öffnen des VoteDialogFragments
            mVoteCallback = (DetailFragment.OpenVoteDialog) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OpenVoteDialog");
        }

        try {
            //Callbackverbindung zum Zurückkehren zum HomeFragment
            mReturnHomeCallback = (DetailFragment.ReturnToHomeFragment) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement ReturnToHomeFragment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail_view, container, false);
        scrollViewDetailView = (ScrollView)rootView.findViewById(R.id.detail_view_scrollview_main);

        //Verknüpfung des MapViews mit MapLifecycle
        mMapView = (CustomMapView) rootView.findViewById(R.id.detail_view_mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        initializeViews();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                    googleMap = mMap;
                    pos = new LatLng(partyToDisplay.getLocation().getLatitude(), partyToDisplay.getLocation().getLongitude());
                    userPosition = googleMap.addMarker(new MarkerOptions().position(pos).title("Party Position").snippet(""));
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(12).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        });

        return rootView;
    }

    /**
     * Initialisiert alle Elemente des Views und verknüpft benötigte OnClickListener
     */
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
        tvDate.setText(DateUtil.getInstance().getDate(partyToDisplay.getPartyDate()));
        tvTime = (TextView)rootView.findViewById(R.id.detail_view_text_party_time);
        tvTime.setText(DateUtil.getInstance().getTime(partyToDisplay.getPartyDate()));

        tvPartyType = (TextView)rootView.findViewById(R.id.detail_view_text_party_type);
        tvPartyType.setText(PartyType.toEnum(partyToDisplay.getPartyType()).toString());
        tvMusicGenre = (TextView)rootView.findViewById(R.id.detail_view_text_music_genre);
        tvMusicGenre.setText(MusicGenre.toEnum(partyToDisplay.getMusicGenre()).toString());
        tvDescription = (TextView)rootView.findViewById(R.id.detail_view_text_description);
        tvDescription.setText(partyToDisplay.getDescription());
        tvPreis = (TextView)rootView.findViewById(R.id.detail_view_text_price);
        tvPreis.setText(Integer.toString(partyToDisplay.getPrice()));

        tvVotingGeneral = (TextView)rootView.findViewById(R.id.detail_view_text_voting_general);
        int generalUpVotes = partyToDisplay.getGeneralUpVoting();
        int generalDownVotes = partyToDisplay.getGeneralDownVoting();
        if(generalUpVotes+generalDownVotes != 0) {
            tvVotingGeneral.setText(calculatePercentage(generalUpVotes, generalDownVotes) + " %");
        }else{
            tvVotingGeneral.setText("-");
        }

        tvVotingLocation = (TextView)rootView.findViewById(R.id.detail_view_text_voting_location);
        int locationUpVotes = partyToDisplay.getLocationUpVoting();
        int locationDownVotes = partyToDisplay.getLocationDownVoting();
        if(locationUpVotes+generalDownVotes != 0) {
            tvVotingLocation.setText(calculatePercentage(locationUpVotes, locationDownVotes)+ " %");
        }else{
            tvVotingLocation.setText("-");
        }

        tvVotingPrice = (TextView)rootView.findViewById(R.id.detail_view_text_voting_price);
        int priceUpVotes = partyToDisplay.getPriceUpVoting();
        int priceDownVotes = partyToDisplay.getPriceDownVoting();
        if(priceUpVotes+priceDownVotes != 0) {
            tvVotingPrice.setText(calculatePercentage(priceUpVotes, priceDownVotes)+ " %");
        }else{
            tvVotingPrice.setText("-");
        }

        tvVotingMood = (TextView)rootView.findViewById(R.id.detail_view_text_voting_mood);
        int moodUpVotes = partyToDisplay.getMoodUpVoting();
        int moodDownVotes = partyToDisplay.getMoodDownVoting();
        if(moodUpVotes+moodDownVotes != 0) {
            tvVotingMood.setText(calculatePercentage(moodUpVotes, moodDownVotes)+ " %");
        }else{
            tvVotingMood.setText("-");
        }

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
        }else if(partyToDisplay.getUserCommitmentState()==2) {
            status = Status.NotParticipant;
        }else if(partyToDisplay.getUserCommitmentState() == 1) {
            status = Status.Bookmarked;
        }else if(partyToDisplay.getUserCommitmentState() == 0) {
            status = Status.Participant;
        }else{
            status = Status.Unknown;
        }

        initializeButtons();

    }

    /**
     * Berechnet den Prozentsatz von den upVotes zu den gesamten Votes
     * @param upVotes
     * @param downVotes
     * @return
     */
    private String calculatePercentage(int upVotes, int downVotes) {
        double percentage = (double)upVotes / ((double)upVotes + (double)downVotes) * 100.00;
        return Integer.toString((int)percentage);
    }


    /**
     * Evaluiert ob Party am heutigen Tag ist oder gestern war.
     * @return true, wenn party heute oder gestern war; sonst false
     */
    private boolean showVoteButton(){
        String sPDate = DateUtil.getInstance().getDateInFormat(partyToDisplay.getPartyDate());
        Calendar now;
        Date pDate = null;
        Date nowDate = null;
        now = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            pDate = format.parse(sPDate);
            nowDate = format.parse(format.format(now.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        long startTime = pDate.getTime();
        long endTime = nowDate.getTime();
        long diffTime = endTime - startTime;
        long diffDays = diffTime / (1000 * 60 * 60 * 24);

        if (diffDays == 0 || diffDays == 1)
            return true;

        return false;

    }

    /**
     * Initialisiert je nach Benutzer die anzuzeigenden Buttons
     */
    private void initializeButtons() {
        switch(status){
            case Participant:
                if (showVoteButton())
                    buttonVote.setVisibility(View.VISIBLE);
                buttonCancelParticipation.setVisibility(View.VISIBLE);
                break;
            case NotParticipant:
                buttonParticipate.setVisibility(View.VISIBLE);
                break;
            case Host:
                buttonCancelEvent.setVisibility(View.VISIBLE);
                buttonEdit.setVisibility(View.VISIBLE);
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

        switch (id){
            case R.id.detail_view_button_edit:
                //Öffne EditFragement
                mCallback.onClickChangePartyButton(partyToDisplay);
                break;
            case R.id.detail_view_button_participate:
                //Übertrage neuen Status, verstecke Teilnahmebutton, zeige Absagebutton und Votebutton, sobald Party beginnt
                new SetCommitmentStateTask(this, partyToDisplay.getPartyId(), CommitmentState.Commited);
                break;
            case R.id.detail_view_button_vote:
                //Zeige Votedialog uber MainActivity per Callback
                mVoteCallback.openVoteDialog(partyToDisplay.getPartyId());
                break;
            case R.id.detail_view_button_cancel_participation:
                //Starte Task für die Übermittelung des Teilnehmestatus
                new SetCommitmentStateTask(this, partyToDisplay.getPartyId(), CommitmentState.NotCommited);
                break;
            case R.id.detail_view_button_cancel_event:
                //Starte Task zum Löschen einer Veranstaltung
                new DeletePartyByIdTask(this, partyToDisplay.getPartyId());
        }
    }


    /**
     * Setzt Buttons entsprechend dem neuen Commitment State, nach erfolgreicher Übertragung des CommitmentStates per Task
     */
    @Override
    public void onSuccessCommitmentState(CommitmentState newCommitmentState) {
        partyToDisplay.setUserCommitmentState(CommitmentState.toInt(newCommitmentState));
        if (newCommitmentState == CommitmentState.Commited) {
            if (showVoteButton())
                buttonVote.setVisibility(View.VISIBLE);
            buttonCancelParticipation.setVisibility(View.VISIBLE);
            buttonParticipate.setVisibility(View.GONE);
        } else if (newCommitmentState == CommitmentState.NotCommited){
            buttonCancelParticipation.setVisibility(View.GONE);
            buttonVote.setVisibility(View.GONE);
            buttonParticipate.setVisibility(View.VISIBLE);
        }else if ( newCommitmentState == CommitmentState.Bookmarked){
            //Kommt in der APP nicht vor
        }

    }

    /**
     * Bei fehlgeschlagener Übertragung des CommitmentState erhält der Nutzer eine Fehlermeldung
     */
    @Override
    public void onFailCommitmentState() {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), "Übertragung des Teilnehmestatus ist fehlgeschlagen. Bitte versuchen Sie es erneut", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Bei erfoglreichem Löschen der Veranstaltung -> Toast mit Erfolgsmeldung und Callbackaufruf zur Rückkehr zum HomeFragment
     * @param result
     */
    @Override
    public void onSuccessDeletePartyById(boolean result) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), "Veranstaltung wurde erfolgreich gelöscht.", Toast.LENGTH_SHORT).show();
        }
        //Löst die Rückkehr zum HomeFragment aus
        mReturnHomeCallback.returnToHomeFragment();
    }

    /**
     * Löschen einer Veranstaltung fehlgeschlagen -> Taskabbruch und Ausführung
     * @param result
     */
    @Override
    public void onFailDeletePartyById(boolean result) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), "Fehler! Veranstaltung wurde nicht gelöscht. Bitte versuchen Sie es erneut", Toast.LENGTH_SHORT).show();
        }
    }
}
