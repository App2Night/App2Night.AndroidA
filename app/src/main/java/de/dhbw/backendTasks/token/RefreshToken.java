package de.dhbw.backendTasks.token;
import android.os.AsyncTask;
import java.io.IOException;
import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;
import de.dhbw.exceptions.NoTokenFoundException;
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 03.11.2016.
 */

public class RefreshToken extends AsyncTask<String, Void, Boolean> {
    String url;


    public void setUrl(String urlParam){
        url = urlParam;
    }

    public RefreshToken(){
        PropertyUtil.getInstance().init(this);
        try {
            String body = PropertyUtil.getInstance().getBodyOfRefreshToken();
            this.execute(url, body);
        } catch (NoTokenFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Boolean  doInBackground(String... params) {
        try {
                return RestBackendCommunication.getInstance().refreshToken(params[0],params[1]);
        } catch (BackendCommunicationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NetworkUnavailableException e) {
            e.printStackTrace();
        } catch (NoTokenFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean refreshErfolgreich){


    }
}
