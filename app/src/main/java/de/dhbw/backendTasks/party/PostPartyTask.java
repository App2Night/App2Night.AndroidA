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

/**
 * Dieser AsyncTask dient dazu eine Party im Backend anzulegen.
 * Der Task wird mit dem Konstruktor erzeugt und dann automatisch gestartet.
 */
public class PostPartyTask extends AsyncTask<Void,Void,Party> implements ApiPartyTask {
    //Initialisert von PropertyUtil
    private String url;

    private final PostParty fragment;
    private final PartyDisplay displayParty;

    public void setUrl(String urlParm){
        url = urlParm;
}

    public PostPartyTask(PostParty fragment, PartyDisplay displayParty){
        this.fragment = fragment;
        this.displayParty=displayParty;
        prepare();
    }

    private void prepare(){
        PropertyUtil.getInstance().init(this);
        this.execute();
    }

    @Override
    protected Party doInBackground(Void... params) {
        try{
                String jString = new Gson().toJson(displayParty);
                //Post
                String id = RestBackendCommunication.getInstance().postRequest(url,jString);
                id=id.substring(1,id.length()-1);
                //Get zum holen aller Informationen und speichern in Allgemeinem Modell
                String result =  RestBackendCommunication.getInstance().getRequest(url+"/id="+id);
                Party party = new Gson().fromJson(result, Party.class);
                return party;
        } catch (IOException e) {
            e.printStackTrace();
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
    protected void onPostExecute(Party result ){
        if (result != null){
            fragment.onSuccessPostParty(result);
    }
        else
            fragment.onFailPostParty(displayParty);
    }

}
