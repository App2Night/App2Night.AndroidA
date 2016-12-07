package de.dhbw.model;

/**
 * Created by Tobias Berner on 01.12.2016.
 */

public enum MusicGenre {
All, Rock, Pop, HipHop, Rap, Electro;

    public MusicGenre toEnum(int i){
        switch(i){
            case 0: return All;
            case 1: return Rock;
            case 2: return Pop;
            case 3: return HipHop;
            case 4: return Rap;
            case 5: return Electro;
            default: throw new IllegalArgumentException();
        }
    }

    public int toEnum(MusicGenre genre){
        switch(genre){
            case All: return 0;
            case Rock: return 1;
            case Pop: return 2;
            case HipHop: return 3;
            case Rap: return 4;
            case Electro: return 5;
            default: throw new IllegalArgumentException();
        }
    }
}
