package de.dhbw.utils;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import de.dhbw.backendTasks.party.ApiPartyTask;
import de.dhbw.backendTasks.userparty.ApiUserPartyTask;
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

    public Properties getProperties(){
        Properties properties = new Properties();
        AssetManager assetManager = ContextManager.getInstance().getContext().getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("app2night.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return properties;
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
        body = body + "&token=" + TokenUtil.getInstance().getRefreshToken();
        return body;
    }

    public String getTokenUrl(){
        Properties props = getProperties();
        return props.getProperty("app2night.api.url.login");
    }

    public String getRefreshUrl() {
        Properties props = getProperties();
        return props.getProperty("app2night.api.url.token.refresh");
    }

    public String getRegisterUrl() {
        Properties props = getProperties();
        return props.getProperty("app2night.api.url.register");
    }

    public String getGetPartyListFileName(){
        Properties props = getProperties();
        return props.getProperty("app2night.data.getpartylist");
    }

    public void init(TokenUtil tokenUtil) {
        Properties props = getProperties();
        tokenUtil.setSharedPreferencesName(props.getProperty("app2night.sharedpref.token"));
    }

    public void init(SettingsUtil sa) {
        Properties props =  getProperties();
        sa.setSharedPrefs(props.getProperty("app2night.settings.sharedpref"));
        sa.setDefaultSetting("radius",props.getProperty("app2night.settings.default.radius"));
    }

    public  void init(ApiPartyTask apt) {
        Properties props =  getProperties();
        apt.setUrl(props.getProperty("app2night.api.url.party"));
    }

    public void init(ApiUserPartyTask upt) {
        Properties props = getProperties();
        upt.setUrl(props.getProperty("app2night.api.url.userparty"));
    }

}
