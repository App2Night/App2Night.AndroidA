package de.dhbw.app2night;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import de.dhbw.model.User;

/**
 * Created by Flo on 31.10.2016.
 */
public class ProfileFragment extends Fragment {

    //Variablen
    EditText profile_name, profile_phone, profile_mail, profile_adress, profile_description;
    FloatingActionButton edit;
    View.OnClickListener save, change;


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Wird beim erstellen aufgerufen
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Wird beim erstellen der View aufgerufen
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        //Zuweisungen der EditTexts
        profile_name = (EditText) rootView.findViewById(R.id.profile_name_view);
        profile_phone =  (EditText) rootView.findViewById(R.id.profile_phone_view);
        profile_mail = (EditText) rootView.findViewById(R.id.profile_mail_view);
        profile_adress = (EditText) rootView.findViewById(R.id.profile_adress_view);
        profile_description = (EditText) rootView.findViewById(R.id.profile_description_view);


        //TODO: Name des Users in TextFeld anzeigen


        //Button Image Ändern und anderen OnClickListener zuweisen
         save = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edit.setImageResource(R.drawable.ic_settings_black_24dp);
                edit.setOnClickListener(change);
            }
        };

        //Button Image Ändern und anderen OnClickListener zuweisen
        change = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edit.setImageResource(R.drawable.ic_save);
                edit.setOnClickListener(save);
            }
        };

        edit = (FloatingActionButton) rootView.findViewById(R.id.profile_edit);
        edit.setOnClickListener(change);

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
}
