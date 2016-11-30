package de.dhbw.backendTasks.userparty;


import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;
import de.dhbw.exceptions.NoTokenFoundException;
import de.dhbw.exceptions.RefreshTokenFailedException;
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 24.11.2016.
 */

public class CommitmentStateTask extends AsyncTask<Void,Void,Boolean> implements ApiUserPartyTask{

    //Initiallisiert von PropertyUtil
    private String url;

    private final CommitmentState commitmentState;
    private final int commitmentStateValue;
    private final String partyId;

    public CommitmentStateTask(CommitmentState commitmentState, String partyId, int commitmentStateValue){
        this.commitmentStateValue = commitmentStateValue;
        this.partyId = "81a93271-591b-4b72-1bc5-08d4193d1de2";//partyId;
        this.commitmentState=commitmentState;
        prepare();
    }

    private void prepare() {
        PropertyUtil.getInstance().init(this);
        this.execute();
    }

    @Override
    public void setUrl(String url) {
        this.url = url + "/commitmentState";
    }

    private String buildPutUrl(){
        return url+"/id="+partyId;
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        try {
            JSONObject jObj = new JSONObject();
            jObj.put("eventCommitment",commitmentStateValue);
            return RestBackendCommunication.getInstance().putRequest(buildPutUrl(), jObj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BackendCommunicationException e) {
            e.printStackTrace();
        } catch (RefreshTokenFailedException e) {
            e.printStackTrace();
        } catch (NetworkUnavailableException e) {
            e.printStackTrace();
        } catch (NoTokenFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result)
            commitmentState.onSuccessCommitmentState();
        else
            commitmentState.onFailCommitmentState();

    }
}
