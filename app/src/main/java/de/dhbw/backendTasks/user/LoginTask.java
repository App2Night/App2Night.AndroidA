package de.dhbw.backendTasks.user;

import android.content.Intent;
import android.os.AsyncTask;

import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.app2night.LoginActivity;
import de.dhbw.app2night.MainActivity;
import de.dhbw.app2night.R;

/**
 * Created by Tobias Berner on 15.11.2016.
 */


public class LoginTask extends AsyncTask<Void, Void, Boolean> {

    private final String mEmail;
    private final String mPassword;
    private final LoginActivity loginActivity;

    public LoginTask(String email, String password, LoginActivity la) {
        mEmail = email;
        mPassword = password;
        loginActivity=la;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return RestBackendCommunication.getInstance().login(mEmail,mPassword,loginActivity);
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        loginActivity.setmAuthTask(null);
        loginActivity.showProgress(false);

        if (success) {
            Intent mainActivityIntent = new Intent(loginActivity, MainActivity.class);
            loginActivity.finish();
            loginActivity.startActivity(mainActivityIntent);

        } else {
            loginActivity.getmPasswordView().setError(loginActivity.getString(R.string.error_incorrect_password));
            loginActivity.getmPasswordView().requestFocus();
        }
    }

    @Override
    protected void onCancelled() {
        loginActivity.setmAuthTask(null);
        loginActivity.showProgress(false);
    }
}





