package de.dhbw.backendTasks.token;
import android.app.Activity;
import android.os.AsyncTask;
import java.io.IOException;
import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.app2night.MainActivity;
import de.dhbw.app2night.TestFragment;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 31.10.2016.
 */

public class GetToken extends AsyncTask<String,Void,String> {

    String url;
    Activity activity;
    TestFragment testFragment;

    public void setUrl(String urlParam){
        url = urlParam;
}

    public GetToken(String username, String password, TestFragment tF){
        testFragment = tF;
        activity = tF.getActivity();
        PropertyUtil.getInstance().init(this, activity);
        String body = PropertyUtil.getInstance().getBodyOfGetToken(username,password, activity);
        this.execute(url, body);
    }


    @Override
    protected String  doInBackground(String... params) {
        try {
            if (activity != null)
            return RestBackendCommunication.getInstance().getToken(params[0],params[1], activity);


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
        if (testFragment != null){
                testFragment.viewStatus.setText(result);

        }
    }


}
