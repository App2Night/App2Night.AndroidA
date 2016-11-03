package de.dhbw.backendTasks.token;
import android.os.AsyncTask;
import java.io.IOException;
import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.app2night.MainActivity;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 31.10.2016.
 */

public class GetToken extends AsyncTask<String,Void,String> {

    String url;
    MainActivity mainActivity;

    public void setUrl(String urlParam){
        url = urlParam;
}

    public GetToken(String username, String password, MainActivity mA){
        mainActivity = mA;
        PropertyUtil.getInstance().init(this, mA);
        String body = PropertyUtil.getInstance().getBodyOfGetToken(username,password, mA);
        this.execute(url, body);
    }


    @Override
    protected String  doInBackground(String... params) {
        try {
            if (mainActivity != null)
            return RestBackendCommunication.getInstance().getToken(params[0],params[1], mainActivity);


        } catch (BackendCommunicationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NetworkUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result){
        //Verarbeitung bei Aufruf von MainApp
        if (mainActivity != null){
                mainActivity.viewStatus.setText(result);

        }
    }


}
