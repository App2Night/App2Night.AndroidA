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
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 22.11.2016.
 */

/**
 * Dieser AsyncTask dient dazu die Liste aller Partys zu erhalten, bei denen man enweder vorgemerkt ist oder teilnimmt.
 * Der Task wird mit dem Konstruktor erzeugt und dann automatisch gestartet.
 */
public class GetMyPartyListTask extends AsyncTask<Void, Void, String> implements ApiPartyTask {

    //Wird initialisiert durch PropertyUtil
    private String url;

    private final GetMyPartyList fragment;

    public GetMyPartyListTask(GetMyPartyList fragment){
        this.fragment = fragment;
        prepare();
    }

    private void prepare(){
        PropertyUtil.getInstance().init(this);
        this.execute();
    }

    @Override
    public void setUrl(String url) {
        this.url = url+"/myParties";
    }


    @Override
    protected String doInBackground(Void... params) {
        try {
            return RestBackendCommunication.getInstance().getRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BackendCommunicationException e) {
            e.printStackTrace();
        } catch (NetworkUnavailableException e) {
            e.printStackTrace();
        } catch (RefreshTokenFailedException e) {
            e.printStackTrace();
        } catch (NoTokenFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result){
        if (result != null){
            Party parties = new Gson().fromJson(result, Party.class);
            fragment.onSuccessGetMyPartyList(parties);
        }


    }
}
