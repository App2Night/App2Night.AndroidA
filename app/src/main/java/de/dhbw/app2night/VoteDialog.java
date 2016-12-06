
package de.dhbw.app2night;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

/**
 * Created by SchmidtRo on 06.12.2016.
 */

public class VoteDialog extends DialogFragment {

    ImageView party_up, party_down, location_up, location_down, mood_up, mood_down, price_up, price_down;
    boolean party_voted = false;
    boolean location_voted = false;
    boolean mood_voted = false;
    boolean price_voted = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_voting, container, false);

        party_up = (ImageView) v.findViewById(R.id.party_thumb_up);
        party_down = (ImageView) v.findViewById(R.id.party_thumb_down);
        location_up = (ImageView) v.findViewById(R.id.location_up);
        location_down = (ImageView) v.findViewById(R.id.location_down);
        mood_up = (ImageView) v.findViewById(R.id.mood_up);
        mood_down = (ImageView) v.findViewById(R.id.mood_down);
        price_up = (ImageView) v.findViewById(R.id.price_up);
        price_down = (ImageView) v.findViewById(R.id.price_down);

        final Animation fade_in = AnimationUtils.loadAnimation(getActivity(), R.anim.image_fade_in);
        final Animation fade_out = AnimationUtils.loadAnimation(getActivity(), R.anim.image_fade_out);

        View.OnClickListener party_vote = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView button = (ImageView) v.findViewById(v.getId());

                if (!party_voted) {
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
                    party_voted = true;

                } else if (party_voted) {
                    button.setVisibility(v.VISIBLE);
                    button.startAnimation(fade_in);
                    button.setClickable(true);
                    location_down.setVisibility(v.VISIBLE);
                    location_down.startAnimation(fade_in);
                    location_down.setClickable(true);
                    mood_down.setVisibility(v.VISIBLE);
                    mood_down.startAnimation(fade_in);
                    mood_down.setClickable(true);
                    price_down.setVisibility(v.VISIBLE);
                    price_down.startAnimation(fade_in);
                    price_down.setClickable(true);
                    party_voted = false;
                }

            }
        };

        party_up.setOnClickListener(party_vote);
        party_down.setOnClickListener(party_vote);



        location_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (location_voted == false)
                {
                    location_down.setVisibility(v.INVISIBLE);
                    location_down.setClickable(false);
                    location_voted=true;

                }else if (location_voted == true)
                {
                    location_down.setVisibility(v.VISIBLE);
                    location_down.setClickable(true);
                    location_voted = false;
                }
            }
        });

        location_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (location_voted == false)
                {
                    location_up.setVisibility(v.INVISIBLE);
                    location_up.setClickable(false);
                    location_voted = true;
                }else if (location_voted == true)
                {
                    location_up.setVisibility(v.VISIBLE);
                    location_up.setClickable(true);
                    location_voted = false;
                }
            }
        });

        mood_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mood_voted == false)
                {
                    mood_down.setVisibility(v.INVISIBLE);
                    mood_down.setClickable(false);
                    mood_voted = true;
                }else if (location_voted == true)
                {
                    mood_down.setVisibility(v.VISIBLE);
                    mood_down.setClickable(true);
                    mood_voted = false;
                }
            }
        });

        mood_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mood_voted == false)
                {
                    mood_up.setVisibility(v.INVISIBLE);
                    mood_up.setClickable(false);
                    mood_voted = true;
                }else if (mood_voted == true)
                {
                    mood_up.setVisibility(v.VISIBLE);
                    mood_up.setClickable(true);
                    mood_voted = false;
                }
            }
        });

        price_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (price_voted == false)
                {
                    price_down.setVisibility(v.INVISIBLE);
                    price_down.setClickable(false);
                    price_voted = true;
                }else if (price_voted == true)
                {
                    price_down.setVisibility(v.VISIBLE);
                    price_down.setClickable(true);
                    price_voted = false;
                }
            }
        });

        price_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (price_voted == false)
                {
                    price_up.setVisibility(v.INVISIBLE);
                    price_up.setClickable(false);
                    price_voted = true;
                }else if (mood_voted == true)
                {
                    price_up.setVisibility(v.VISIBLE);
                    price_up.setClickable(true);
                    price_voted = false;
                }
            }
        });
        return v;
    }
}
