package de.dhbw.app2night;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import de.dhbw.backendTasks.user.Register;
import de.dhbw.backendTasks.user.RegisterUserTask;
import de.dhbw.utils.CheckUserInputRegister;

/**
 * Created by Flo on 27.10.2016.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener, Register {
    OnRegisterButtonClickListener mCallback;

    public interface OnRegisterButtonClickListener {
        void onInputFragmentRegister(String userName);
    }

    public final static String ARG_USERNAME = "arg_username";
    EditText editTextUserName, editTextEmail, editTextPassword1, editTextPassword2;
    TextInputLayout tilUserName, tilEmail, tilPassword1, tilPassword2;
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

        try {
            mCallback = (OnRegisterButtonClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnRegisterButtonListener");
        }

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
        editTextPassword2 = (EditText) rootView.findViewById(R.id.register_input_password_repeat);

        tilUserName = (TextInputLayout) rootView.findViewById(R.id.register_input_layout_user_name);
        tilEmail = (TextInputLayout) rootView.findViewById(R.id.register_input_layout_email);
        tilPassword1 = (TextInputLayout) rootView.findViewById(R.id.register_input_layout_password);
        tilPassword2 = (TextInputLayout) rootView.findViewById(R.id.register_input_layout_password_repeat);

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
        getEditTextInputs();

        korrekteEingabe = true;

        resetErrorFlags();


        if(!pwRepeat.equals(pw)) {
            korrekteEingabe = false;
            editTextPassword2.setText(null);
            editTextPassword2.setError("Eingegebene Passwörter stimmen nicht überein, bitte Passwort korrekt wiederholen.");
            tilPassword2.setError("Passwort wiederholen");
        }
        if(!CheckUserInputRegister.getInstance().acceptPassword(pw)){
            korrekteEingabe = false;
            editTextPassword1.setError("Das Passwort muss zwischen 5 und 30 Stellen haben.");
            tilPassword1.setError("Passwort");
        }
        if(!CheckUserInputRegister.getInstance().acceptEmail(email)){
            korrekteEingabe = false;
            editTextEmail.setError("Keine gültige Email eingegeben.");
            tilEmail.setError("E-Mail");
        }
        if(!CheckUserInputRegister.getInstance().acceptNickname(userName)){
            korrekteEingabe=false;
            editTextUserName.setError("Eingegebener Benutzername wird nicht akzeptiert.");
            tilUserName.setError("Benutzername");
        }


        if(korrekteEingabe){
            new RegisterUserTask(userName, pw, email);
            mCallback.onInputFragmentRegister(userName);
        }
    }

    private void getEditTextInputs() {
        userName = editTextUserName.getText().toString();
        email = editTextEmail.getText().toString();
        pw = editTextPassword1.getText().toString();
        pwRepeat = editTextPassword2.getText().toString();
    }

    private void resetErrorFlags() {
        editTextUserName.setError(null);
        editTextEmail.setError(null);
        editTextPassword1.setError(null);
        editTextPassword2.setError(null);

        tilUserName.setError(null);
        tilEmail.setError(null);
        tilPassword1.setError(null);
        tilPassword2.setError(null);
    }

    @Override
    public void onSuccessLogin() {

    }

    @Override
    public void onFailLogin() {

    }

    @Override
    public void onCancelLogin() {

    }
}
