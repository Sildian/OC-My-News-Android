package com.sildian.mynews.controller.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sildian.mynews.R;
import com.sildian.mynews.controller.fragments.ArticleFragment;
import com.sildian.mynews.controller.fragments.SettingsBaseFragment;

/*************************************************************************************************
 * SettingsActivity
 * This activity shows the settings options.
 * The settings options can be shown to add new sheets,
 * to search articles or to activate/deactivate notifications
 *************************************************************************************************/

public class SettingsActivity extends AppCompatActivity {

    /**The fragment**/

    private SettingsBaseFragment settingsFragment;

    /**Callback methods**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setSupportActionBar(findViewById(R.id.activity_settings_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        displaySettingsFragment();
    }

    /**Displays the fragment**/

    private void displaySettingsFragment(){
        //Todo : Displays the child fragments
        this.settingsFragment=(SettingsBaseFragment)getSupportFragmentManager().findFragmentById(R.id.activity_settings_fragment);
        if(this.settingsFragment==null){
            this.settingsFragment=new SettingsBaseFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_settings_fragment, this.settingsFragment)
                    .commit();
        }
    }
}
