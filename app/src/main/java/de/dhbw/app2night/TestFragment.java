package de.dhbw.app2night;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import de.dhbw.backendTasks.party.DeletePartyById;
import de.dhbw.backendTasks.party.DeletePartyByIdTask;
import de.dhbw.backendTasks.party.GetMyPartyList;
import de.dhbw.backendTasks.party.GetMyPartyListTask;
import de.dhbw.backendTasks.party.GetPartyList;
import de.dhbw.backendTasks.party.GetPartyListTask;
import de.dhbw.backendTasks.party.PostParty;
import de.dhbw.backendTasks.party.PostPartyTask;
import de.dhbw.backendTasks.user.RegisterUserTask;
import de.dhbw.backendTasks.userparty.CommitmentState;
import de.dhbw.backendTasks.userparty.CommitmentStateTask;
import de.dhbw.backendTasks.userparty.PartyRating;
import de.dhbw.backendTasks.userparty.PartyRatingTask;
import de.dhbw.exceptions.IllegalKeyException;
import de.dhbw.model.Location;
import de.dhbw.model.Party;
import de.dhbw.model.PartyDisplay;
import de.dhbw.model.Rating;
import de.dhbw.utils.SettingsUtil;

/**
 * Created by Flo on 02.11.2016.
 */

public class TestFragment extends Fragment implements View.OnClickListener, DeletePartyById, GetPartyList, PostParty, GetMyPartyList, CommitmentState, PartyRating {
    public TextView viewStatus;
    private Button buttonGet, buttonPost, buttonPut, buttonDelete, buttonSettings, buttonRegister;

    public TestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test, container, false);

        viewStatus = (TextView) rootView.findViewById(R.id.test_statusTextView);
        buttonGet = (Button) rootView.findViewById(R.id.main_button_get);
        buttonPost = (Button) rootView.findViewById(R.id.main_button_post);
        buttonPut = (Button) rootView.findViewById(R.id.main_button_put);
        buttonDelete = (Button) rootView.findViewById(R.id.main_button_delete);
        buttonSettings = (Button) rootView.findViewById(R.id.main_button_settings);
        buttonRegister = (Button) rootView.findViewById(R.id.main_button_register);

        buttonGet.setOnClickListener(this);
        buttonPost.setOnClickListener(this);
        buttonPut.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonSettings.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.main_button_register:

                new RegisterUserTask("flo","flo","theflo@vollbio.de");


                break;
            case R.id.main_button_settings:
                try {
                    viewStatus.setText(SettingsUtil.getInstance().getSetting("radius"));
                } catch (IllegalKeyException e) {
                    e.printStackTrace();
                }


                break;
            case R.id.main_button_get:
                new GetMyPartyListTask(this);



                break;
            case R.id.main_button_post:
                Party p = new Party();
                p.setPartyName("Geile Sause");
                p.setDescription("Bla");
                p.setMusicGenre(1);
                p.setPrice(2);
                p.setPartyDate("2017-11-06T14:06:36.823Z");
                p.setPartyType(2);

                Location l = new Location();
                l.setCountyName("Germany");
                l.setCityName("Horb am Neckar");
                l.setStreetName("Florianstraße");
                l.setHouseNumber("12");
                l.setZipcode("72160");
                l.setLatitude(0);
                l.setLongitude(0);

                p.setLocation(l);

                new PostPartyTask(this, new PartyDisplay(p));

                break;
            case R.id.main_button_put:
                new CommitmentStateTask(this,"bla",1);
                new PartyRatingTask(this,"bla",new Rating(1,1,-1,0));

                break;
            case R.id.main_button_delete:
                new DeletePartyByIdTask(this, "b0c8a258-4ad6-493a-57b2-08d40658d85e");
                break;
        }
    }

    @Override
    public void onSuccessDeletePartyById(boolean result) {
            if (result)
                viewStatus.setText("Löschen erfolgreich");
            else
                viewStatus.setText("Löschen nicht erfolgreich");
    }

    @Override
    public void onFailDeletePartyById(boolean result) {

    }

    @Override
    public void onSuccessGetPartyList(Party[] parties) {

    }

    @Override
    public void onFailGetPartyList(Party[] parties) {

    }

    @Override
    public void onSuccessPostParty(Party party) {
            viewStatus.setText(new Gson().toJson(party));
    }

    @Override
    public void onFailPostParty(PartyDisplay party) {

    }

    @Override
    public void onSuccessGetMyPartyList(Party[] parties) {

    }

    @Override
    public void onFailGetMyPartyList() {

    }

    @Override
    public void onSuccessCommitmentState() {

    }

    @Override
    public void onFailCommitmentState() {

    }

    @Override
    public void onSuccessPartyRating() {

    }

    @Override
    public void onFailPartyRating() {

    }
}
