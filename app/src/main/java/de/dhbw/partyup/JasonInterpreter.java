package de.dhbw.partyup;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Tobias Berner on 17.10.2016.
 */

public class JasonInterpreter {

    public static JSONObject buildJSON(String toJSON){

    toJSON =  "{\"data\":{\"name\":{\"name\":\"New Product\",\"id\":1}}}";
        try {
            JSONObject jObject  = new JSONObject(toJSON); // json
            //JSONObject data = jObject.getJSONObject("data");
            //JSONObject test = data.getJSONObject("name");

            //System.out.println( jObject.getString("data"));
            return jObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;


    }


}
