package de.dhbw.backendTasks.party;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;

import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.app2night.MainActivity;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;

/**
 * Created by Tobias Berner on 20.10.2016.
 */

public class GetPartyListTask extends AsyncTask<String, Void, String> {


    final String url="http://app2nightapi.azurewebsites.net/api/Party";

    MainActivity mainActivity;


    public GetPartyListTask(MainActivity mA){
        mainActivity = mA;
        this.execute(url);

    }

    private void prepare(){
        this.execute(url);
    }

    @Override
    protected String doInBackground(String... params) {
        try{
            RestBackendCommunication rbc = new RestBackendCommunication();
            if(mainActivity != null)
                return rbc.getRequest(params[0],mainActivity);
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
        if (mainActivity != null){
                mainActivity.view.setText(result);
        }
    }
}
