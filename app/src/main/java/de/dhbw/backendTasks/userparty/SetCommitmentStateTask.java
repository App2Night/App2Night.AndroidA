package de.dhbw.backendTasks.userparty;


import android.os.AsyncTask;

import org.json.JSONObject;

import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.model.CommitmentState;
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 24.11.2016.
 */

public class SetCommitmentStateTask extends AsyncTask<Void,Void,Boolean> implements ApiUserPartyTask{

    //Initiallisiert von PropertyUtil
    private String url;

    private final SetCommitmentState setCommitmentState;
    private final CommitmentState commitmentState;
    private final String partyId;

    public SetCommitmentStateTask(SetCommitmentState setCommitmentState, String partyId, CommitmentState commitmentState){
        this.commitmentState = commitmentState;
        this.partyId = partyId;
        this.setCommitmentState=setCommitmentState;
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
        return url+"?id="+partyId;
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        try {
            JSONObject jObj = new JSONObject();
            jObj.put("eventCommitment",CommitmentState.toInt(commitmentState));
            return RestBackendCommunication.getInstance().putRequest(buildPutUrl(), jObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result)
            setCommitmentState.onSuccessCommitmentState(commitmentState);
        else
            setCommitmentState.onFailCommitmentState();

    }
}
