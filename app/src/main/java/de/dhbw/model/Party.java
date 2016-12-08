package de.dhbw.model;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Tobias Berner on 02.11.2016.
 */

    public class Party implements Serializable{

        private String PartyId;
        private String PartyName;
        private int Price;
        private String PartyDate;
        private Location Location;
        private int MusicGenre;
        private int PartyType;
        private String Description;
        private User Host;
        private boolean HostedByUser;
        private int GeneralUpVoting;
        private int GeneralDownVoting;
        private int PriceUpVoting;
        private int PriceDownVoting;
        private int LocationUpVoting;
        private int LocationDownVoting;
        private int MoodUpVoting;
        private int MoodDownVoting;
        private int UserCommitmentState;
        private User[] CommmittedUser;




    public int getUserCommitmentState() {
        return UserCommitmentState;
    }

    public void setUserCommitmentState(int userCommitmentState) {
        UserCommitmentState = userCommitmentState;
    }
    public boolean isHostedByUser() {
        return HostedByUser;
    }

    public void setHostedByUser(boolean hostedByUser) {
        HostedByUser = hostedByUser;
    }

    public int getGeneralUpVoting() {
        return GeneralUpVoting;
    }

    public void setGeneralUpVoting(int generalUpVoting) {
        GeneralUpVoting = generalUpVoting;
    }

    public int getGeneralDownVoting() {
        return GeneralDownVoting;
    }

    public void setGeneralDownVoting(int generalDownVoting) {
        GeneralDownVoting = generalDownVoting;
    }

    public int getPriceUpVoting() {
        return PriceUpVoting;
    }

    public void setPriceUpVoting(int priceUpVoting) {
        PriceUpVoting = priceUpVoting;
    }

    public int getPriceDownVoting() {
        return PriceDownVoting;
    }

    public void setPriceDownVoting(int priceDownVoting) {
        PriceDownVoting = priceDownVoting;
    }

    public int getLocationUpVoting() {
        return LocationUpVoting;
    }

    public void setLocationUpVoting(int locationUpVoting) {
        LocationUpVoting = locationUpVoting;
    }

    public int getLocationDownVoting() {
        return LocationDownVoting;
    }

    public void setLocationDownVoting(int locationDownVoting) {
        LocationDownVoting = locationDownVoting;
    }

    public int getMoodUpVoting() {
        return MoodUpVoting;
    }

    public void setMoodUpVoting(int moodUpVoting) {
        MoodUpVoting = moodUpVoting;
    }

    public int getMoodDownVoting() {
        return MoodDownVoting;
    }

    public void setMoodDownVoting(int moodDownVoting) {
        MoodDownVoting = moodDownVoting;
    }

    public User[] getCommmittedUser() {
        return CommmittedUser;
    }

    public void setCommmittedUser(User[] commmittedUser) {
        CommmittedUser = commmittedUser;
    }

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


















