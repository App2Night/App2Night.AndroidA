
package de.dhbw.app2night;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import de.dhbw.backendTasks.userparty.PartyRating;
import de.dhbw.backendTasks.userparty.PartyRatingTask;
import de.dhbw.model.Rating;

/**
 * Created by SchmidtRo on 06.12.2016.
 */

public class VoteDialog extends DialogFragment implements PartyRating, View.OnClickListener{

    //Variablen
    public final static String ARG_PARTYID = "arg_partyid";
    ImageView party_up, party_down, location_up, location_down, mood_up, mood_down, price_up, price_down;
    boolean party_voted = false;
    boolean location_voted = false;
    boolean mood_voted = false;
    boolean price_voted = false;
    Button ok_button, cancel_button;
    String partyId;
    int generalRating = 0;
    int locationRating = 0;
    int priceRating = 0;
    int moodRating = 0;


    public VoteDialog(){
        // Required empty public constructor
    }

    /**
     * Beim erstellen Aufgerufen
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        partyId = getArguments().getString(ARG_PARTYID);
    }


    /**
     * Beim erstellen der View aufgerufen
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_voting, container, false);

        //Vote Buttons
        party_up = (ImageView) v.findViewById(R.id.party_thumb_up);
        party_down = (ImageView) v.findViewById(R.id.party_thumb_down);
        location_up = (ImageView) v.findViewById(R.id.location_up);
        location_down = (ImageView) v.findViewById(R.id.location_down);
        mood_up = (ImageView) v.findViewById(R.id.mood_up);
        mood_down = (ImageView) v.findViewById(R.id.mood_down);
        price_up = (ImageView) v.findViewById(R.id.price_up);
        price_down = (ImageView) v.findViewById(R.id.price_down);
        ok_button = (Button) v.findViewById(R.id.vote_button_ok);

        ok_button.setOnClickListener(this);
        cancel_button = (Button) v.findViewById(R.id.vote_button_cancel);
        cancel_button.setOnClickListener(this);

        //Animationen laden
        final Animation fade_in = AnimationUtils.loadAnimation(getActivity(), R.anim.image_fade_in);
        final Animation fade_out = AnimationUtils.loadAnimation(getActivity(), R.anim.image_fade_out);

        //Wenn die Party nicht gesamt gevotet ist, je nachdem wie abgestimmt wurde die anderen Buttons ausblenden, unklickbar machen. Farbe der Buttons die gewählt wurden ändern.
        View.OnClickListener party_vote = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!party_voted) {

                    switch(v.getId()){
                        case R.id.party_thumb_up:
                            party_down.startAnimation(fade_out);
                            party_down.setVisibility(v.INVISIBLE);
                            party_down.setClickable(false);
                            location_down.startAnimation(fade_out);
                            location_down.setVisibility(v.INVISIBLE);
                            location_down.setClickable(false);
                            mood_down.startAnimation(fade_out);
                            mood_down.setVisibility(v.INVISIBLE);
                            mood_down.setClickable(false);
                            price_down.setAnimation(fade_out);
                            price_down.setVisibility(v.INVISIBLE);
                            price_down.setClickable(false);
                            party_up.setImageResource(R.drawable.thumb_up_96);
                            location_up.setImageResource(R.drawable.thumb_up_96);
                            mood_up.setImageResource(R.drawable.thumb_up_96);
                            price_up.setImageResource(R.drawable.thumb_up_96);
                            generalRating = 1;
                            locationRating = 1;
                            priceRating = 1;
                            moodRating = 1;
                            break;
                        case R.id.party_thumb_down:
                            party_up.setVisibility(v.INVISIBLE);
                            party_up.startAnimation(fade_out);
                            party_up.setClickable(false);
                            location_up.setVisibility(v.INVISIBLE);
                            location_up.startAnimation(fade_out);
                            location_up.setClickable(false);
                            mood_up.setVisibility(v.INVISIBLE);
                            mood_up.startAnimation(fade_out);
                            mood_up.setClickable(false);
                            price_up.setVisibility(v.INVISIBLE);
                            price_up.startAnimation(fade_out);
                            price_up.setClickable(false);
                            party_down.setImageResource(R.drawable.thumbs_down_96);
                            location_down.setImageResource(R.drawable.thumbs_down_96);
                            mood_down.setImageResource(R.drawable.thumbs_down_96);
                            price_down.setImageResource(R.drawable.thumbs_down_96);
                            generalRating = -1;
                            locationRating = -1;
                            priceRating = -1;
                            moodRating = -1;
                            break;
                    }
                    party_voted = true;
                    location_voted = true;
                    mood_voted = true;
                    price_voted = true;

                    // Wenn Party schon gevoted wurde wieder Buttons richtig einblenden, klickbar machen und farben ändern.
                } else if (party_voted) {

                    switch(v.getId())
                    {
                        case R.id.party_thumb_down:
                            party_up.setVisibility(v.VISIBLE);
                            party_up.startAnimation(fade_in);
                            party_up.setClickable(true);
                            location_up.setVisibility(v.VISIBLE);
                            location_up.startAnimation(fade_in);
                            location_up.setClickable(true);
                            mood_up.setVisibility(v.VISIBLE);
                            mood_up.startAnimation(fade_in);
                            mood_up.setClickable(true);
                            price_up.setVisibility(v.VISIBLE);
                            price_up.startAnimation(fade_in);
                            price_up.setClickable(true);
                            party_down.setVisibility(v.VISIBLE);
                            party_down.startAnimation(fade_in);
                            party_down.setClickable(true);
                            location_down.setVisibility(v.VISIBLE);
                            location_down.startAnimation(fade_in);
                            location_down.setClickable(true);
                            mood_down.setVisibility(v.VISIBLE);
                            mood_down.startAnimation(fade_in);
                            mood_down.setClickable(true);
                            price_down.setVisibility(v.VISIBLE);
                            price_down.startAnimation(fade_in);
                            price_down.setClickable(true);
                            party_up.setImageResource(R.drawable.ic_thumb_up);
                            location_up.setImageResource(R.drawable.ic_thumb_up);
                            mood_up.setImageResource(R.drawable.ic_thumb_up);
                            price_up.setImageResource(R.drawable.ic_thumb_up);
                            price_up.setImageResource(R.drawable.ic_thumb_up);
                            party_down.setImageResource(R.drawable.ic_thumb_down);
                            location_down.setImageResource(R.drawable.ic_thumb_down);
                            mood_down.setImageResource(R.drawable.ic_thumb_down);
                            price_down.setImageResource(R.drawable.ic_thumb_down);
                            break;

                        case R.id.party_thumb_up:
                            party_down.setVisibility(v.VISIBLE);
                            party_down.startAnimation(fade_in);
                            party_down.setClickable(true);
                            location_down.setVisibility(v.VISIBLE);
                            location_down.startAnimation(fade_in);
                            location_down.setClickable(true);
                            mood_down.setVisibility(v.VISIBLE);
                            mood_down.startAnimation(fade_in);
                            mood_down.setClickable(true);
                            price_down.setVisibility(v.VISIBLE);
                            price_down.startAnimation(fade_in);
                            price_down.setClickable(true);
                            party_up.setVisibility(v.VISIBLE);
                            party_up.startAnimation(fade_in);
                            party_up.setClickable(true);
                            location_up.setVisibility(v.VISIBLE);
                            location_up.startAnimation(fade_in);
                            location_up.setClickable(true);
                            mood_up.setVisibility(v.VISIBLE);
                            mood_up.startAnimation(fade_in);
                            mood_up.setClickable(true);
                            price_up.setVisibility(v.VISIBLE);
                            price_up.startAnimation(fade_in);
                            price_up.setClickable(true);
                            party_up.setImageResource(R.drawable.ic_thumb_up);
                            location_up.setImageResource(R.drawable.ic_thumb_up);
                            mood_up.setImageResource(R.drawable.ic_thumb_up);
                            price_up.setImageResource(R.drawable.ic_thumb_up);
                            party_down.setImageResource(R.drawable.ic_thumb_down);
                            location_down.setImageResource(R.drawable.ic_thumb_down);
                            mood_down.setImageResource(R.drawable.ic_thumb_down);
                            price_down.setImageResource(R.drawable.ic_thumb_down);
                            break;
                    }
                    generalRating = 0;
                    locationRating = 0;
                    priceRating = 0;
                    moodRating = 0;
                    party_voted = false;
                    location_voted = false;
                    mood_voted = false;
                    price_voted = false;
                }
            }
        };

        // Zuweisungen der OnClickListener
        party_up.setOnClickListener(party_vote);
        party_down.setOnClickListener(party_vote);

        // Anderen Button unsichtbar machen, nicht klickbar. Farbe des gewählten Buttons ändern
        // Falls schon gevoted wurde, anderen Button wieder einblenden, klickbar machen, Farben ändern
        location_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (location_voted == false)
                {
                    location_down.setVisibility(v.INVISIBLE);
                    location_down.startAnimation(fade_out);
                    location_down.setClickable(false);
                    location_up.setImageResource(R.drawable.thumb_up_96);
                    locationRating = 1;
                    location_voted=true;

                }else if (location_voted == true)
                {
                    location_down.setVisibility(v.VISIBLE);
                    location_down.startAnimation(fade_in);
                    location_down.setClickable(true);
                    location_up.setImageResource(R.drawable.ic_thumb_up);
                    locationRating = 0;
                    location_voted = false;
                }
            }
        });

        // Anderen Button unsichtbar machen, nicht klickbar. Farbe des gewählten Buttons ändern
        // Falls schon gevoted wurde, anderen Button wieder einblenden, klickbar machen, Farben ändern
        location_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (location_voted == false)
                {
                    location_up.setVisibility(v.INVISIBLE);
                    location_up.startAnimation(fade_out);
                    location_up.setClickable(false);
                    location_down.setImageResource(R.drawable.thumbs_down_96);
                    locationRating = -1;
                    location_voted = true;
                }else if (location_voted == true)
                {
                    location_up.setVisibility(v.VISIBLE);
                    location_up.startAnimation(fade_in);
                    location_up.setClickable(true);
                    location_down.setImageResource(R.drawable.ic_thumb_down);
                    locationRating = 0;
                    location_voted = false;
                }
            }
        });

        // Anderen Button unsichtbar machen, nicht klickbar. Farbe des gewählten Buttons ändern
        // Falls schon gevoted wurde, anderen Button wieder einblenden, klickbar machen, Farben ändern
        mood_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mood_voted == false)
                {
                    mood_down.setVisibility(v.INVISIBLE);
                    mood_down.startAnimation(fade_out);
                    mood_down.setClickable(false);
                    mood_up.setImageResource(R.drawable.thumb_up_96);
                    moodRating = 1;
                    mood_voted = true;

                }else if (mood_voted == true)
                {
                    mood_down.setVisibility(v.VISIBLE);
                    mood_down.startAnimation(fade_in);
                    mood_down.setClickable(true);
                    mood_up.setImageResource(R.drawable.ic_thumb_up);
                    moodRating = 0;
                    mood_voted = false;
                }
            }
        });

        // Anderen Button unsichtbar machen, nicht klickbar. Farbe des gewählten Buttons ändern
        // Falls schon gevoted wurde, anderen Button wieder einblenden, klickbar machen, Farben ändern
        mood_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mood_voted == false)
                {
                    mood_up.setVisibility(v.INVISIBLE);
                    mood_up.startAnimation(fade_out);
                    mood_up.setClickable(false);
                    mood_down.setImageResource(R.drawable.thumbs_down_96);
                    moodRating = -1;
                    mood_voted = true;
                }else if (mood_voted == true)
                {
                    mood_up.setVisibility(v.VISIBLE);
                    mood_up.startAnimation(fade_in);
                    mood_up.setClickable(true);
                    mood_down.setImageResource(R.drawable.ic_thumb_down);
                    moodRating = 0;
                    mood_voted = false;
                }
            }
        });

        // Anderen Button unsichtbar machen, nicht klickbar. Farbe des gewählten Buttons ändern
        // Falls schon gevoted wurde, anderen Button wieder einblenden, klickbar machen, Farben ändern
        price_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (price_voted == false)
                {
                    price_down.setVisibility(v.INVISIBLE);
                    price_down.startAnimation(fade_out);
                    price_down.setClickable(false);
                    price_up.setImageResource(R.drawable.thumb_up_96);
                    priceRating = 1;
                    price_voted = true;
                }else if (price_voted == true)
                {
                    price_down.setVisibility(v.VISIBLE);
                    price_down.startAnimation(fade_in);
                    price_down.setClickable(true);
                    price_up.setImageResource(R.drawable.ic_thumb_up);
                    priceRating = 0;
                    price_voted = false;
                }
            }
        });

        // Anderen Button unsichtbar machen, nicht klickbar. Farbe des gewählten Buttons ändern
        // Falls schon gevoted wurde, anderen Button wieder einblenden, klickbar machen, Farben ändern
        price_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (price_voted == false)
                {
                    price_up.setVisibility(v.INVISIBLE);
                    price_up.startAnimation(fade_out);
                    price_up.setClickable(false);
                    price_down.setImageResource(R.drawable.thumbs_down_96);
                    priceRating = -1;
                    price_voted = true;
                }else if (price_voted == true)
                {
                    price_up.setVisibility(v.VISIBLE);
                    price_up.startAnimation(fade_in);
                    price_up.setClickable(true);
                    price_down.setImageResource(R.drawable.ic_thumb_down);
                    priceRating = 0;
                    price_voted = false;
                }
            }
        });

        return v;
    }

    /**
     * Wird aufgerufen wenn das Rating erfolgreich war, erzeugt eine Nachricht
     * Dies darf nur dann geschehen, solange die Activity greifbar ist. Ansonsten kommt es zu abstürzen.
     */
    @Override
    public void onSuccessPartyRating() {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), "Rating war erfolgreich", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Wird aufgerufen wenn das Rating fehlgeschlagen ist, erzeugt eine Nachricht
     * Dies darf nur dann geschehen, solange die Activity greifbar ist. Ansonsten kommt es zu abstürzen.
     */
    @Override
    public void onFailPartyRating() {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), "Rating war nicht erfolgreich", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Neue Backend Task erzeugen -> PartyRatingTask, sofern "Ok" gedrückt wurde oder ausblenden, falls "Cancel" gedrückt wurde
     * @param v
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.vote_button_ok:
                new PartyRatingTask(this, partyId, new Rating(generalRating, priceRating, locationRating,  moodRating));
                dismiss();
                break;
            case R.id.vote_button_cancel:
                dismiss();
                break;
        }
    }
}
