package de.dhbw.backendTasks.user;

import android.os.AsyncTask;

import de.dhbw.BackEndCommunication.RestBackendCommunication;

/**
 * Created by Tobias Berner on 15.11.2016.
 */

/**
 * Dieser AsyncTask dient dazu, einen Nutzer einzuloggen.
 * Der Task wird mit dem Konstruktor erzeugt und dann automatisch gestartet.
 */
public class LoginTask extends AsyncTask<Void, Void, Boolean> {

    private final String mEmail;
    private final String mPassword;
    private final Login fragment;

    public LoginTask(String email, String password, Login fragment) {
        mEmail = email;
        mPassword = password;
        this.fragment = fragment;
        this.execute();
    }


    @Override
    protected Boolean doInBackground(Void... params) {
        return RestBackendCommunication.getInstance().login(mEmail,mPassword);
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        if (success) {
            fragment.onSuccessLogin();
        } else {
            fragment.onFailLogin();
        }
    }

    @Override
    protected void onCancelled() {
        fragment.onCancelLogin();
    }
}





