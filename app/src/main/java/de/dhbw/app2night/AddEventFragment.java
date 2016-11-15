package de.dhbw.app2night;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;

/**
 * Created by Flo on 31.10.2016.
 */
public class AddEventFragment extends Fragment implements View.OnTouchListener{
    EditText editTextDescription;
    ScrollView scrollViewAddEvent;
    Spinner spinnerPartyType;
    Spinner spinnerMusicGenre;

    public AddEventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_addevent, container, false);

        editTextDescription = (EditText) rootView.findViewById(R.id.input_description);
        editTextDescription.setOnTouchListener(this);
        scrollViewAddEvent = (ScrollView) rootView.findViewById(R.id.addevent_scrollview_main);

        spinnerPartyType = (Spinner) rootView.findViewById(R.id.spinner_party_type);
    // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterPartyType = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.party_type, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
        adapterPartyType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
        spinnerPartyType.setAdapter(adapterPartyType);

        spinnerMusicGenre = (Spinner) rootView.findViewById(R.id.spinner_music_genre);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterMusicGenre = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.music_genre, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterMusicGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerMusicGenre.setAdapter(adapterMusicGenre);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        scrollViewAddEvent.requestDisallowInterceptTouchEvent(true);

        int action = motionEvent.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_UP:
                scrollViewAddEvent.requestDisallowInterceptTouchEvent(false);
                break;
        }

        return false;
    }
}
