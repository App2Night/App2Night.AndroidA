package de.dhbw.model;

/**
 * Created by Tobias Berner on 01.12.2016.
 */

public enum CommitmentState {
Commited, NotCommited, Bookmarked;

    public static CommitmentState toEnum(int i){
        switch(i){
            case 0: return Commited;
            case 1: return  Bookmarked;
            case 2: return NotCommited;
            default: throw new IllegalArgumentException();
        }
    }

    public static int toEnum(CommitmentState commitmentState){
        switch(commitmentState){
            case Commited: return 0;
            case Bookmarked: return 1;
            case NotCommited: return 2;
            default: throw new IllegalArgumentException();
        }
    }
}
