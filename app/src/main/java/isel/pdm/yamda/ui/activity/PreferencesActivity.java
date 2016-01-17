package isel.pdm.yamda.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import isel.pdm.yamda.R;
import isel.pdm.yamda.YamdaApplication;
import isel.pdm.yamda.ui.activity.base.ToolbarActivity;
import isel.pdm.yamda.ui.fragment.PreferencesFragment;

/**
 * Class used to store shared preferences of some details of the application
 * Uses a PreferencesFragment
 */
public class PreferencesActivity extends ToolbarActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.enableBackButton();

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);

        getFragmentManager().beginTransaction()
                            .replace(R.id.content, new PreferencesFragment()).commit();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        int days = Integer.parseInt(sharedPreferences.getString(key, "7"));

        if(key.equals(getResources().getString(R.string.soon_periodicity))){
            ((YamdaApplication)getApplication()).refreshSoonAlarm(days);
        } else if(key.equals(getResources().getString(R.string.soon_periodicity))){
            ((YamdaApplication)getApplication()).refreshTheatersAlarm(days);
        }
    }
}
