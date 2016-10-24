package de.dhbw.backendTasks.party;

import android.os.AsyncTask;

import java.io.IOException;

import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.app2night.MainActivity;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;

/**
 * Created by Tobias Berner on 20.10.2016.
 */

public class changePartyByIdTask extends AsyncTask<String, Void, Boolean> {

    final String url="http://app2nightapi.azurewebsites.net/api/Party";

    MainActivity mainActivity;

    public changePartyByIdTask(MainActivity mA, String id , String jString){
        mainActivity = mA;
        prepare(id, jString);
    }


    private  String buildPutUrl(String id){
        return url+"/id=" + id;
    }

    private void prepare(String id, String jString){
        String putUrl = buildPutUrl(id);
        this.execute(putUrl, jString);
    }

    @Override
    protected Boolean  doInBackground(String... params) {
        try {
            RestBackendCommunication rbc = new RestBackendCommunication();
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
                mainActivity.view.setText("Put erfolgreich");
            else
                mainActivity.view.setText("Put nicht erfolgreich");
        }
    }
}
