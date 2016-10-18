package de.dhbw.partyup;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Tobias Berner on 17.10.2016.
 *
 */
public class RestBackendCommunication {

    public TextView view;

    // When user clicks button, calls AsyncTask.
    // Before attempting to fetch the URL, makes sure that there is a network connection.
    public void getRequest(String url, Context c) {
        // Gets the URL from the UI's text field.
         url = "http://app2nightapi.azurewebsites.net/api/Party";
        Context context = c;
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute(url);
        } else {

        }
    }

    // When user clicks button, calls AsyncTask.
    // Before attempting to fetch the URL, makes sure that there is a network connection.
    public void postRequest(String url,JSONObject jObj, Context c) {
        // Gets the URL from the UI's text field.
        // url = "http://192.168.240.1/arduino/digital/13";
        Context context = c;

        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            new postToServerTask().execute(url,"" /*jObj.toString()*/);
        } else {

        }
    }

    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
   private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String contentAsString = br.readLine();
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private String postToServer(String myurl, String jString){

        InputStream is = null;
        OutputStream os = null;
        String contentAsString = "";
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL("http://app2nightapi.azurewebsites.net/api/Party");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");


            conn.setDoInput(true);
            conn.setDoOutput(true);

            os = conn.getOutputStream();
            //JSONObject jObj = new JSONObject(jString);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

            bw.write("{\"partyName\": \"string\", \"partyDate\": \"2016-10-18T17:21:27.909Z\"}");
            bw.flush();
            bw.close();


            // Starts the query
            conn.connect();


            int response = conn.getResponseCode();
            is = conn.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            contentAsString = br.readLine();

            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e)    {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return contentAsString;

    }


    private class postToServerTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... eingabe) {
                return postToServer(eingabe[0],eingabe[1]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if (result != null)
                view.setText(result);
        }
    }


    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if (result != null)
                view.setText(result);
        }
    }
}
