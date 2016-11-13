package de.dhbw.BackEndCommunication;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import de.dhbw.app2night.MainActivity;
import de.dhbw.exceptions.BackendCommunicationException;
import de.dhbw.exceptions.NetworkUnavailableException;
import de.dhbw.exceptions.NoTokenFoundException;
import de.dhbw.exceptions.RefreshTokenFailedException;
import de.dhbw.utils.PropertyUtil;
import de.dhbw.utils.Token;

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

    /**
     * Ruft Token von Server ab.
     *
     * @return Json, welches unter anderem Token und Refresh Token enthält
     * @throws IOException - Wenn bei dem Zugriff auf den Input Stream ein Fehler auftritt
     * @throws BackendCommunicationException - Wenn Request fehlschlägt
     * @throws NetworkUnavailableException - Wenn keine Internetverbindung besteht
     * @throws NoTokenFoundException - Wenn kein Token zur Authentifizierung gefunden wird
     */
    public boolean getToken (String username, String password, Context c) {
        InputStream is = null;
        OutputStream os = null;
        String token;
        ConnectivityManager connMgr = (ConnectivityManager)
                c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                String myurl = PropertyUtil.getInstance().getTokenUrl(c);
                String body = PropertyUtil.getInstance().getBodyOfGetToken(username,password,c);
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
                    Token.getInstance().saveTokenAwnser(token,c);
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
     * Refresht das Token.
     *
     * @return true, wenn refresh erfolgreich war
     * @throws IOException - Wenn bei dem Zugriff auf den Input Stream ein Fehler auftritt
     * @throws BackendCommunicationException - Wenn Request fehlschlägt
     * @throws NetworkUnavailableException - Wenn keine Internetverbindung besteht
     * @throws NoTokenFoundException - Wenn kein Token zur Authentifizierung gefunden wird
     */
    public Boolean refreshToken () throws BackendCommunicationException, IOException, NetworkUnavailableException, NoTokenFoundException {
        InputStream is = null;
        OutputStream os = null;
        Context context = MainActivity.getContext();
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
                //Hole altes Token (ohne refresh)
                conn.setRequestProperty("Authorization", Token.getInstance().getAuthorizationWithoutRefresh());
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
       Context context = MainActivity.getContext();
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
        Context context = MainActivity.getContext();
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", Token.getInstance().getAuthorization());
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
        Context context = MainActivity.getContext();
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            try {

                URL url = new URL(myurl);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", Token.getInstance().getAuthorization());

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
        Context context = MainActivity.getContext();
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
                URL url = new URL(myurl);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("DELETE");
                conn.setRequestProperty("Authorization", Token.getInstance().getAuthorization());

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
