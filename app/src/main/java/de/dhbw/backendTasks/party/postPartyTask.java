package de.dhbw.backendTasks.party;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;

import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.app2night.AddEventActivity;
import de.dhbw.app2night.MainActivity;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 20.10.2016.
 */

public class PostPartyTask extends AsyncTask<String,Void,String> implements ApiPartyTask {
    //Initialisert von PropertyUtil
    private static String url;
    AddEventActivity addEventActivity;
    MainActivity mainActivity;

    public void setUrl(String urlParm){
        url = urlParm;
    }

    public PostPartyTask(AddEventActivity aEA, String jString){
        addEventActivity = aEA;
        prepare(jString, aEA);
    }

    public PostPartyTask(MainActivity mA, String jString){
        mainActivity = mA;
        prepare(jString,mA);
    }

    private void prepare(String jString, Context c){
        PropertyUtil.getInstance().init(this,c);
        this.execute(url,jString);
    }

    @Override
    protected String doInBackground(String... params) {
        try{
            RestBackendCommunication rbc = RestBackendCommunication.getInstance();
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
            mainActivity.viewStatus.setText(result);
        }
    }
}
