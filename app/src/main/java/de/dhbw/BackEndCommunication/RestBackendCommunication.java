package de.dhbw.BackEndCommunication;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import de.dhbw.app2night.MainActivity;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;
import de.dhbw.exceptions.NoTokenFoundException;
import de.dhbw.exceptions.RefreshTokenFailedException;
import de.dhbw.model.Location;
import de.dhbw.utils.ContextManager;
import de.dhbw.utils.PropertyUtil;
import de.dhbw.utils.TokenUtil;

/**
 * Created by Tobias Berner on 17.10.2016.
 *
 */
public class RestBackendCommunication {

    private static RestBackendCommunication RBC = null;
    private RestBackendCommunication() {}
    public static RestBackendCommunication getInstance(){
        if (RBC == null)
            RBC = new RestBackendCommunication();
        return RBC;
    }


    public boolean validateAdress(String myurl, Location location) throws IOException, RefreshTokenFailedException, NoTokenFoundException, BackendCommunicationException, NetworkUnavailableException {
        InputStream is = null;
        OutputStream os = null;
        Context context = ContextManager.getInstance().getContext();
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", TokenUtil.getInstance().getAuthorization());
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                os = conn.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                String jString = new Gson().toJson(location);
                bw.write(jString);
                bw.flush();
                bw.close();
                conn.connect();
                int response = conn.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) {
                    return true;
                }else {
                    throw new BackendCommunicationException(Integer.toString(response));
                }
            } finally {
                //Streams schließen
                if (is != null) {
                    is.close();
                }
                if(os != null){
                    os.close();
                }
            }
        }else {
            throw new NetworkUnavailableException("Network not connected");
        }













    }

    /**
     *
     * @param username Benutzername
     * @param password  Passwort
     * @param email     Email
     * @return  true, wenn anlegen funktioniert hat
     * @throws IOException - Wenn bei dem Zugriff auf den Input Stream ein Fehler auftritt
     * @throws BackendCommunicationException - Wenn Request fehlschlägt
     */
    public boolean register(String username, String password, String email) throws IOException, BackendCommunicationException {
        OutputStream os = null;
        ConnectivityManager connMgr = (ConnectivityManager)
                ContextManager.getInstance().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String myurl = PropertyUtil.getInstance().getRegisterUrl();
            String jString;
            try {
                JSONObject jObj = new JSONObject();
                jObj.put("username",username);
                jObj.put("password",password);
                jObj.put("email",email);
                jString = jObj.toString();
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
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
                return true;
            }
            else
                throw new BackendCommunicationException(Integer.toString(response));
        } else {
            //Netzwerk nicht verbunden
            return false;
        }

    }

    /**
     * Ruft TokenUtil von Server ab.
     *
     * @return Json, welches unter anderem TokenUtil und Refresh TokenUtil enthält
     * @throws IOException - Wenn bei dem Zugriff auf den Input Stream ein Fehler auftritt
     * @throws BackendCommunicationException - Wenn Request fehlschlägt
     * @throws NetworkUnavailableException - Wenn keine Internetverbindung besteht
     * @throws NoTokenFoundException - Wenn kein TokenUtil zur Authentifizierung gefunden wird
     */
    public boolean login(String username, String password) {
        InputStream is = null;
        OutputStream os = null;
        String token;
        ConnectivityManager connMgr = (ConnectivityManager)
                ContextManager.getInstance().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                String myurl = PropertyUtil.getInstance().getTokenUrl();
                String body = PropertyUtil.getInstance().getBodyOfGetToken(username,password);
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                os = conn.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                bw.write(body);
                bw.flush();
                bw.close();
                conn.connect();
                int response = conn.getResponseCode();
               if (response == HttpURLConnection.HTTP_OK) {
                    is = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    token = br.readLine();
                    TokenUtil.getInstance().saveTokenAwnser(token);
                   return true;
               } else {
                   //Antwort nicht ok
                    return false;
               }
            } catch (IOException e) {
                return false;
            } finally {
                //Stream schließen
                if (is != null)
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (os != null)
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        } else {
            //Netzwerk nicht verbunden
            return false;
        }
    }

    /**
     * Refresht das TokenUtil.
     *
     * @return true, wenn refresh erfolgreich war
     * @throws IOException - Wenn bei dem Zugriff auf den Input Stream ein Fehler auftritt
     * @throws BackendCommunicationException - Wenn Request fehlschlägt
     * @throws NetworkUnavailableException - Wenn keine Internetverbindung besteht
     * @throws NoTokenFoundException - Wenn kein TokenUtil zur Authentifizierung gefunden wird
     */
    public Boolean refreshToken () throws BackendCommunicationException, IOException, NetworkUnavailableException, NoTokenFoundException {
        InputStream is = null;
        OutputStream os = null;
        Context context = ContextManager.getInstance().getContext();
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                //Ermittle benötigte Informationen
                String myurl = PropertyUtil.getInstance().getRefreshUrl();
                String body = PropertyUtil.getInstance().getBodyOfRefreshToken();

                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                //Hole altes TokenUtil (ohne refresh)
                conn.setRequestProperty("Authorization", TokenUtil.getInstance().getAuthorizationWithoutRefresh());
                conn.setDoOutput(true);
                conn.setDoInput(true);
                os = conn.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                bw.write(body);
                bw.flush();
                bw.close();
                conn.connect();
                String str = conn.getResponseMessage();
                int response = conn.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) {
                    return true;
                } else {
                    throw new BackendCommunicationException(Integer.toString(response));
                }
            } finally {
                //Stream schließen
                if (is != null)
                    is.close();
                if (os != null)
                    os.close();
            }
        } else {
            //Netzwerk nicht verbunden
            throw new NetworkUnavailableException("Network not connected");
        }
    }

    public boolean saveUserId() throws IOException, BackendCommunicationException, NetworkUnavailableException {
        InputStream is = null;
        String jStringFromServer;
        Context context = ContextManager.getInstance().getContext();
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                String myurl;
                myurl = PropertyUtil.getInstance().getUserInfoUrl();
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                int response = conn.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) {
                    is = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    jStringFromServer = br.readLine();
                    br.close();
                    return false;
                } else {
                    throw new BackendCommunicationException(Integer.toString(response));
                }
            } finally {
                //Stream schließen
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
     * Führt einen Get-Request an die URL aus und gibt den Body der Serverantwort zurück.
     *
     * @param myurl  - URL für Get-Request
     * @return - Body der Serverantwort
     * @throws IOException - Wenn bei dem Zugriff auf den Input Stream ein Fehler auftritt
     * @throws BackendCommunicationException - Wenn get Request fehlschlägt
     * @throws NetworkUnavailableException - Wenn keine Internetverbindung besteht
     */
   public String getRequest(String myurl) throws IOException, BackendCommunicationException, NetworkUnavailableException {
       InputStream is = null;
       String jStringFromServer;
       Context context = ContextManager.getInstance().getContext();
       ConnectivityManager connMgr = (ConnectivityManager)
               context.getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
       if (networkInfo != null && networkInfo.isConnected()) {
           try {
               URL url = new URL(myurl);
               HttpURLConnection conn = (HttpURLConnection) url.openConnection();
               conn.setReadTimeout(10000 /* milliseconds */);
               conn.setConnectTimeout(15000 /* milliseconds */);
               conn.setRequestMethod("GET");
               conn.setDoInput(true);
               conn.connect();
               int response = conn.getResponseCode();
               if (response == HttpURLConnection.HTTP_OK) {
                   is = conn.getInputStream();
                   BufferedReader br = new BufferedReader(new InputStreamReader(is));
                   jStringFromServer = br.readLine();
                   br.close();
               }
               else {
                   throw new BackendCommunicationException(Integer.toString(response));
               }
               return jStringFromServer;
           } finally {
               //Stream schließen
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
     *  Führt einen PostRequest an die URL aus.
     *
     * @param myurl - URL für den Post Request
     * @param jString - JSON Objekt als String, das an URL geschickt werden soll
     * @return  Body der Serverantwort
     * @throws NetworkUnavailableException - Wenn keine Internetverbindung besteht
     * @throws IOException - Wenn bei dem Zugriff auf den Input oder Output Stream ein Fehler auftritt
     * @throws BackendCommunicationException -  Wenn post Request fehlschlägt
     */
    public String postRequest(String myurl, String jString) throws NetworkUnavailableException, IOException, BackendCommunicationException, NoTokenFoundException, RefreshTokenFailedException {
        InputStream is = null;
        OutputStream os = null;
        Context context = ContextManager.getInstance().getContext();
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", TokenUtil.getInstance().getAuthorization());
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
                } else if (response == HttpURLConnection.HTTP_UNAUTHORIZED){
                    //To Do
                    return "UNAUTHORIZED";
                }else {
                    throw new BackendCommunicationException(Integer.toString(response));
                }
            } finally {
                //Streams schließen
                if (is != null) {
                        is.close();
                }
                if(os != null){
                        os.close();
                }
            }
        }else {
            throw new NetworkUnavailableException("Network not connected");
        }
    }

    /**
     *  Führt einen Put-Request an die URL aus.
     *
     * @param myurl - URL für den Post Request
     * @param jString - JSON Objekt als String, das an URL geschickt werden soll
     * @return Body der Serverantwort
     * @throws NetworkUnavailableException - Wenn keine Internetverbindung besteht
     * @throws IOException - Wenn bei dem Zugriff auf den Input oder Output Stream ein Fehler auftritt
     * @throws BackendCommunicationException -  Wenn post Request fehlschlägt
     */
    public boolean putRequest(String myurl, String jString) throws NetworkUnavailableException, IOException, BackendCommunicationException, NoTokenFoundException, RefreshTokenFailedException {
        InputStream is = null;
        OutputStream os = null;
        Context context = ContextManager.getInstance().getContext();
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", TokenUtil.getInstance().getAuthorization());
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("PUT");
                conn.setDoInput(true);
                os = conn.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                bw.write(jString);
                bw.flush();
                bw.close();
                conn.connect();
                int response = conn.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) {
                    return true;
                } else {
                    throw new BackendCommunicationException(Integer.toString(response));
                }
            } finally {
                //Streams schließen
                if (is != null) {
                        is.close();
                    if (os != null) {
                        os.close();
                    }
                }
            }

        }else {
            throw new NetworkUnavailableException("Network not connected");
        }
    }

    /**
     * Führt ein Delete-Request and die URL aus.
     *
     * @param myurl - URL für den Delete-Request
     * @return true, wenn löschen erfolgreich
     * @throws NetworkUnavailableException - Wenn keine Internetverbindung besteht
     * @throws IOException - Wenn bei dem Zugriff auf den Input oder Output Stream ein Fehler auftritt
     * @throws BackendCommunicationException -  Wenn post Request fehlschlägt
     */
    public boolean deleteRequest(String myurl) throws NetworkUnavailableException, IOException, BackendCommunicationException, NoTokenFoundException, RefreshTokenFailedException {
        Context context = ContextManager.getInstance().getContext();
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("DELETE");
                conn.setRequestProperty("Authorization", TokenUtil.getInstance().getAuthorization());
                conn.connect();
                int response = conn.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) {
                    return true;
                } else {
                    throw new BackendCommunicationException(Integer.toString(response));
                }
                }
        else {
            throw new NetworkUnavailableException("Network not connected");
        }
    }

}
