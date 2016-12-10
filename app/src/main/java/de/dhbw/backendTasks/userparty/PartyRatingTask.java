package de.dhbw.backendTasks.userparty;
import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.backendTasks.party.ApiPartyTask;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;
import de.dhbw.exceptions.NoTokenFoundException;
import de.dhbw.exceptions.RefreshTokenFailedException;
import de.dhbw.model.Rating;
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 24.11.2016.
 */

public class PartyRatingTask extends AsyncTask<Void,Void,Boolean> implements ApiUserPartyTask {
    //Initiallisiert von PropertyUtil
    private String url;

    private final PartyRating partyRating;
    private final Rating rating;
    private final String partyId;

    public PartyRatingTask(PartyRating partyRating, String partyId, Rating rating){
        this.rating = rating;
        this.partyId = partyId;
        this.partyRating = partyRating;
        prepare();
    }

    private void prepare() {
        PropertyUtil.getInstance().init(this);
        this.execute();
    }

    @Override
    public void setUrl(String url) {
        this.url = url + "/partyRating";
    }

    private String buildPutUrl(){
        return url+"?id="+partyId;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
       try{
            return RestBackendCommunication.getInstance().putRequest(buildPutUrl(), new Gson().toJson(rating));
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result)
            partyRating.onSuccessPartyRating();
        else
            partyRating.onFailPartyRating();

    }
}
