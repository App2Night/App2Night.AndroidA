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

public class GetPartyByID extends AsyncTask<String, Void, String> implements ApiPartyTask{

    //Initialisert von PropertyUtil
    private static String url;
    MainActivity mainActivity;

    public void setUrl(String urlParm){
        url = urlParm;
    }


    public GetPartyByID(MainActivity mA, String id){
        mainActivity = mA;
        prepare(mA,id);
    }

    private void prepare(Context c, String id){
        PropertyUtil.getInstance().init(this,c);
        String urlId = buildGetUrl(id);
        this.execute(urlId);
    }

    private  String buildGetUrl(String id){
        return url+"/id=" + id;
    }

    @Override
    protected String doInBackground(String... params) {
        try{
            RestBackendCommunication rbc = RestBackendCommunication.getInstance();
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
                mainActivity.viewStatus.setText(result);
        }
    }
}
