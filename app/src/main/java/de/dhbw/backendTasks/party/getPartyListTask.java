package de.dhbw.backendTasks.party;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;

import de.dhbw.BackEndCommunication.RestBackendCommunication;
import de.dhbw.app2night.MainActivity;
import de.dhbw.app2night.TestFragment;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;
import de.dhbw.model.Party;
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 20.10.2016.
 */

public class GetPartyListTask extends AsyncTask<String, Void, String> implements ApiPartyTask {

    //Initialisert von PropertyUtil
    private static String url;
    MainActivity mainActivity;
    TestFragment testFragment;
    Activity activity;

    public void setUrl(String urlParm) {
        url = urlParm;
    }

    public GetPartyListTask(MainActivity mA) {
        mainActivity = mA;
        prepare(mA);
    }


    private void prepare(Context c) {
        PropertyUtil.getInstance().init(this);
        this.execute(url);
    }

    public GetPartyListTask(TestFragment tF) {
        testFragment = tF;
        activity = tF.getActivity();
        this.execute(url);

    }

    private void prepare() {
        this.execute(url);
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            RestBackendCommunication rbc = new RestBackendCommunication();
            if (activity != null)
                return rbc.getRequest(params[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        } catch (BackendCommunicationException e) {
            e.printStackTrace();
        } catch (NetworkUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {

        //Verarbeitung bei Aufruf von MainApp
        if (testFragment != null) {
            testFragment.viewStatus.setText(result);
            Party[] parties = new Gson().fromJson(result, Party[].class);
            for (Party p : parties) {
                System.out.println(p.getPartId());
                System.out.println(p.getLocation().getCityName());

            }

        }
    }
}

