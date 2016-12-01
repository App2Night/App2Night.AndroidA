package de.dhbw.app2night;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pavelsikun.seekbarpreference.SeekBarPreference;

/**
 * Created by Flo on 27.10.2016.
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals("party_range"))
        {
            //TODO: Implementieren von Settings

        }

        if (key.equals("gps_preference"))
        {
            //TODO: Implementieren von Settings
        }
        if (key.equals("wlan_preference"))
        {
            //TODO: Implementieren von Settings
        }
        if (key.equals("benachrichtung_preference"))
        {
            //TODO: Implementieren von Settings
        }
    }
}

