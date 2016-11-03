package de.dhbw.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.dhbw.model.Party;

/**
 * Created by Tobias Berner on 02.11.2016.
 */

public class JSONHelper {

    private static JSONHelper jhelper= null;

    private JSONHelper(){};

    public static JSONHelper getInstance(){
        if (jhelper == null)
            jhelper = new JSONHelper();
        return jhelper;
    }



}
