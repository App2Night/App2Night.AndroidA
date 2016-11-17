package de.dhbw.backendTasks.user;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;

import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.exceptions.BackendCommunicationException;

/**
 * Created by Tobias Berner on 14.11.2016.
 */

public class RegisterUserTask extends AsyncTask<Void, Void, Boolean> {

    private final Context context;
    private final String username;
    private final String password;
    private final String email;


    public RegisterUserTask(String username, String password, String email, Context context){
        this.username = username;
        this.password = password;
        this.email = email;
        this.context = context;

        this.execute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            return RestBackendCommunication.getInstance().register(username,password,email,context);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BackendCommunicationException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    protected void onPostExecute(Boolean result){


    }


}
