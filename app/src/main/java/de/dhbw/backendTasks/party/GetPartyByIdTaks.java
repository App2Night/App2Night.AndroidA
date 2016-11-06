package de.dhbw.backendTasks.party;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;

import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.app2night.MainActivity;
import de.dhbw.app2night.TestFragment;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;
import de.dhbw.model.Party;
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 20.10.2016.
 */

public class GetPartyByIdTaks extends AsyncTask<String, Void, String> implements ApiPartyTask{

    //Initialisert von PropertyUtil
    private static String url;
    Activity activity;
    GetPartyById fragment;

    public void setUrl(String urlParm){
        url = urlParm;
    }


    public GetPartyByIdTaks(GetPartyById fr, String id){
        fragment = fr;
        prepare(activity,id);
    }

    private void prepare(Context c, String id){
        PropertyUtil.getInstance().init(this);
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
            if(activity != null)
                return rbc.getRequest(params[0]);
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
        Party party = new Gson().fromJson(result, Party.class);
        fragment.onFinishGetPartyById(party);
    }
}
