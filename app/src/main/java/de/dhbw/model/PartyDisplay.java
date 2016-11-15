package de.dhbw.model;

/**
 * Created by Tobias Berner on 13.11.2016.
 */

public class PartyDisplay {


    private String partyId;
    private String partyName;
    private String partyDate;
    private int musicGenre;
    private String countryName;
    private String cityName;
    private String streetName;
    private String houseNumber;
    private String houseNumberAdditional;
    private String zipcode;
    private int    partyType;
    private String description;


    public PartyDisplay(){

    }



    public PartyDisplay(Party p){
        partyId = p.getPartyId();
        partyName = p.getPartyName();
        partyDate = p.getPartyDate();
        musicGenre = p.getMusicGenre();
        countryName = p.getLocation().getCountyName();
        cityName = p.getLocation().getCityName();
        streetName = p.getLocation().getStreetName();
        houseNumber = p.getLocation().getHouseNumber();
        houseNumberAdditional = p.getLocation().getHouseNumberAdditional();
        zipcode = p.getLocation().getZipcode();
        partyType = p.getPartyType();
        description = p.getDescription();
        partyId=null;
    }


    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyDate() {
        return partyDate;
    }

    public void setPartyDate(String partyDate) {
        this.partyDate = partyDate;
    }

    public int getMusicGenre() {
        return musicGenre;
    }

    public void setMusicGenre(int musicGenre) {
        this.musicGenre = musicGenre;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getHouseNumberAdditional() {
        return houseNumberAdditional;
    }

    public void setHouseNumberAdditional(String houseNumberAdditional) {
        this.houseNumberAdditional = houseNumberAdditional;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public int getPartyType() {
        return partyType;
    }

    public void setPartyType(int partyType) {
        this.partyType = partyType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
