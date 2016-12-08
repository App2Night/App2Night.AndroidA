package de.dhbw.utils;

/**
 * Created by Tobias Berner on 01.12.2016.
 */

/**
 * Dient der Formatierung des Datums und der Zeit.
 */
public class DateUtil {

    private DateUtil(){}
    private static DateUtil du;
    public static DateUtil getInstance(){
        if (du == null)
            du = new DateUtil();
        return du;
    }

    public String getDateToDisplay(String input){
        String day = input.substring(8,10);
        String month = input.substring(5,7);
        String year = input.substring(0,4);
        String output = day +".";
        output += month +".";
        output += year;

        return output;
    }

    public String getDateInFormat(String input){
        String day = input.substring(8,10);
        String month = input.substring(5,7);
        String year = input.substring(0,4);
        String output = year +"-";
        output += month +"-";
        output += day;

        return output;
    }

    public String getDate(String input){
        String day = input.substring(8,10);
        String month = input.substring(5,7);
        String year = input.substring(0,4);
        String output = day +". ";
        switch (month) {
            case "01": output += "Januar "; break;
            case "02": output += "Februar "; break;
            case "03": output += "März "; break;
            case "04": output += "April "; break;
            case "05": output += "Mai "; break;
            case "06": output += "Juni "; break;
            case "07": output += "Juli "; break;
            case "08": output += "August "; break;
            case "09": output += "September "; break;
            case "10": output += "Oktober "; break;
            case "11": output += "November "; break;
            case "12": output += "Dezember "; break;
            default: output += month + " "; break;
        }
        output += year;

        return output;
    }

    public String getTime(String input){
       return input.substring(11,16);
    }

}
