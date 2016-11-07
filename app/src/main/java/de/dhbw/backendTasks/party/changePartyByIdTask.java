package de.dhbw.backendTasks.party;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;


import java.io.IOException;

import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.app2night.MainActivity;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;
import de.dhbw.exceptions.NoTokenFoundException;
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 20.10.2016.
 */

public class ChangePartyByIdTask extends AsyncTask<String, Void, Boolean> implements ApiPartyTask{
    Activity activity;
    ChangePartyById fragment;

    //Initialisert von PropertyUtil
    private static String url;

    public ChangePartyByIdTask(ChangePartyById fr, String id , String jString) {
        fragment = fr;
        prepare(id, jString, MainActivity.getContext());
    }
    public void setUrl(String urlParm){
        url = urlParm;
    }


    private  String buildPutUrl(String id){
        return url+"/id=" + id;
    }

    private void prepare(String id, String jString, Context c){
        String putUrl = buildPutUrl(id);
        PropertyUtil.getInstance().init(this);
        this.execute(putUrl, jString);
    }

    @Override
    protected Boolean  doInBackground(String... params) {
        try {
            RestBackendCommunication rbc = new RestBackendCommunication();
            if (activity != null)
                return rbc.putRequest(params[0], params[1]);
        } catch (IOException e) {
            return false;
        } catch (BackendCommunicationException e) {
            return false;
        } catch (NetworkUnavailableException e) {
            return false;
        } catch (NoTokenFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result){
        fragment.onFinischChangePartyById(result);
    }
}
