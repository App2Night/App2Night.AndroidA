package de.dhbw.app2night;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import de.dhbw.backendTasks.party.PostParty;
import de.dhbw.backendTasks.party.PostPartyTask;
import de.dhbw.model.Party;
import de.dhbw.model.PartyDisplay;

/**
 * Created by Flo on 31.10.2016.
 */
public class AddEventFragment extends Fragment implements View.OnTouchListener, View.OnClickListener,
       TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, PostParty{
    EditText editTextPartyName, editTextStreetName, editTextHouseNumber, editTextZipCode, editTextCityName, editTextCountryName, editTextDescription;
    TextView textDate, textTime;
    ScrollView scrollViewAddEvent;
    Spinner spinnerPartyType, spinnerMusicGenre;
    View rootView;
    String partyDate;
    DatePickerDialog dpd;
    TimePickerDialog tpd;
    Button okButton;


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
        rootView = inflater.inflate(R.layout.fragment_addevent, container, false);

        initializeViews();

        // Inflate the layout for this fragment
        return rootView;
    }

    private void initializeViews() {
        editTextDescription = (EditText) rootView.findViewById(R.id.input_description);
        editTextDescription.setOnTouchListener(this);
        textDate = (TextView) rootView.findViewById(R.id.input_party_date);
        textDate.setOnClickListener(this);
        textTime = (TextView) rootView.findViewById(R.id.input_party_time);
        textTime.setOnClickListener(this);

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

        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                AddEventFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        tpd = TimePickerDialog.newInstance(
                AddEventFragment.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );

        okButton = (Button) rootView.findViewById(R.id.addevent_button_ok);
        okButton.setOnClickListener(this);

        editTextPartyName = (EditText) rootView.findViewById(R.id.input_party_name);
        editTextStreetName = (EditText) rootView.findViewById(R.id.input_street_name);
        editTextHouseNumber = (EditText) rootView.findViewById(R.id.input_house_number);
        editTextZipCode = (EditText) rootView.findViewById(R.id.input_zipcode);
        editTextCityName = (EditText) rootView.findViewById(R.id.input_city_name);
        editTextCountryName = (EditText) rootView.findViewById(R.id.input_country_name);
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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.input_party_date:
                dpd.show(getFragmentManager(), "DatePickerDialog");
                break;
            case R.id.input_party_time:
                tpd.show(getFragmentManager(), "TimePickerDialog");
                break;
            case R.id.addevent_button_ok:
                runPartyCheck();
        }
    }

    private void runPartyCheck() {
        PartyDisplay partyDisplay = new PartyDisplay();
        Boolean correctInput = true;

        partyDisplay.setPartyName(editTextPartyName.getText().toString());
        partyDisplay.setStreetName(editTextStreetName.getText().toString());
        partyDisplay.setHouseNumber(editTextHouseNumber.getText().toString());
        partyDisplay.setZipcode(editTextZipCode.getText().toString());
        partyDisplay.setCityName(editTextCityName.getText().toString());
        partyDisplay.setCountryName(editTextCountryName.getText().toString());
        partyDisplay.setPartyDate(partyDate);
        //TODO: Verbesserung durchfuehren, nur zum Testen
        partyDisplay.setPartyType(spinnerPartyType.getSelectedItemPosition()-1);
        partyDisplay.setMusicGenre(spinnerMusicGenre.getSelectedItemPosition()-1);

        partyDisplay.setDescription(editTextDescription.getText().toString());

        new PostPartyTask(this, partyDisplay);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        //Speichern des erfassten Datums fuer Party und setzen in Eingabefeld
        partyDate = year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
        textDate.setText(dayOfMonth + "." + (monthOfYear+1) + "." + year);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        //Speichern der erfassten Zeit fuer Party und setzen in Eingabefeld
        partyDate = partyDate + "T" + hourOfDay + ":" + minute + ":" + second;
        textTime.setText(hourOfDay + ":" + minute);
    }

    @Override
    public void onFinishPostParty(Party party) {

    }
}
