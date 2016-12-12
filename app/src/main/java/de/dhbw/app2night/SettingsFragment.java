package de.dhbw.app2night;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pavelsikun.seekbarpreference.SeekBarPreference;

import de.dhbw.exceptions.IllegalKeyException;
import de.dhbw.utils.SettingsUtil;

/**
 * Created by Flo on 27.10.2016.
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    //Variablen
    SettingsUtil settingsUtil;

    /**
     * Wird beim erstellen aufgerufen, verknüpft die xml mit diesem File.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsUtil = SettingsUtil.getInstance();
        addPreferencesFromResource(R.xml.preferences);

    }

    /**
     * Wird aufgerufen, sobald die View erstellt wird
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * OnChangedListener für die Einstellungen, übergibt den geänderten Wert an die SettingsUtil
     * @param sharedPreferences: Instanz der geänderten Einstellungen
     * @param key: Key der geänderten Einstellungsoption
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals("radius")) {
            SeekBarPreference range = (SeekBarPreference) findPreference(key);
            String value = Integer.toString(range.getCurrentValue());
            try {
                settingsUtil.putSettingString(key, value);
            } catch (IllegalKeyException e) {
            }
        }

    }

    /**
     * Wird aufgerufen, wenn das Fragment fortgesetzt wird, verknüpft den OnChangeListener
     */
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    /**
     * Wird auferufen, wenn das Fragment pausiert wird, löscht den OnChangeListener
     */
    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}

