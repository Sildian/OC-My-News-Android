package com.sildian.mynews.controller.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sildian.mynews.R;
import com.sildian.mynews.controller.fragments.MainFragment;
import com.sildian.mynews.model.UserSettings;
import com.sildian.mynews.utils.NotificationReceiver;
import com.sildian.mynews.view.MainFragmentAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*************************************************************************************************
 * MainActivity
 * This activity displays fragments running queries to get lists of articles from the
 * New York Times API.
 ************************************************************************************************/

public class MainActivity extends AppCompatActivity {

    /**Static attributes**/

    public static Application APPLICATION;

    /**Keys used to save and load data**/

    public static final String KEY_FILE_NAME_USER_SETTINGS="user_settings.xml";
    public static final String KEY_FILE_USER_SETTINGS="KEY_FILE_USER_SETTINGS";
    public static final String KEY_FILE_NAME_CHECKED_ARTICLES="checked_articles.xml";
    public static final String KEY_FILE_CHECKED_ARTICLES="KEY_FILE_CHECKED_ARTICLES";

    /**Keys used to transfer data within intents**/

    public static final String KEY_SETTINGS_ID="KEY_SETTINGS_ID";
    public static final String KEY_SETTINGS_USER="KEY_SETTINGS_USER";
    public static final String KEY_NOTIFICATION_USER="KEY_NOTIFICATION_USER";

    public static final int KEY_RESULT_SETTINGS=10;

    /**View pager items**/

    private MainFragmentAdapter mainFragmentAdapter;
    @BindView (R.id.activity_main_view_pager) ViewPager viewPager;
    @BindView (R.id.activity_main_tab_layout) TabLayout tabLayout;

    /**Other attributes**/

    private UserSettings userSettings;                      //The user settings
    private List<String> checkedArticlesUrls;               //The list of all URL related to already checked articles

    /**Callback methods**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APPLICATION=getApplication();
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.activity_main_toolbar));
        ButterKnife.bind(this);
        loadUserSettings();
        loadCheckedArticlesUrls();
        initializeViewPager();
    }

    @Override
    protected void onDestroy() {
        saveUserSettings();
        saveCheckedArticlesUrls();
        super.onDestroy();
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
                if(resultCode==RESULT_OK) {
                    this.userSettings = data.getParcelableExtra(KEY_SETTINGS_USER);
                    this.mainFragmentAdapter.updateUserSettings(this.userSettings);
                    int id = data.getIntExtra(MainActivity.KEY_SETTINGS_ID, 0);
                    startTaskAfterSettingsActivityResult(id);
                }
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
                if(this.viewPager.getCurrentItem()!= MainFragment.ID_TOP_STORIES) {
                    this.viewPager.setCurrentItem(MainFragment.ID_TOP_STORIES, true);
                }
                break;
            case SettingsActivity.ID_SEARCH:
                if(this.viewPager.getCurrentItem()!= MainFragment.ID_SEARCH) {
                    this.viewPager.setCurrentItem(MainFragment.ID_SEARCH, true);
                    this.viewPager.getAdapter().notifyDataSetChanged();
                }
                break;
            case SettingsActivity.ID_NOTIFICATIONS:
                setNotificationAlarm();
                break;
            default:
                break;
        }
    }

    /**Initializes the view pager and displays the fragments**/

    private void initializeViewPager(){
        this.mainFragmentAdapter=new MainFragmentAdapter(getSupportFragmentManager(), this.userSettings);
        this.viewPager.setAdapter(this.mainFragmentAdapter);
        this.tabLayout.setupWithViewPager(viewPager);
        this.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    /**Activates or deactivates the alarm allowing to send notification to the phone**/

    private void setNotificationAlarm(){

        //TODO : change the alarm first start and interval

        /*Creates the alarm manager*/

        AlarmManager notificationAlarm=(AlarmManager) getSystemService(ALARM_SERVICE);

        /*Creates an intent allowing to start NotificationReceiver*/

        Intent notificationReceiverIntent = new Intent(MainActivity.this, NotificationReceiver.class);
        notificationReceiverIntent.putExtra(KEY_NOTIFICATION_USER, this.userSettings);
        PendingIntent notificationReceiverPendingIntent = PendingIntent.getBroadcast(this, 0, notificationReceiverIntent, 0);

        /*If the notification is on, activates the alarm. Else, deactivates it.*/

        if(this.userSettings.getNotificationOn()) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, 1);
            notificationAlarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60000, notificationReceiverPendingIntent);
        }
        else{
            notificationAlarm.cancel(notificationReceiverPendingIntent);
        }
    }

    /**Loads the user settings if already exists.**/

    private void loadUserSettings(){

        Gson gson=new Gson();
        SharedPreferences sharedPreferences=getSharedPreferences(KEY_FILE_NAME_USER_SETTINGS, MODE_PRIVATE);

        if(sharedPreferences.contains(KEY_FILE_USER_SETTINGS)){
            String userSettingsJson=sharedPreferences.getString(KEY_FILE_USER_SETTINGS, null);
            this.userSettings=gson.fromJson(userSettingsJson, UserSettings.class);
        }
        else{
            this.userSettings=new UserSettings();
        }
    }

    /**Saves the user settings**/

    private void saveUserSettings(){
        Gson gson=new Gson();
        String userSettingsJson=gson.toJson(this.userSettings);
        SharedPreferences sharedPreferences=getSharedPreferences(KEY_FILE_NAME_USER_SETTINGS, MODE_PRIVATE);
        sharedPreferences.edit().putString(KEY_FILE_USER_SETTINGS, userSettingsJson).apply();
    }

    /**Loads the URL of all articles which are already checked if exist**/

    public void loadCheckedArticlesUrls(){

        Gson gson=new Gson();
        SharedPreferences sharedPreferences=getSharedPreferences(KEY_FILE_NAME_CHECKED_ARTICLES, MODE_PRIVATE);

        if(sharedPreferences.contains(KEY_FILE_CHECKED_ARTICLES)){
            String checkedArticlesUrlsJson=sharedPreferences.getString(KEY_FILE_CHECKED_ARTICLES, null);
            Type stringCollection = new TypeToken<Collection<String>>(){}.getType();
            this.checkedArticlesUrls=gson.fromJson(checkedArticlesUrlsJson, stringCollection);
        }
        else{
            this.checkedArticlesUrls=new ArrayList<>();
        }
    }

    /**Saves the URL of all articles which are already checked**/

    public void saveCheckedArticlesUrls(){
        Gson gson=new Gson();
        String checkedArticlesUrlJson=gson.toJson(this.checkedArticlesUrls);
        SharedPreferences sharedPreferences=getSharedPreferences(KEY_FILE_NAME_CHECKED_ARTICLES, MODE_PRIVATE);
        sharedPreferences.edit().putString(KEY_FILE_CHECKED_ARTICLES, checkedArticlesUrlJson).apply();
    }

    /**Adds an url to the list of checked articles urls
     * @param url : the article's url to be added to the list
     */

    public void addCheckedArticleUrl(String url){
        this.checkedArticlesUrls.add(url);
    }

    /**Getters**/

    public List<String> getCheckedArticlesUrls() {
        return checkedArticlesUrls;
    }
}
