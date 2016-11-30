package de.dhbw.backendTasks.party;
import android.os.AsyncTask;
import com.google.gson.Gson;
import java.io.IOException;
import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;
import de.dhbw.exceptions.NoTokenFoundException;
import de.dhbw.exceptions.RefreshTokenFailedException;
import de.dhbw.model.PartyDisplay;
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 20.10.2016.
 */


/**
 * Dieser AsyncTask dient dazu eine bereits bestehende Party zu verändern.
 * Der Task wird mit dem Konstruktor erzeugt und dann automatisch gestartet.
 */
public class ChangePartyByIdTask extends AsyncTask<Void, Void, Boolean> implements ApiPartyTask{


    //Initialisert von PropertyUtil
    private String url;

    private final ChangePartyById fragment;
    private final PartyDisplay partyDisplay;

    public ChangePartyByIdTask(ChangePartyById fragment, PartyDisplay partyDisplay) {
        this.fragment = fragment;
        this.partyDisplay = partyDisplay;

        prepare();
    }
    public void setUrl(String urlParm){
        url = urlParm;
    }


    private  String buildPutUrl(String id){
        return url+"/id=" + id;
    }

    private void prepare(){

        PropertyUtil.getInstance().init(this);
        this.execute();
    }

    @Override
    protected Boolean  doInBackground(Void... params) {
        try {

                String id = partyDisplay.getPartyId();
                partyDisplay.setPartyId(null);
                String jString = new Gson().toJson(partyDisplay);
                return RestBackendCommunication.getInstance().putRequest(buildPutUrl(id), jString);
        } catch (IOException e) {
            return false;
        } catch (BackendCommunicationException e) {
            return false;
        } catch (NetworkUnavailableException e) {
            return false;
        } catch (NoTokenFoundException e) {
            e.printStackTrace();
        } catch (RefreshTokenFailedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result){
        fragment.onSuccessChangePartyById();
    }
}
