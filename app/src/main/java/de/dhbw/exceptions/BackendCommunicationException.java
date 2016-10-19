package de.dhbw.exceptions;

/**
 * Created by Tobias Berner on 19.10.2016.
 */

public class BackendCommunicationException extends Exception {

    public BackendCommunicationException (String errorMsg) {
        super(errorMsg);
    }

}
