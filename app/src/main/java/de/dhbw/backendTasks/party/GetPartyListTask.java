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
 * Created by Tobias Berner on 21.10.2016.
 */

public class GetPartyListTask extends AsyncTask<Void, Void, String> implements ApiPartyTask {

    //Initialisert von PropertyUtil
    private String url;
    private final GetPartyList fragment;


    public void setUrl(String urlParm) {
        url = urlParm;
    }

    public GetPartyListTask(GetPartyList fragment) {
        this.fragment = fragment;
        prepare();
    }

    private void prepare() {
        PropertyUtil.getInstance().init(this);
        this.execute();
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
                return RestBackendCommunication.getInstance().getRequest(url);
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
    protected void onPostExecute(String result) {
        if (result != null) {
            Party[] parties = new Gson().fromJson(result, Party[].class);
            fragment.onFinishGetPartyList(parties);
        }
    }
}

