package de.dhbw.model;

/**
 * Created by Tobias Berner on 01.12.2016.
 */

public enum MusicGenre {
ALL, ROCK, POP, HIPHOP, RAP, ELECTRO;

    public static MusicGenre toEnum(int i){
        switch(i){
            case 0: return ALL;
            case 1: return ROCK;
            case 2: return POP;
            case 3: return HIPHOP;
            case 4: return RAP;
            case 5: return ELECTRO;
            default: throw new IllegalArgumentException();
        }
    }

    public static int toEnum(MusicGenre genre){
        switch(genre){
            case ALL: return 0;
            case ROCK: return 1;
            case POP: return 2;
            case HIPHOP: return 3;
            case RAP: return 4;
            case ELECTRO: return 5;
            default: throw new IllegalArgumentException();
        }
    }
}
