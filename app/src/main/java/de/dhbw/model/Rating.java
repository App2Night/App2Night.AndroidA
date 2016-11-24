package de.dhbw.model;

/**
 * Created by Tobias Berner on 24.11.2016.
 */
public class Rating {

    private final int generalRating;
    private  final int locationRating;
    private  final int priceRating;
    private final int moodRating;

    public int getGeneralRating() {
        return generalRating;
    }

    public int getLocationRating() {
        return locationRating;
    }

    public int getPriceRating() {
        return priceRating;
    }

    public int getMoodRating() {
        return moodRating;
    }

    public Rating(int generalRating, int locationRating, int priceRating, int moodRating){
        if (generalRating > 0)
            this.generalRating=1;
        else if (generalRating < 0)
            this.generalRating = -1;
        else
            this.generalRating=0;

        if (locationRating > 0)
            this.locationRating=1;
        else if (locationRating < 0)
            this.locationRating = -1;
        else
            this.locationRating=0;

        if (priceRating > 0)
            this.priceRating=1;
        else if (priceRating < 0)
            this.priceRating = -1;
        else
            this. priceRating=0;

        if (moodRating > 0)
            this.moodRating=1;
        else if (moodRating < 0)
            this.moodRating = -1;
        else
            this.moodRating=0;
    }



}
