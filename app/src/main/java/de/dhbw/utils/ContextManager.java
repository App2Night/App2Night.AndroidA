package de.dhbw.utils;

import android.content.Context;

/**
 * Created by Tobias Berner on 17.11.2016.
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
