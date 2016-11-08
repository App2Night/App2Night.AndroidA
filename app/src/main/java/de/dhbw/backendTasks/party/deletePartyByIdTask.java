package de.dhbw.backendTasks.party;

import android.content.Context;
import android.app.Activity;
import android.os.AsyncTask;

import java.io.IOException;

import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.app2night.MainActivity;
import de.dhbw.app2night.TestFragment;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;
import de.dhbw.exceptions.NoTokenFoundException;
import de.dhbw.exceptions.RefreshTokenFailedException;
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 20.10.2016.
 */

public class DeletePartyByIdTask extends AsyncTask<String,Void,Boolean> implements ApiPartyTask {

    //Initialisert von PropertyUtil
    private static String url;
    DeletePartyById fragment;

    public void setUrl(String urlParm){
        url = urlParm;
    }


    public DeletePartyByIdTask(DeletePartyById fr, String id){
        fragment = fr;
        prepare(id);
    }

    private  String buildDeleteUrl(String id){
        return url+"/id=" + id;
    }

    private void prepare(String id){
        PropertyUtil.getInstance().init(this);
        String deleteUrl = buildDeleteUrl(id);
        this.execute(deleteUrl);
    }

    @Override
    protected Boolean  doInBackground(String... params) {
        try {
            RestBackendCommunication rbc = new RestBackendCommunication();
                return rbc.deleteRequest(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
          } catch (BackendCommunicationException e) {
            e.printStackTrace();
            return false;
        } catch (NetworkUnavailableException e) {
            e.printStackTrace();
            return false;
        } catch (NoTokenFoundException e) {
            e.printStackTrace();
            return false;
        } catch (RefreshTokenFailedException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result){
        fragment.onFinishDeletePartyById(result);
    }
}
