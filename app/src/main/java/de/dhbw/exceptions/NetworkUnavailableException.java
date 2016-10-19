package de.dhbw.exceptions;

/**
 * Created by Tobias Berner on 19.10.2016.
 */

public class NetworkUnavailableException extends Exception {

    public NetworkUnavailableException(String errorMsg){
        super(errorMsg);

    }
}
