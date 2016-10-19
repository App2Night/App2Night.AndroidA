package de.dhbw.BackEndCommunication;

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

import org.json.JSONArray;
import org.json.JSONException;

import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;

/**
 * Created by Tobias Berner on 17.10.2016.
 *
 */
public class RestBackendCommunication {

    public TextView view;
/*
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


    public void postJsonObjectToUrl(String url, JSONObject jObj, Context c) {
        Context context = c;
        String jString = jObj.toString();
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            new postToServerTask().execute(url,jString);
        } else {

        }
    }
    */

    /**
     * Führt einen Get-Request an die URL aus und gibt den Body der Serverantwort zurück.
     *
     * @param myurl  - URL für Get-Request
     * @param context - Aufrufende Activity
     * @return - Body der Serverantwort
     * @throws IOException - Wenn bei dem Zugriff auf den Input Stream ein Fehler auftritt
     * @throws BackendCommunicationException - Wenn get Request fehlschlägt
     * @throws NetworkUnavailableException - Wenn keine Internetverbindung besteht
     */
   private String getRequest(String myurl, Context context) throws IOException, BackendCommunicationException, NetworkUnavailableException {
       InputStream is = null;
       String jStringFromServer;

       ConnectivityManager connMgr = (ConnectivityManager)
               context.getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
       if (networkInfo != null && networkInfo.isConnected()) {

           try {
               myurl = "http://app2nightapi.azurewebsites.net/api/Party";
               URL url = new URL(myurl);

               HttpURLConnection conn = (HttpURLConnection) url.openConnection();
               conn.setReadTimeout(10000 /* milliseconds */);
               conn.setConnectTimeout(15000 /* milliseconds */);
               conn.setRequestMethod("GET");
               conn.setDoInput(true);
               // Starts the query

               conn.connect();

               int response = conn.getResponseCode();
               if (response == HttpURLConnection.HTTP_OK) {
                   is = conn.getInputStream();
                   BufferedReader br = new BufferedReader(new InputStreamReader(is));
                   jStringFromServer = br.readLine();
                   br.close();
               } else {
                   throw new BackendCommunicationException(Integer.toString(response));
               }

               return jStringFromServer;
           } finally {
               if (is != null) {
                   is.close();
               }
           }

       } else {
           //Netzwerk nicht verbunden
           throw new NetworkUnavailableException("Network not connected");
       }
   }


    /**
     *  Führt einen PostRequest an die URL durch. Dabei wird ein JSON Objekt als String
     *  mitgeschickt und die Antwort des Servers wird zurückgegeben.
     *
     * @param myurl - URL für den Post Request
     * @param jString - JSON Objekt als String, das an URL gepostet werden soll
     * @param context - Aufrufende Activity
     * @return  Body der Serverantwort
     * @throws NetworkUnavailableException - Wenn keine Internetverbindung besteht
     * @throws IOException - Wenn bei dem Zugriff auf den Input oder Output Stream ein Fehler auftritt
     * @throws BackendCommunicationException -  Wenn post Request fehlschlägt
     */
    private String postRequest(String myurl, String jString, Context context) throws NetworkUnavailableException, IOException, BackendCommunicationException {

        InputStream is = null;
        OutputStream os = null;

        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            try {
                myurl = "http://app2nightapi.azurewebsites.net/api/Party";
                URL url = new URL(myurl);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json");

                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");

                conn.setDoInput(true);
                conn.setDoOutput(true);

                os = conn.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                bw.write(jString);
                bw.flush();
                bw.close();


                conn.connect();
                int response = conn.getResponseCode();

                if (response == HttpURLConnection.HTTP_CREATED) {
                    is = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String jsonAsString = br.readLine();
                    br.close();
                    return jsonAsString;
                } else {
                   throw new BackendCommunicationException(Integer.toString(response));
                }
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(os != null){
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }else {
            throw new NetworkUnavailableException("Network not connected");
        }
    }


    private class postToServerTask extends AsyncTask<String, Void, String> {

        @Override
        //eingabe[0] -> url; eingabe[1] -> JSON String
        protected String doInBackground(String... eingabe) {
                //return postRequest(eingabe[0],eingabe[1]);
            return "";
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if (result != null && view != null)
                view.setText(result);
        }
    }


    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

           /* // params comes from the execute() call: params[0] is the url.
            try {
               // return getRequest(urls[0],new Context());
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }*/
            return "";
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if (result != null && view != null)
                view.setText(result);

            try {
                JSONArray jArray = new JSONArray(result);
                view.setText(jArray.getJSONObject(0).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}