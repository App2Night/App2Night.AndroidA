package de.dhbw.utils;

import android.content.Context;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Tobias Berner on 17.11.2016.
 */

public class UserUtil {

    private String sharedPreferencesUserUtil;
    private static UserUtil userUtil = null;

    private UserUtil(){
        PropertyUtil.getInstance().init(this);
    }

    public static UserUtil getInstance(){
        if (userUtil == null)
            userUtil = new UserUtil();
        return userUtil;
    }

    public boolean saveUserId(String jString, Context context){
        try {
            JSONObject jObj = new JSONObject(jString);
            String toSave  = jObj.getString("sub");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }


    public void setSharedPreferencesName(String sharedPreferencesUserUtil) {
        this.sharedPreferencesUserUtil = sharedPreferencesUserUtil;
    }
}
