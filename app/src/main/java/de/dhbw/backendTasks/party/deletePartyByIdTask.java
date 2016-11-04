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
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 20.10.2016.
 */

public class DeletePartyByIdTask extends AsyncTask<String,Void,Boolean> implements ApiPartyTask {
    //Initialisert von PropertyUtil
    private static String url;
    MainActivity mainActivity;
    TestFragment testFragment;
    Activity activity;

    public void setUrl(String urlParm){
        url = urlParm;
    }

    public DeletePartyByIdTask(MainActivity mA, String id){
        mainActivity = mA;
        prepare(id, mA);
    }

    public DeletePartyByIdTask(TestFragment tF, String id){
        testFragment = tF;
        activity = tF.getActivity();
        prepare(id, MainActivity.getContext());
    }

    private  String buildDeleteUrl(String id){
        return url+"/id=" + id;
    }

    private void prepare(String id,Context c){
        String deleteUrl = buildDeleteUrl(id);
        PropertyUtil.getInstance().init(this);
        this.execute(deleteUrl);
    }

    @Override
    protected Boolean  doInBackground(String... params) {
        try {
            RestBackendCommunication rbc = new RestBackendCommunication();
            if (activity != null)
                return rbc.deleteRequest(params[0]);
        } catch (IOException e) {
            return false;
        } catch (BackendCommunicationException e) {
            return false;
        } catch (NetworkUnavailableException e) {
            return false;
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result){
        if (testFragment != null){
            if (result)
                testFragment.viewStatus.setText("Löschen erfolgreich");
            else
                testFragment.viewStatus.setText("Löschen nicht erfolgreich");
        }
    }
}
