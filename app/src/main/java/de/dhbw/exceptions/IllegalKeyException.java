package de.dhbw.exceptions;

/**
 * Created by Tobias Berner on 26.10.2016.
 */

/**
 * Wird geworfen, wenn ein nicht vorgesehener Key in die Shared Preferences eingefügt werden sollte.
 */
public class IllegalKeyException extends Exception{
    public IllegalKeyException(String errorMsg){
        super(errorMsg);

    }
}
