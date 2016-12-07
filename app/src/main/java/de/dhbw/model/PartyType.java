package de.dhbw.model;

/**
 * Created by Tobias Berner on 01.12.2016.
 */

public enum PartyType {
    Bar, Disco, Forest;


    public static PartyType toEnum(int i){
        switch(i){
            case 0: return Bar;
            case 1: return Disco;
            case 2: return Forest;
            default: throw new IllegalArgumentException();
        }
    }

    public static int toEnum(PartyType partyType){
        switch(partyType){
            case Bar: return 0;
            case Disco: return 1;
            case Forest: return 2;
            default: throw new IllegalArgumentException();
        }
    }
}
