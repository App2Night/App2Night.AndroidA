package de.dhbw.app2night;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.dhbw.backendTasks.party.ChangePartyByIdTask;
import de.dhbw.backendTasks.party.DeletePartyByIdTask;
import de.dhbw.backendTasks.party.GetPartyListTask;
import de.dhbw.backendTasks.party.PostPartyTask;
import de.dhbw.exceptions.IllegalKeyException;
import de.dhbw.utils.SettingsAdministration;

/**
 * Created by Flo on 02.11.2016.
 */

public class TestFragment extends Fragment implements View.OnClickListener{
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
                    /*Thread.sleep(1000);
                    putSettingString("test","Test erfolgreich!",this);
                    viewStatus.setText(getSettingString("test",this));*/
                } catch (IllegalKeyException e) {
                    e.printStackTrace();
                }


                break;
            case R.id.main_button_get:
                new GetPartyListTask(this);
                break;
            case R.id.main_button_post:
                new PostPartyTask(this,"{\"partyName\": \"string\", "+
                        "\"partyDate\": \"2016-10-24T17:58:34.538Z\", "+
                        " \"musicGenre\": 0," +
                        " \"location\": {" +
                        "\"countryName\": \"string\","+
                        "\"cityName\": \"string\","+
                        "\"streetName\": \"string\","+
                        "\"houseNumber\": 0,"+
                        "\"houseNumberAdditional\": \"string\","+
                        "\"zipcode\": 0,"+
                        " \"latitude\": 0,"+
                        " \"longitude\": 0}," +
                        "\"partyType\": 0," +
                        "\"description\": \"string\"}");
                break;
            case R.id.main_button_put:
                new ChangePartyByIdTask(this,"2acec5b0-37e1-4c88-4692-08d3fc41e1f5","{\"partyName\": \"string\", "+
                        "\"partyDate\": \"2016-10-24T17:58:34.538Z\", "+
                        " \"musicGenre\": 0," +
                        " \"location\": {" +
                        "\"countryName\": \"string\","+
                        "\"cityName\": \"string\","+
                        "\"streetName\": \"string\","+
                        "\"houseNumber\": 0,"+
                        "\"houseNumberAdditional\": \"string\","+
                        "\"zipcode\": 0,"+
                        " \"latitude\": 0,"+
                        " \"longitude\": 0}," +
                        "\"partyType\": 0," +
                        "\"description\": \"string\"}");
                break;
            case R.id.main_button_delete:
                new DeletePartyByIdTask(this, "2acec5b0-37e1-4c88-4692-08d3fc41e1f5");
                break;
        }
    }
}
