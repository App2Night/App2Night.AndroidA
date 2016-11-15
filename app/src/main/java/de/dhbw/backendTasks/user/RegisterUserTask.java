package de.dhbw.backendTasks.user;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;

import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.exceptions.BackendCommunicationException;

/**
 * Created by Tobias Berner on 14.11.2016.
 */

public class RegisterUserTask extends AsyncTask<String, Void, Boolean> {

    Context context;

    public RegisterUserTask(String username, String password, String email, Context c){
        context = c;
        this.execute(username,password,email);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            return RestBackendCommunication.getInstance().register(params[0],params[1],params[2],context);
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
