package de.dhbw.hilfsfunktionen;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tobias Berner on 19.10.2016.
 */

public class PruefungBenutzereingabe {

    /**
     * Wertet aus, ob eine E-Mail akzeptiert wird.
     *
     * @param email - zu überprüfende Email
     * @return true, wenn Email aktzeptiert
     */
    public static boolean acceptEmail(String email){
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern compareTo = Pattern.compile(emailPattern);
        Matcher m = compareTo.matcher(email);
        return m.matches();
    }

    /**
     * Wertet aus, ob ein Nickname akzeptiert wird.
     *
     * @param nickname - zu überprüfender Nickname
     * @return true, wenn Nickname aktzeptiert
     */
    public static boolean acceptNickname(String nickname){
        String nicknamePattern = "[A-Za-z][A-Za-z0-9-]{0,29}";
        Pattern compareTo = Pattern.compile(nicknamePattern);
        Matcher m = compareTo.matcher(nickname);
        return m.matches();
    }

    /**
     * Wertet aus, ob ein Passwort akzeptiert wird. Wird akzeptiert wenn es zwischen 8 und 30 Zeichen lang ist und
     * mindestens 2 der 4 "Eingabeklassen" (Grossbuchstaben, Kleinbuchstaben, Sonderzeichen, Ziffern) verwendet.
     *
     * @param password - zu überprüfendes Passwort
     * @return true, wenn Passwort aktzeptiert
     */
    public static boolean checkPassword(String password){
        //"Eingabeklassen":
        boolean grossbuchstaben = false;
        boolean kleinbuchstaben = false;
        boolean sonderzeichen = false;
        boolean ziffern = false;

        //Mindeslaenge 8 Zeichen
        if (password.length() < 8)
            return false;

        //Maximallänge 30 Zeichen
        if (password.length() > 30)
            return false;

        for (int i = 0; i < password.length(); i++){
            if(password.charAt(i)>= 'a' && password.charAt(i) <= 'z')
                kleinbuchstaben = true;
            else if (password.charAt(i)>= 'A' && password.charAt(i) <= 'Z')
                grossbuchstaben = true;
            else if (password.charAt(i)>= '0' && password.charAt(i) <= '9')
                ziffern = true;
            else if (password.charAt(i) == '!' || password.charAt(i) == '?' || password.charAt(i) == '/' || password.charAt(i) == '.' || password.charAt(i) == ',')
                sonderzeichen = true;
            else
            //unerlaubtes Zeichen
                return false;
        }
        //Mindestens zwei "Eingabeklassen" müssen verwendet werden
        if (grossbuchstaben && kleinbuchstaben || grossbuchstaben && sonderzeichen
                || grossbuchstaben && ziffern || kleinbuchstaben && sonderzeichen
                || kleinbuchstaben && ziffern || sonderzeichen && ziffern)
            return true;
        else
            return false;
    }
}
