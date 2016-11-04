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
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 20.10.2016.
 */

public class PostPartyTask extends AsyncTask<String,Void,String> implements ApiPartyTask {
    //Initialisert von PropertyUtil
    private static String url;
    TestFragment testFragment;
    Activity activity;


    public void setUrl(String urlParm){
        url = urlParm;
    }

    public PostPartyTask(TestFragment tF, String jString){
        testFragment = tF;
        activity = tF.getActivity();
        prepare(jString);
    }

    private void prepare(String jString){
        PropertyUtil.getInstance().init(this);
        this.execute(url,jString);
    }

    @Override
    protected String doInBackground(String... params) {
        try{
            RestBackendCommunication rbc = new RestBackendCommunication();
            if(activity != null)
                return rbc.postRequest(params[0],params[1]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        } catch (BackendCommunicationException e) {
            e.printStackTrace();
        } catch (NetworkUnavailableException e) {
            e.printStackTrace();
        } catch (NoTokenFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result){
        if (testFragment != null){
            testFragment.viewStatus.setText(result);
        }
    }
}
