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
import com.sildian.mynews.controller.fragments.MainFragment;
import com.sildian.mynews.model.UserSettings;
import com.sildian.mynews.view.MainFragmentAdapter;

import java.util.Set;

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

    private UserSettings userSettings;                      //The user settings
    private ViewPager viewPager;                            //The view pager monitoring the fragments
    private MainFragmentAdapter mainFragmentAdapter;        //The adapter monitoring the fragments

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
                this.mainFragmentAdapter.updateUserSettings(this.userSettings);
                int id=data.getIntExtra(MainActivity.KEY_SETTINGS_ID, 0);
                startTaskAfterSettingsActivityResult(id);
                break;
            default:
                break;
        }
    }

    /**Defines a task to be proceeded after receiving a result from SettingsActivity
     * @param id : the id received from SettingsActivity
     */

    private void startTaskAfterSettingsActivityResult(int id){
        switch(id){
            case SettingsActivity.ID_SHEETS:
                break;
            case SettingsActivity.ID_SEARCH:
                if(this.viewPager.getCurrentItem()!= MainFragment.ID_SEARCH) {
                    this.viewPager.setCurrentItem(MainFragment.ID_SEARCH, true);
                    this.viewPager.getAdapter().notifyDataSetChanged();
                }
                break;
            case SettingsActivity.ID_NOTIFICATIONS:
                break;
            default:
                break;
        }
    }

    /**Initializes the user settings**/

    private void initializeUserSettings(){
        this.userSettings=new UserSettings();
    }

    /**Initializes the view pager and displays the fragments**/

    private void initializeViewPager(){
        this.mainFragmentAdapter=new MainFragmentAdapter(getSupportFragmentManager(), this.userSettings);
        this.viewPager=findViewById(R.id.activity_main_view_pager);
        this.viewPager.setAdapter(this.mainFragmentAdapter);
        TabLayout tabLayout=findViewById(R.id.activity_main_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
