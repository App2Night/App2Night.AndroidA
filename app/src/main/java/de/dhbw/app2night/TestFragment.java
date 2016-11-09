package de.dhbw.app2night;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.dhbw.backendTasks.party.DeletePartyById;
import de.dhbw.backendTasks.party.DeletePartyByIdTask;
import de.dhbw.backendTasks.party.GetPartyList;
import de.dhbw.backendTasks.party.GetPartyListTask;
import de.dhbw.backendTasks.party.PostParty;
import de.dhbw.backendTasks.party.PostPartyTask;
import de.dhbw.exceptions.IllegalKeyException;
import de.dhbw.model.Location;
import de.dhbw.model.Party;
import de.dhbw.utils.SettingsAdministration;

/**
 * Created by Flo on 02.11.2016.
 */

public class TestFragment extends Fragment implements View.OnClickListener, DeletePartyById, GetPartyList, PostParty{
    public TextView viewStatus;
    private Button buttonGet, buttonPost, buttonPut, buttonDelete, buttonSettings;

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

        buttonGet.setOnClickListener(this);
        buttonPost.setOnClickListener(this);
        buttonPut.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonSettings.setOnClickListener(this);

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
            case R.id.main_button_settings:
                try {
                    viewStatus.setText(SettingsAdministration.getInstance().getSetting("radius", this.getActivity()));
                } catch (IllegalKeyException e) {
                    e.printStackTrace();
                }


                break;
            case R.id.main_button_get:
                new GetPartyListTask(this);



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
                l.setCountyName("Musterland");
                l.setCityName("stadt");
                l.setStreetName("str");
                l.setHouseNumber("5");
                l.setHouseNumberAdditional("b");
                l.setZipcode("50555");
                l.setLatitude(0);
                l.setLongitude(0);

                p.setLocation(l);

                new PostPartyTask(this, p);

                break;
            case R.id.main_button_put:

                break;
            case R.id.main_button_delete:
                new DeletePartyByIdTask(this, "b0c8a258-4ad6-493a-57b2-08d40658d85e");
                break;
        }
    }

    @Override
    public void onFinishDeletePartyById(boolean result) {

    }

    @Override
    public void onFinishGetPartyList(Party[] parties) {

    }

    @Override
    public void onFinishPostParty(Party party) {

    }
}
