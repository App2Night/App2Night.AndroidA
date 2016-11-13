package de.dhbw.backendTasks.party;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;

import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;
import de.dhbw.exceptions.NoTokenFoundException;
import de.dhbw.exceptions.RefreshTokenFailedException;
import de.dhbw.model.Party;
import de.dhbw.model.PartyDisplay;
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 20.10.2016.
 */

public class PostPartyTask extends AsyncTask<String,Void,String> implements ApiPartyTask {
    //Initialisert von PropertyUtil
    private String url;
    PostParty fragment;

    public void setUrl(String urlParm){
        url = urlParm;
    }

    public PostPartyTask(PostParty fr, PartyDisplay party){
        fragment = fr;
        prepare(party);
    }

    private void prepare(PartyDisplay party){
        PropertyUtil.getInstance().init(this);
        String jString = new Gson().toJson(party);
        this.execute(url,jString);
    }

    @Override
    protected String doInBackground(String... params) {
        try{
                //Post
                String id = RestBackendCommunication.getInstance().postRequest(params[0],params[1]);
                id=id.substring(1,id.length()-1);
                //Get zum holen aller Informationen und speichern in Allgemeinem Modell
                return RestBackendCommunication.getInstance().getRequest(url+"/id="+id);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        } catch (BackendCommunicationException e) {
            e.printStackTrace();
        } catch (NetworkUnavailableException e) {
            e.printStackTrace();
        } catch (NoTokenFoundException e) {
            e.printStackTrace();
        } catch (RefreshTokenFailedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result){
        if (result != null){
            Party[] party = new Gson().fromJson(result, Party[].class);
            fragment.onFinishPostParty(party[0]);
    }
    }

}
