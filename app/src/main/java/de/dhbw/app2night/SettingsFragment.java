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
import com.pavelsikun.seekbarpreference.SeekBarPreferenceCompat;

import de.dhbw.exceptions.IllegalKeyException;
import de.dhbw.utils.PropertyUtil;
import de.dhbw.utils.SettingsUtil;

/**
 * Created by Flo on 27.10.2016.
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    SettingsUtil settingsUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsUtil = SettingsUtil.getInstance();
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals("radius"))
        {
            SeekBarPreference range = (SeekBarPreference) findPreference(key);
            String value = Integer.toString(range.getCurrentValue());

            try
            {
                settingsUtil.putSettingString(key, value);
            }catch(IllegalKeyException e) {
            }
        }

        if (key.equals("gps_preference"))
        {
            Preference gps = findPreference(key);
            //TODO: Implementieren von Settings
        }

        if (key.equals("wlan_preference"))
        {
            Preference gps = findPreference(key);
            //TODO: Implementieren von Settings
        }

        if (key.equals("benachrichtung_preference"))
        {
            Preference gps = findPreference(key);
            //TODO: Implementieren von Settings
        }
    }
}

