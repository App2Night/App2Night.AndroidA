package de.dhbw.backendTasks.party;

import android.os.AsyncTask;

import java.io.IOException;

import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;
import de.dhbw.exceptions.NoTokenFoundException;
import de.dhbw.exceptions.RefreshTokenFailedException;
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 20.10.2016.
 */



/**
 * Dieser AsyncTask dient dazu eine bestehende Party zu löschen.
 * Der Task wird mit dem Konstruktor erzeugt und dann automatisch gestartet.
 */
public class DeletePartyByIdTask extends AsyncTask<Void,Void,Boolean> implements ApiPartyTask {

    //Initialisert von PropertyUtil
    private String url;

    private final DeletePartyById fragment;
    private final String id;

    public DeletePartyByIdTask(DeletePartyById fragment, String id){
        this.fragment = fragment;
        this.id = id;
        prepare();
    }

    private void prepare(){
        PropertyUtil.getInstance().init(this);
        this.execute();
    }

    public void setUrl(String urlParm){
        url = urlParm;
    }

    private  String buildDeleteUrl(){
        return url+"?id=" + id;
    }


    @Override
    protected Boolean  doInBackground(Void... params) {
        try {
            return RestBackendCommunication.getInstance().deleteRequest(buildDeleteUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result){
        fragment.onSuccessDeletePartyById(result);
    }
}
