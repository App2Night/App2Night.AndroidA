package de.dhbw.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Properties;

import de.dhbw.app2night.MainActivity;
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 02.11.2016.
 */

public class Location {
    private String CountyName;
    private String CityName;
    private String StreetName;
    private int HouseNumber;
    private String HouseNumberAdditional;
    private int Zipcode;
    private int Latitude;
    private int Longitude;

    public String getCountyName() {
        return CountyName;
    }

    public void setCountyName(String countyName) {
        this.CountyName = countyName;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        this.CityName = cityName;
    }

    public String getStreetName() {
        return StreetName;
    }

    public void setStreetName(String streetName) {
        this.StreetName = streetName;
    }

    public int getHouseNumber() {
        return HouseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.HouseNumber = houseNumber;
    }

    public String getHouseNumberAdditional() {
        return HouseNumberAdditional;
    }

    public void setHouseNumberAdditional(String houseNumberAdditional) {
        this.HouseNumberAdditional = houseNumberAdditional;
    }

    public int getZipcode() {
        return Zipcode;
    }

    public void setZipcode(int zipcode) {
        this.Zipcode = zipcode;
    }

    public int getLatitude() {
        return Latitude;
    }

    public void setLatitude(int latitude) {
        this.Latitude = latitude;
    }

    public int getLongitude() {
        return Longitude;
    }

    public void setLongitude(int longitude) {
        this.Longitude = longitude;
    }


}


