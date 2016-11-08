package de.dhbw.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.app2night.MainActivity;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;
import de.dhbw.exceptions.NoTokenFoundException;
import de.dhbw.exceptions.RefreshTokenFailedException;

/**
 * Created by Tobias Berner on 03.11.2016.
 */

public class Token {

    private static Token token = null;
    private Token(){}
    public static Token getInstance(){
        if(token == null)
            token = new Token();
        return token;
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
            //Extra Tupel in JSON, welches beim Abfragen der Authentifikation zu einem Refresh führt) (1000 Karenz)
           // jObj.put("refresh", System.currentTimeMillis()+duration -1000);
            jObj.put("refresh", System.currentTimeMillis());
            toSave = jObj.toString();
        } catch (JSONException e) {
            //Fals Fehler Auftritt, kann Eingabe JSON gespeichert werden
           toSave = eingabe;
        }

        SharedPreferences sp = MainActivity.getContext().getSharedPreferences("token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("tokenjson",toSave);
        editor.commit();
    }

    /**
     * Liest den Refresh Token aus dem Speicher.
     *
     * @return refresh Token
     * @throws NoTokenFoundException - kein gespeichertes Token wird gefunden
     */
    public String getRefreshToken() throws NoTokenFoundException {
        SharedPreferences sp = MainActivity.getContext().getSharedPreferences("token", Context.MODE_PRIVATE);
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
     * @throws NoTokenFoundException - Wenn kein früher gespeichertes Token gefunden wird
     * @throws RefreshTokenFailedException - Wenn das erneuern des Tokens fehlschlägt
     */
    public String getAuthorization() throws NoTokenFoundException, RefreshTokenFailedException {
        SharedPreferences sp = MainActivity.getContext().getSharedPreferences("token", Context.MODE_PRIVATE);
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
        //Token soll refreshed werden
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
        SharedPreferences sp = MainActivity.getContext().getSharedPreferences("token", Context.MODE_PRIVATE);
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
