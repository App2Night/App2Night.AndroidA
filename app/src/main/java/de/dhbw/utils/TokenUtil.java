package de.dhbw.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;
import de.dhbw.exceptions.NoTokenFoundException;
import de.dhbw.exceptions.RefreshTokenFailedException;

/**
 * Created by Tobias Berner on 03.11.2016.
 */

public class TokenUtil {

    private static TokenUtil tokenUtil = null;
    private TokenUtil(){
        PropertyUtil.getInstance().init(this);
    }
    public static TokenUtil getInstance(){
        if(tokenUtil == null)
            tokenUtil = new TokenUtil();
        return tokenUtil;
    }

    private String sharedPreferencesToken;

    public void setSharedPreferencesName(String sharedPreferences) {
        this.sharedPreferencesToken = sharedPreferences;
    }

    /**
     * Speichert die Antwort der GetToken Anfrage. Zusätzlich wird in JSON ein Element refresh eingefügt, das anzeigt wann ein neuer Refresh durchgeführt werden sollte
     *
     * @param eingabe Serverantwort nach GetToken
     */
    public void saveTokenAwnser(String eingabe){
        String toSave;
        try {
            JSONObject jObj = new JSONObject (eingabe);
            int duration = jObj.getInt("expires_in");
            //Extra Tupel in JSON, welches beim Abfragen der Authentifikation zu einem Refresh führt) (1000 Karenz /Minuten in  Millisekunden umgerechnet)
            jObj.put("refresh", System.currentTimeMillis()+duration* 60000 -1000);
            toSave = jObj.toString();
        } catch (JSONException e) {
            //Fals Fehler Auftritt, kann Eingabe JSON gespeichert werden
           toSave = eingabe;
        }
        SharedPreferences sp = ContextManager.getInstance().getContext().getSharedPreferences(sharedPreferencesToken, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("tokenjson",toSave);
        editor.commit();
    }

    /**
     * Liest den Refresh TokenUtil aus dem Speicher.
     *
     * @return refresh TokenUtil
     * @throws NoTokenFoundException - kein gespeichertes TokenUtil wird gefunden
     */
    public String getRefreshToken() throws NoTokenFoundException {
        SharedPreferences sp =ContextManager.getInstance().getContext().getSharedPreferences(sharedPreferencesToken, Context.MODE_PRIVATE);
        String jString = sp.getString("tokenjson",null);
        if (jString == null)
            throw new NoTokenFoundException();
        try {
            JSONObject jObj = new JSONObject(jString);
            return jObj.getString("refresh_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        throw new NoTokenFoundException();
    }

    /**
     * Gibt die Authorization für eine Serveranfrage zurück
     *
     * @return  Authorization String
     * @throws NoTokenFoundException - Wenn kein früher gespeichertes TokenUtil gefunden wird
     * @throws RefreshTokenFailedException - Wenn das erneuern des Tokens fehlschlägt
     */
    public String getAuthorization() throws NoTokenFoundException, RefreshTokenFailedException {
        SharedPreferences sp = ContextManager.getInstance().getContext().getSharedPreferences(sharedPreferencesToken, Context.MODE_PRIVATE);
        String jString = sp.getString("tokenjson",null);
        if (jString == null)
            throw new NoTokenFoundException();

        long isItTimeToRefresh = -1;
        try {
            JSONObject jObj = new JSONObject(jString);
            isItTimeToRefresh = jObj.getLong("refresh");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //TokenUtil soll refreshed werden
        if (isItTimeToRefresh < System.currentTimeMillis()) {
            try {
                RestBackendCommunication.getInstance().refreshToken();
            } catch (BackendCommunicationException |IOException |NetworkUnavailableException  e) {
                throw new RefreshTokenFailedException();
            }
        }

        //Geb Authentifizierung zurück
        try {
            JSONObject jObj = new JSONObject(jString);
            return jObj.getString("token_type") + " " + jObj.getString("access_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        throw new NoTokenFoundException();
    }

    public String getAuthorizationWithoutRefresh() throws NoTokenFoundException {
        SharedPreferences sp = ContextManager.getInstance().getContext().getSharedPreferences(sharedPreferencesToken, Context.MODE_PRIVATE);
        String jString = sp.getString("tokenjson",null);
        if (jString == null)
            throw new NoTokenFoundException();

        //Geb Authentifizierung zurück
        try {
            JSONObject jObj = new JSONObject(jString);
            return jObj.getString("token_type") + " " + jObj.getString("access_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        throw new NoTokenFoundException();

    }
}
