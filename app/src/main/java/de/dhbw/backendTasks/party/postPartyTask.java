package de.dhbw.backendTasks.party;

import android.os.AsyncTask;

import java.io.IOException;

import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.app2night.AddEventActivity;
import de.dhbw.app2night.MainActivity;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;

/**
 * Created by Tobias Berner on 20.10.2016.
 */

public class postPartyTask extends AsyncTask<String,Void,String> {

    final String url="http://app2nightapi.azurewebsites.net/api/Party";
    AddEventActivity addEventActivity;
    MainActivity mainActivity;

    public postPartyTask(AddEventActivity aEA, String jString){
        addEventActivity = aEA;
        prepare(jString);
    }

    public postPartyTask(MainActivity mA, String jString){
        mainActivity = mA;
        prepare(jString);
    }

    private void prepare(String jString){
        this.execute(url,jString);
    }

    @Override
    protected String doInBackground(String... params) {
        try{
            RestBackendCommunication rbc = new RestBackendCommunication();
            if(addEventActivity != null)
                return rbc.postRequest(params[0],params[1], addEventActivity);
            else if(mainActivity != null)
                return rbc.postRequest(params[0],params[1],mainActivity);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        } catch (BackendCommunicationException e) {
            e.printStackTrace();
        } catch (NetworkUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result){
        //Verarbeitung bei Aufruf von MainApp
        if (addEventActivity != null){

            addEventActivity.view.setText(result);
        }
        else if (mainActivity != null){
            mainActivity.view.setText(result);
        }
    }
}
