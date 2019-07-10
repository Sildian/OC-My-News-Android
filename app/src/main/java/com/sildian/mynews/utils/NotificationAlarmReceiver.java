package com.sildian.mynews.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.sildian.mynews.controller.activities.MainActivity;
import com.sildian.mynews.model.UserSettings;

import java.util.Calendar;
import java.util.TimeZone;

/*************************************************************************************************
 * NotificationAlarmReceiver
 * Activates or deactivates the alarm allowing to send notifications to the phone.
 * This receiver is called by MainActivity or automatically after the phone reboots.
 ************************************************************************************************/

public class NotificationAlarmReceiver extends BroadcastReceiver {

    /**Attributes**/

    private Context context;                                //The context
    private UserSettings userSettings;                      //The user settings

    /**Callback methods**/

    @Override
    public void onReceive(Context context, Intent intent) {

        /*Stores the context*/

        this.context=context;

        if(intent!=null) {

            /*Defines how to get the user settings depending on which action is received*/

            switch(intent.getAction()) {
                case MainActivity.EVENT_NOTIFICATION_ALARM:
                    Log.d("CHECK_ALARM", intent.getAction());
                    this.userSettings = intent.getParcelableExtra(MainActivity.KEY_NOTIFICATION_USER);
                    break;
                case Intent.ACTION_BOOT_COMPLETED:
                    Log.d("CHECK_ALARM", intent.getAction());
                    SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                    loadUserSettings(sharedPreferences);
                    break;
                default:
                    Log.d("CHECK_ALARM", intent.getAction());
                    break;
            }

            /*Then sets the alarm*/

            setAlarm();
        }
    }

    /**Activates or deactivates the alarm**/

    private void setAlarm(){

        /*Creates the alarm manager*/

        AlarmManager notificationAlarm = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);

        /*Creates an intent allowing to start NotificationReceiver*/

        Intent notificationReceiverIntent = new Intent(this.context, NotificationReceiver.class);
        notificationReceiverIntent.putExtra(MainActivity.KEY_NOTIFICATION_USER, this.userSettings);
        PendingIntent notificationReceiverPendingIntent = PendingIntent.getBroadcast(this.context, 0, notificationReceiverIntent, 0);

        /*If the notification is on, activates the alarm. Else, deactivates it.*/

        if (this.userSettings.getNotificationOn()) {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Puerto_Rico"));
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            notificationAlarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, notificationReceiverPendingIntent);
        } else {
            notificationAlarm.cancel(notificationReceiverPendingIntent);
        }
    }

    /**Loads the user settings from shared preferences
     * @param sharedPreferences : the file containing the data
     */

    private void loadUserSettings(SharedPreferences sharedPreferences){

        Gson gson=new Gson();

        if(sharedPreferences.contains(MainActivity.KEY_FILE_USER_SETTINGS)){
            String userSettingsJson=sharedPreferences.getString(MainActivity.KEY_FILE_USER_SETTINGS, null);
            this.userSettings=gson.fromJson(userSettingsJson, UserSettings.class);
        }
        else{
            this.userSettings=new UserSettings();
        }
    }
}
