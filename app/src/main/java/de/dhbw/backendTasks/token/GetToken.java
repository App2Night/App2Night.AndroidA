package de.dhbw.backendTasks.token;
import android.app.Activity;
import android.os.AsyncTask;
import java.io.IOException;
import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.app2night.TestFragment;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;
import de.dhbw.exceptions.NoTokenFoundException;
import de.dhbw.utils.PropertyUtil;
import de.dhbw.utils.Token;

/**
 * Created by Tobias Berner on 31.10.2016.
 */

public class GetToken extends AsyncTask<String,Void,String> {

    String url;

    public void setUrl(String urlParam){
        url = urlParam;
}

    public GetToken(String username, String password){
        PropertyUtil.getInstance().init(this);
        String body = PropertyUtil.getInstance().getBodyOfGetToken(username,password);
        this.execute(url, body);
    }


    @Override
    protected String  doInBackground(String... params) {
        try {
            return RestBackendCommunication.getInstance().getToken(params[0],params[1]);
        } catch (BackendCommunicationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NetworkUnavailableException e) {
            e.printStackTrace();
        } catch (NoTokenFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result){
        if (result != null)
            Token.getInstance().saveTokenAwnser(result);
    }


}
