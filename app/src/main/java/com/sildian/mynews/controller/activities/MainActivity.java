package com.sildian.mynews.controller.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.sildian.mynews.R;
import com.sildian.mynews.controller.fragments.MainFragment;
import com.sildian.mynews.model.UserSettings;
import com.sildian.mynews.utils.NotificationReceiver;
import com.sildian.mynews.view.MainFragmentAdapter;

import java.util.Calendar;

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

    /**Callback methods**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APPLICATION=getApplication();
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.activity_main_toolbar));
        ButterKnife.bind(this);
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
                setNotificationAlarm();
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
        this.viewPager.setAdapter(this.mainFragmentAdapter);
        this.tabLayout.setupWithViewPager(viewPager);
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
}
