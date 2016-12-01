package de.dhbw.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Properties;

import de.dhbw.app2night.MainActivity;
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 02.11.2016.
 */

public class Location implements Serializable{
    private String CountryName;
    private String CityName;
    private String StreetName;
    private String HouseNumber;
    private String Zipcode;
    private double Latitude;
    private double Longitude;

    public Location(){}

    public Location(PartyDisplay pd){
        CountryName = pd.getCountryName();
        CityName = pd.getCityName();
        StreetName = pd.getStreetName();
        HouseNumber = pd.getHouseNumber();
        Zipcode = pd.getZipcode();
        Latitude = 0;
        Longitude = 0;
    }

    public String getCountyName() {
        return CountryName;
    }

    public void setCountyName(String countyName) {
        this.CountryName = countyName;
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

    public String getHouseNumber() {
        return HouseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.HouseNumber = houseNumber;
    }

    public String getZipcode() {
        return Zipcode;
    }

    public void setZipcode(String zipcode) {
        this.Zipcode = zipcode;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        this.Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        this.Longitude = longitude;
    }

}


