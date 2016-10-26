package de.dhbw.exceptions;

/**
 * Created by Tobias Berner on 19.10.2016.
 */

/**
 * Wird geworfen, wenn der Netzwerkdienst des Gerätes bei einer Internetoperation nicht verfügbar ist
 */
public class NetworkUnavailableException extends Exception {
    public NetworkUnavailableException(String errorMsg){
        super(errorMsg);

    }
}
