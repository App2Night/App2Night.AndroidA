package de.dhbw.backendTasks.party;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

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

public class ChangePartyByIdTask extends AsyncTask<String, Void, Boolean> implements ApiPartyTask{
    Activity activity;
    TestFragment testFragment;

    //Initialisert von PropertyUtil
    private static String url;

    public ChangePartyByIdTask(TestFragment tF, String id , String jString) {
        testFragment = tF;
        activity = tF.getActivity();
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
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result){
        if (testFragment != null){
            if (result)
                testFragment.viewStatus.setText("Put erfolgreich");
            else
                testFragment.viewStatus.setText("Put nicht erfolgreich");
        }
    }
}
