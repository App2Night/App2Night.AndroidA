package de.dhbw.app2night;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import de.dhbw.backendTasks.user.RegisterUserTask;

/**
 * Created by Flo on 27.10.2016.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener{
    public final static String ARG_USERNAME = "arg_username";
    EditText editTextUserName, editTextEmail, editTextPassword1, editTextPassword2;
    Button registerButton;
    View rootView;
    private String userName;
    private String email;
    private String pw ;
    private String pwRepeat;
    private boolean korrekteEingabe;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_register, container, false);
        initializeViews();

        // Inflate the layout for this fragment
        return rootView;
    }

    private void initializeViews() {
        editTextUserName = (EditText) rootView.findViewById(R.id.register_input_user_name);
        editTextUserName.setText(getArguments().getString("arg_username"));

        editTextEmail = (EditText) rootView.findViewById(R.id.register_input_email);
        editTextPassword1 = (EditText) rootView.findViewById(R.id.register_input_password);
        editTextPassword2 = (EditText) rootView.findViewById(R.id.register_input_passwort_repeat);

        registerButton = (Button) rootView.findViewById(R.id.register_button_ok);
        registerButton.setOnClickListener(this);
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
    public void onClick(View view) {
        userName = editTextUserName.getText().toString();
        email = editTextEmail.getText().toString();
        pw = editTextPassword1.getText().toString();
        pwRepeat = editTextPassword2.getText().toString();
        korrekteEingabe = true;

        editTextUserName.setError(null);
        editTextEmail.setError(null);
        editTextPassword1.setError(null);
        editTextPassword2.setError(null);

        if(userName.equals("")){
            korrekteEingabe=false;
            editTextUserName.setError("Benutzername darf nicht leer sein");
        }
        if(!pwRepeat.equals(pw)) {
            korrekteEingabe = false;
            editTextPassword2.setText(null);
            editTextPassword2.setError("Eingegebene Passwörter stimmen nicht überein, bitte Passwort korrekt wiederholen");
        }
        if(pw.length()>4){
            korrekteEingabe = false;
            editTextPassword1.setError("Das Passwort muss mindestens 4 Stellen besitzen");
        }
        if(email.equals("")){
            korrekteEingabe = false;
            editTextEmail.setError("Es muss eine EMail Adresse eingegeben werden, an die der Registierungslink versendet werden kann");
        }

        if(korrekteEingabe){
            new RegisterUserTask(userName, pw, email);
        }
    }
}
