package de.dhbw.model;
/**
 * Created by Tobias Berner on 02.11.2016.
 */

    public class Party {

        private String PartyId;
        private String PartyName;
        private int Price;
        private String PartyDate;
        private Location Location;
        private int MusicGenre;
        private int PartyType;
        private String Description;
        private User Host;


        public String getPartyId() {
            return PartyId;
        }

        public void setPartyId(String partyId) {
            PartyId = partyId;
        }

        public String getPartyName() {
            return PartyName;
        }

        public void setPartyName(String partyName) {
            PartyName = partyName;
        }

        public int getPrice() {
            return Price;
        }

        public void setPrice(int price) {
            Price = price;
        }

        public String getPartyDate() {
            return PartyDate;
        }

        public void setPartyDate(String partyDate) {
            PartyDate = partyDate;
        }

        public Location getLocation() {
            return Location;
        }

        public void setLocation(Location location) {
            Location = location;
        }

        public int getMusicGenre() {
            return MusicGenre;
        }

        public void setMusicGenre(int musicGenre) {
            MusicGenre = musicGenre;
        }

        public int getPartyType() {
            return PartyType;
        }

        public void setPartyType(int partyType) {
            PartyType = partyType;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public User getHost() {
            return Host;
        }

        public void setHost(User host) {
            Host = host;
        }


        public Party() {

        }

}


















