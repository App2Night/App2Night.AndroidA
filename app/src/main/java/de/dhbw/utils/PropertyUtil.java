package de.dhbw.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

import de.dhbw.backendTasks.party.ApiPartyTask;
import de.dhbw.app2night.MainActivity;
import de.dhbw.backendTasks.token.GetToken;
import de.dhbw.backendTasks.token.RefreshToken;
import de.dhbw.exceptions.NoTokenFoundException;

/**
 * Created by Tobias Berner on 27.10.2016.
 */

public class PropertyUtil {

    private static PropertyUtil pu = null;

    private PropertyUtil(){   }

    public static PropertyUtil getInstance(){
        if(pu == null)
            pu = new PropertyUtil();
        return pu;

    }

    public void init (GetToken gt){
        Properties props = getProperties();
        gt.setUrl(props.getProperty("app2night.api.url.token.get"));

    }

    public void init(SettingsAdministration sa) {
        Properties props =  getProperties();
        sa.setSharedPrefs(props.getProperty("app2night.settings.sharedpref"));
        sa.setDefaultSetting("radius",props.getProperty("app2night.settings.default.radius"));
    }

    public  void init(ApiPartyTask apt) {
        Properties props =  getProperties();
        apt.setUrl(props.getProperty("app2night.api.url.party"));
    }

    public void init(RefreshToken refreshToken) {
        Properties props = getProperties();
        refreshToken.setUrl(props.getProperty("app2night.api.url.token.refresh"));
    }

    public String getBodyOfGetToken (String username, String password){
        Properties props =  getProperties();
        String body = props.getProperty("app2night.token.get.body");
        body = "username=" + username + "&password=" + password + "&" + body;
        return body;
    }

    public String getBodyOfRefreshToken() throws NoTokenFoundException {
        Properties props =  getProperties();
        String body = props.getProperty("app2night.token.refresh.body");
        body = body + "&token=" + Token.getInstance().getRefreshToken();
        return body;



    }

    public Properties getProperties(){
        Properties properties = new Properties();
        AssetManager assetManager = MainActivity.getContext().getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("app2night.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
