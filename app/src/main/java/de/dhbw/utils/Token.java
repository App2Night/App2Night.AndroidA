package de.dhbw.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import org.json.JSONException;
import org.json.JSONObject;

import de.dhbw.app2night.MainActivity;
import de.dhbw.exceptions.NoTokenFoundException;

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



    public void saveTokenAwnser(String tosave){



        SharedPreferences sp = MainActivity.getContext().getSharedPreferences("token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("tokenjson",tosave);
        editor.commit();

    }

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

    public String getAuthorization() throws NoTokenFoundException {
        SharedPreferences sp = MainActivity.getContext().getSharedPreferences("token", Context.MODE_PRIVATE);
        String jString = sp.getString("tokenjson",null);
        if (jString == null)
            throw new NoTokenFoundException();

        try {
            JSONObject jObj = new JSONObject(jString);
            return jObj.getString("token_type") + " " + jObj.getString("access_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        throw new NoTokenFoundException();
    }
}
