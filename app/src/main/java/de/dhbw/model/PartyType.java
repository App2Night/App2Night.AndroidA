package de.dhbw.model;

/**
 * Created by Tobias Berner on 01.12.2016.
 */

public enum PartyType {
BAR,DISCO,FOREST;


    public static PartyType toEnum(int i){
        switch(i){
            case 0: return BAR;
            case 1: return DISCO;
            case 2: return FOREST;
            default: throw new IllegalArgumentException();
        }
    }

    public static int toEnum(PartyType partyType){
        switch(partyType){
            case BAR: return 0;
            case DISCO: return 1;
            case FOREST: return 2;
            default: throw new IllegalArgumentException();
        }
    }
}
