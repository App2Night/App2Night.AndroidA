package de.dhbw.utils;

import android.content.Context;

/**
 * Created by Tobias Berner on 17.11.2016.
 */


/**
 *  Die Klasse dient als Hilfskonstrukt, um vor Allem in den Utilities immer einen Context abfragen zu können.
 *  Jede Activity sollte im onCreate ihren aktuellen Context an diese Klasse übergeben.
 */
public class ContextManager {

    Context context;

    private static ContextManager contextManager = null;

    private ContextManager(){

    }

    public static ContextManager getInstance(){
        if (contextManager == null)
            contextManager = new ContextManager();
        return contextManager;
    }

    public Context getContext(){
        return context;
    }

    public void setContext(Context context){
        this.context = context;
    }



}
