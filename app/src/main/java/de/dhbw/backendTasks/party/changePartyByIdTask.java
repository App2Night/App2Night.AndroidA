package de.dhbw.backendTasks.party;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;

import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.app2night.MainActivity;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 20.10.2016.
 */

public class ChangePartyByIdTask extends AsyncTask<String, Void, Boolean> implements ApiPartyTask {
    //Initialisert von PropertyUtil
    private static String url;
    MainActivity mainActivity;

    public void setUrl(String urlParm){
        url = urlParm;
    }

    public ChangePartyByIdTask(MainActivity mA, String id , String jString){
        mainActivity = mA;
        prepare(id, jString, mA);
    }


    private  String buildPutUrl(String id){
        return url+"/id=" + id;
    }

    private void prepare(String id, String jString, Context c){
        String putUrl = buildPutUrl(id);
        PropertyUtil.getInstance().init(this,c);
        this.execute(putUrl, jString);
    }

    @Override
    protected Boolean  doInBackground(String... params) {
        try {
            RestBackendCommunication rbc = RestBackendCommunication.getInstance();
            if (mainActivity != null)
                return rbc.putRequest(params[0], params[1], mainActivity);
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
        //Verarbeitung bei Aufruf von MainApp
        if (mainActivity != null){
            if (result)
                mainActivity.viewStatus.setText("Put erfolgreich");
            else
                mainActivity.viewStatus.setText("Put nicht erfolgreich");
        }
    }
}
