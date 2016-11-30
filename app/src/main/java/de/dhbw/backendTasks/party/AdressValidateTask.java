package de.dhbw.backendTasks.party;

import android.os.AsyncTask;

import java.io.IOException;

import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;
import de.dhbw.exceptions.NoTokenFoundException;
import de.dhbw.exceptions.RefreshTokenFailedException;
import de.dhbw.model.Location;
import de.dhbw.model.PartyDisplay;
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 20.11.2016.
 */

/**
 * Dieser AsyncTask dient der Validierung einer Adresse gegenüber dem Backend.
 * Der Task wird mit dem Konstruktor erzeugt und dann automatisch gestartet.
 */
public class AdressValidateTask extends AsyncTask<Void, Void, Boolean> implements ApiPartyTask {

    //Initialisert von PropertyUtil
    private String url;

    private final PartyDisplay partyDisplay;
    private final AdressValidate adressValidate;

    public AdressValidateTask(PartyDisplay partyDisplay, AdressValidate adressValidate){
        this.partyDisplay = partyDisplay;
        this.adressValidate = adressValidate;
        prepare();
    }

    private void prepare(){
        PropertyUtil.getInstance().init(this);
        this.execute();
    }

    @Override
    public void setUrl(String url) {
        this.url = url+"/validate";
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            Location location = new Location(partyDisplay);
            return RestBackendCommunication.getInstance().validateAdress(url,location);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RefreshTokenFailedException e) {
            e.printStackTrace();
        } catch (NoTokenFoundException e) {
            e.printStackTrace();
        } catch (BackendCommunicationException e) {
            e.printStackTrace();
        } catch (NetworkUnavailableException e) {
            e.printStackTrace();
        }
        return false;
    }



    @Override
    protected void onPostExecute(Boolean result){
        if(result)
            adressValidate.onSuccessAddressValidate(partyDisplay);
        else
            adressValidate.onFailAddressValidate(partyDisplay);

    }
}
