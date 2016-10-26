package de.dhbw.exceptions;

/**
 * Created by Tobias Berner on 19.10.2016.
 */

/**
 * Wird geworfen, wenn das Backend mit einem unerwarteten Http Response Code antwortet
 */
public class BackendCommunicationException extends Exception {

    public BackendCommunicationException (String errorMsg) {
        super(errorMsg);
    }

}
