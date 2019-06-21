package com.sildian.mynews.controller.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sildian.mynews.R;
import com.sildian.mynews.controller.fragments.SettingsBaseFragment;
import com.sildian.mynews.controller.fragments.SettingsSheetsFragment;

/*************************************************************************************************
 * SettingsActivity
 * This activity shows the settings options.
 * The settings options can be shown to add new sheets,
 * to search articles or to activate/deactivate notifications
 *************************************************************************************************/

public class SettingsActivity extends AppCompatActivity {

    /**The id will define which fragment is shown**/

    public static final int ID_SHEETS=0;                 //SettingsSheetsFragment will be shown
    public static final int ID_SEARCH=1;                 //SettingsSearchFragment will be shown
    public static final int ID_NOTIFICATIONS=2;          //SettingsNotificationsFragment will be shown

    /**The fragment**/

    private SettingsBaseFragment settingsFragment;

    /**Attributes**/

    private int id;                                     //This id defines which fragment will be shown

    /**Callback methods**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setSupportActionBar(findViewById(R.id.activity_settings_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.id=getIntent().getIntExtra(MainActivity.KEY_SETTINGS_ID, ID_SHEETS);
        displaySettingsFragment();
    }

    /**Displays the fragment depending on the id**/

    private void displaySettingsFragment(){
        String[] settingsPagesNames=getResources().getStringArray(R.array.settings_pages_names);
        getSupportActionBar().setTitle(settingsPagesNames[id]);
        switch(this.id){
            case ID_SHEETS:
                this.settingsFragment=(SettingsSheetsFragment)getSupportFragmentManager().findFragmentById(R.id.activity_settings_fragment);
                if(this.settingsFragment==null){
                    this.settingsFragment=new SettingsSheetsFragment();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.activity_settings_fragment, this.settingsFragment)
                            .commit();
                }
                break;
            case ID_SEARCH:
                break;
            case ID_NOTIFICATIONS:
                break;
            default:
                break;
        }
    }
}
