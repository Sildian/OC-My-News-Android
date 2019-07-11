package com.sildian.mynews.controller.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sildian.mynews.R;
import com.sildian.mynews.controller.fragments.MainFragment;
import com.sildian.mynews.model.UserSettings;
import com.sildian.mynews.utils.NotificationAlarmReceiver;
import com.sildian.mynews.view.MainFragmentAdapter;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*************************************************************************************************
 * MainActivity
 * This activity displays fragments running queries to get lists of articles from the
 * New York Times API.
 ************************************************************************************************/

public class MainActivity extends AppCompatActivity {

    /**Static attributes**/

    public static Application APPLICATION;

    /**Keys used to save and load data**/

    public static final String KEY_FILE_USER_SETTINGS="KEY_FILE_USER_SETTINGS";
    public static final String KEY_FILE_CHECKED_ARTICLES="KEY_FILE_CHECKED_ARTICLES";

    /**Keys used to transfer data within intents**/

    public static final String KEY_SETTINGS_ID="KEY_SETTINGS_ID";
    public static final String KEY_SETTINGS_USER="KEY_SETTINGS_USER";
    public static final String KEY_NOTIFICATION_USER="KEY_NOTIFICATION_USER";

    public static final int KEY_RESULT_SETTINGS=10;

    /**Events**/

    public static final String EVENT_NOTIFICATION_ALARM="com.sildian.mynews.notificationAlarm";
    public static final String EVENT_NOTIFICATION_START="com.sildian.mynews.notificationStart";

    /**View pager items**/

    private WeakReference<MainFragmentAdapter> mainFragmentAdapter;
    private WeakReference<ViewPager> viewPager;
    private WeakReference<TabLayout> tabLayout;

    /**Receivers to be registered (only for Oreo and higher SDK versions)**/

    private NotificationAlarmReceiver notificationAlarmReceiver;

    /**Other attributes**/

    private UserSettings userSettings;                      //The user settings
    private List<String> checkedArticlesUrls;               //The list of all URL related to already checked articles

    /**Callback methods**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APPLICATION=getApplication();
        registerReceivers();
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.activity_main_toolbar));
        loadUserSettings();
        loadCheckedArticlesUrls();
        initializeViewPager();
    }

    @Override
    protected void onDestroy() {
        saveUserSettings();
        saveCheckedArticlesUrls();
        unregisterReceivers();
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
            case R.id.menu_main_help:
                showHelpDialog();
                return super.onOptionsItemSelected(item);
            case R.id.menu_main_about:
                showAboutDialog();
                return super.onOptionsItemSelected(item);
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
                    this.mainFragmentAdapter.get().updateUserSettings(this.userSettings);
                    int id = data.getIntExtra(MainActivity.KEY_SETTINGS_ID, 0);
                    startTaskAfterSettingsActivityResult(id);
                }
                break;
            default:
                break;
        }
    }

    /**Registers receivers (only for Oreo and higher versions)**/

    private void registerReceivers(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            IntentFilter notificationAlarmFilter=new IntentFilter();
            notificationAlarmFilter.addAction(EVENT_NOTIFICATION_ALARM);
            notificationAlarmFilter.addAction(Intent.ACTION_BOOT_COMPLETED);
            this.notificationAlarmReceiver=new NotificationAlarmReceiver();
            registerReceiver(this.notificationAlarmReceiver, notificationAlarmFilter);
        }
    }

    /**Unregister receivers (only for Oreo and higher versions)**/

    private void unregisterReceivers(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            unregisterReceiver(this.notificationAlarmReceiver);
        }
    }

    /**Defines a task to be proceeded after receiving a result from SettingsActivity
     * @param id : the id received from SettingsActivity
     */

    private void startTaskAfterSettingsActivityResult(int id){
        switch(id){
            case SettingsActivity.ID_SHEETS:
                if(this.viewPager.get().getCurrentItem()!= MainFragment.ID_TOP_STORIES) {
                    this.viewPager.get().setCurrentItem(MainFragment.ID_TOP_STORIES, true);
                }
                break;
            case SettingsActivity.ID_SEARCH:
                if(this.viewPager.get().getCurrentItem()!= MainFragment.ID_SEARCH) {
                    this.viewPager.get().setCurrentItem(MainFragment.ID_SEARCH, true);
                    this.viewPager.get().getAdapter().notifyDataSetChanged();
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
        this.mainFragmentAdapter=new WeakReference<>(new MainFragmentAdapter(getSupportFragmentManager(), this.userSettings));
        this.viewPager=new WeakReference<>(findViewById(R.id.activity_main_view_pager));
        this.tabLayout=new WeakReference<>(findViewById(R.id.activity_main_tab_layout));
        this.viewPager.get().setAdapter(this.mainFragmentAdapter.get());
        this.tabLayout.get().setupWithViewPager(viewPager.get());
        this.tabLayout.get().setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    /**Activates or deactivates the alarm allowing to send notification to the phone**/

    private void setNotificationAlarm(){
        Intent notificationAlarmReceiverIntent = new Intent(EVENT_NOTIFICATION_ALARM);
        notificationAlarmReceiverIntent.putExtra(KEY_NOTIFICATION_USER, this.userSettings);
        sendBroadcast(notificationAlarmReceiverIntent);
    }

    /**Shows a dialog box about help*/

    private void showHelpDialog(){
        AlertDialog.Builder helpDialog=new AlertDialog.Builder(this);
        helpDialog.setTitle(R.string.dialog_help_title);
        helpDialog.setMessage(R.string.dialog_help_message);
        helpDialog.setNeutralButton(R.string.dialog_neutral_button_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        helpDialog.create().show();
    }

    /**Shows a dialog box about the app*/

    private void showAboutDialog(){
        AlertDialog.Builder aboutDialog=new AlertDialog.Builder(this);
        aboutDialog.setTitle(R.string.dialog_about_title);
        aboutDialog.setMessage(R.string.dialog_about_message);
        aboutDialog.setNeutralButton(R.string.dialog_neutral_button_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        aboutDialog.create().show();
    }

    /**Loads the user settings if already exists.**/

    private void loadUserSettings(){

        Gson gson=new Gson();
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

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
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences.edit().putString(KEY_FILE_USER_SETTINGS, userSettingsJson).apply();
    }

    /**Loads the URL of all articles which are already checked if exist**/

    public void loadCheckedArticlesUrls(){

        Gson gson=new Gson();
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

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
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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
