package de.dhbw.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tobias Berner on 19.10.2016.
 */


/**
 * Die Klasse dient der Validierung von Benutzereingaben beim Registrieren.
 */
public class CheckUserInputRegister {

    private static CheckUserInputRegister pbe = null;
    private CheckUserInputRegister(){}

    public static CheckUserInputRegister getInstance(){
        if (pbe == null)
            pbe = new CheckUserInputRegister();
        return pbe;
    }


    /**
     * Wertet aus, ob eine E-Mail akzeptiert wird.
     *
     * @param email - zu überprüfende Email
     * @return true, wenn Email aktzeptiert
     */
    public boolean acceptEmail(String email){
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
    public boolean acceptNickname(String nickname){
        String nicknamePattern = "[A-Za-z][A-Za-z0-9-]{0,29}";
        Pattern compareTo = Pattern.compile(nicknamePattern);
        Matcher m = compareTo.matcher(nickname);
        return m.matches();
    }

    /**
     * Wertet aus, ob das eingegebene Passwor gültig ist
     *
     * @param password - zu überprüfendes Passwort
     * @return true, wenn Passwort aktzeptiert
     */
    public boolean acceptPassword(String password){

        //Mindeslaenge 8 Zeichen
        if (password.length() < 5)
            return false;

        //Maximallänge 30 Zeichen
        if (password.length() > 30)
            return false;

        return true;
    }
}
