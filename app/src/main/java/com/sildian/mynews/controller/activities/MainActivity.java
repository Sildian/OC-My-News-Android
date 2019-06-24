package com.sildian.mynews.controller.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.sildian.mynews.R;
import com.sildian.mynews.model.UserSettings;
import com.sildian.mynews.view.MainFragmentAdapter;

/*************************************************************************************************
 * MainActivity
 * This activity displays fragments running queries to get lists of articles from the
 * New York Times API.
 ************************************************************************************************/

public class MainActivity extends AppCompatActivity {

    /**Static attributes**/

    public static Application APPLICATION;

    /**Keys used to transfer data within intents**/

    public static final String KEY_SETTINGS_ID="KEY_SETTINGS_ID";
    public static final String KEY_SETTINGS_USER="KEY_SETTINGS_USER";

    public static final int KEY_RESULT_SETTINGS=10;

    /**Other attributes**/

    UserSettings userSettings;                                  //The user settings

    /**Callback methods**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APPLICATION=getApplication();
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.activity_main_toolbar));
        initializeUserSettings();
        initializeViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent settingsActivityIntent=new Intent(this, SettingsActivity.class);

        switch(item.getItemId()){
            case R.id.menu_main_add:
                settingsActivityIntent.putExtra(KEY_SETTINGS_ID, SettingsActivity.ID_SHEETS);
                break;
            case R.id.menu_main_search:
                settingsActivityIntent.putExtra(KEY_SETTINGS_ID, SettingsActivity.ID_SEARCH);
                break;
            case R.id.menu_main_notifications:
                settingsActivityIntent.putExtra(KEY_SETTINGS_ID, SettingsActivity.ID_NOTIFICATIONS);
                break;
            default:
                break;
        }

        settingsActivityIntent.putExtra(KEY_SETTINGS_USER, this.userSettings);
        startActivityForResult(settingsActivityIntent, KEY_RESULT_SETTINGS);

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(requestCode){
            case KEY_RESULT_SETTINGS:
                this.userSettings=data.getParcelableExtra(KEY_SETTINGS_USER);
                break;
        }
    }

    /**Initializes the user settings**/

    private void initializeUserSettings(){
        this.userSettings=new UserSettings();
    }

    /**Initializes the view pager and displays the fragments**/

    private void initializeViewPager(){
        ViewPager viewPager=findViewById(R.id.activity_main_view_pager);
        viewPager.setAdapter(new MainFragmentAdapter(getSupportFragmentManager()));
        TabLayout tabLayout=findViewById(R.id.activity_main_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
