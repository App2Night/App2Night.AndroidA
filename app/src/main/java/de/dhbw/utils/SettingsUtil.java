package de.dhbw.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import de.dhbw.exceptions.IllegalKeyException;

/**
 * Created by Tobias Berner on 26.10.2016.
 */

public class SettingsUtil {
    private static SettingsUtil sa = null;

    //Wird über PropertyUtil initialisiert
    private String sharedPreferencesSettings;
    private Map<String,String> defaultSettingsStrings = new HashMap<>();


    private SettingsUtil(){
        PropertyUtil.getInstance().init(this);
    }

    public static SettingsUtil getInstance(){
        if (sa == null){
            sa = new SettingsUtil();
        }
        return sa;
    }

    public void setDefaultSetting(String key, String value){
        defaultSettingsStrings.put(key, value);
    }

    public void setSharedPrefs(String filename){
        sharedPreferencesSettings=filename;
    }


    public String getSetting(String key) throws IllegalKeyException {
        if(!defaultSettingsStrings.containsKey(key))
            throw new IllegalKeyException(key + "entspricht keinem gespeicherten Schlüssel");

        SharedPreferences sp = ContextManager.getInstance().getContext().getApplicationContext().getSharedPreferences(sharedPreferencesSettings,Context.MODE_PRIVATE);
        return sp.getString(key,defaultSettingsStrings.get(key));
    }


    public void putSettingString(String key, String value) throws IllegalKeyException {
        key = key.toLowerCase();
        if(!defaultSettingsStrings.containsKey(key))
            throw new IllegalKeyException(key + "entspricht keinem Settingschlüssel");

        SharedPreferences sp = ContextManager.getInstance().getContext().getApplicationContext().getSharedPreferences(sharedPreferencesSettings,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,value);
        editor.commit();
    }

    /*
    public static String getSetting(String key, Context context){
        JSONObject settingsAsJSON = null;
        BufferedReader br = null;
        FileInputStream is = null;
        try {
            is = context.getApplicationContext().openFileInput(settingsFileName);
            br = new BufferedReader(new InputStreamReader(is));
            String SettingsAsJSONString;
            SettingsAsJSONString = br.readLine();
            settingsAsJSON= new JSONObject(SettingsAsJSONString);
        } catch (Exception e) {
            //Allgemeine Exception, da keine der Exceptions je auftreten dürfte
            e.printStackTrace();
        }finally{
            try {
            if(br != null)
                    br.close();
            if (is != null)
                    is.close();
            } catch (IOException e) {
                    e.printStackTrace();
            }
        }
        if (settingsAsJSON != null)
            try{
                String rückgabe = settingsAsJSON.getString(key);
                return rückgabe;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return "Fehler";
    }

    public static boolean writeSettings(String settingsAsJSON, Context context) {
        BufferedWriter bw = null;
        FileOutputStream os = null;
        if (!isValidJSONString(settingsAsJSON))
            return false;
        try {
            os = context.getApplicationContext().openFileOutput(settingsFileName,Context.MODE_PRIVATE);
            bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(settingsAsJSON);
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }finally{
            try{
            if (bw!=null)
                    bw.close();
            if (os!=null)
                    os.close();
        } catch (IOException e) {
                    e.printStackTrace();
            }
        }
        return true;
    }

    private static boolean isValidJSONString (String jString){
            try {
                JSONObject jObj = new JSONObject(jString);
                return true;
            } catch (JSONException e) {
                return false;
            }
    }*/
}
