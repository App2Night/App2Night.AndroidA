package de.dhbw.utils;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.backendTasks.party.GetMyPartyList;
import de.dhbw.model.Party;

/**
 * Created by Tobias Berner on 30.11.2016.
 */

/**
 * Diese Klasse dient dem Speichern und Laden von der letzten Abfrage der Partyliste.
 */
public class GetPartyListSave {

    private static GetPartyListSave gpls;
    private final String getPartyListFile;


    private GetPartyListSave() {
       getPartyListFile = PropertyUtil.getInstance().getGetPartyListFileName();
    }

    public static GetPartyListSave getInstance(){
        if (gpls == null)
            gpls = new GetPartyListSave();
        return gpls;
    }

    public Party[] getList(){
        InputStream is = null;
        BufferedReader br = null;
        String jString;
        List<Party> partyList= new ArrayList<>();
        Gson gson = new Gson();
        try {
            is = ContextManager.getInstance().getContext().openFileInput(getPartyListFile);
            br = new BufferedReader(new InputStreamReader(is));
            jString = br.readLine();
            while (jString != null){
                partyList.add(gson.fromJson(jString, Party.class));
                jString = br.readLine();
            }

            Party[] parties = new Party[partyList.size()];
            return partyList.toArray(parties);
        } catch (FileNotFoundException e) {
           e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return new Party[0];
    }

    public void storeList(Party[] parties){
        OutputStream os = null;
        Gson gson = new Gson();
        try {
            os = ContextManager.getInstance().getContext().openFileOutput(getPartyListFile, Context.MODE_PRIVATE);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            for (Party p : parties) {
                String temp = gson.toJson(p);
                bw.write(temp);
                bw.newLine();
            }
            bw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if (os != null)
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
