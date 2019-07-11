package com.sildian.mynews.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.Gson;
import com.sildian.mynews.R;
import com.sildian.mynews.controller.activities.MainActivity;
import com.sildian.mynews.model.UserSettings;
import com.sildian.mynews.model.articles_search_api.SearchAPIResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/*************************************************************************************************
 * NotificationReceiver
 * When called, this receiver run an Article search request using
 * the user settings notification keywords and sections, and using the date of today.
 * Then if at least one article is found, send a notification to the phone.
 ************************************************************************************************/

public class NotificationReceiver extends BroadcastReceiver {

    /**Notification information**/

    public static final String CHANEL_NOTIFICATION="CHANEL_NOTIFICATION";
    public static final String CHANEL_NAME="My news notification";
    public static final int NOTIFICATION_ID=10;

    /**Attributes**/

    private Context context;                        //The context
    private UserSettings userSettings;              //The user settings
    private Disposable disposable;                  //The disposable which gets the response

    /**Callback methods**/

    @Override
    public void onReceive(Context context, Intent intent) {

        /*Stores the context*/

        this.context = context;

        /*Gets the data from the intent*/

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        loadUserSettings(sharedPreferences);

        /*Sets the parameters to be sent to the query*/

        String keyWords = this.userSettings.getNotificationKeyWords();
        ArrayList<String> sections = this.userSettings.getNotificationSections();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Puerto_Rico"));
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String beginDate = Utilities.generateDate("yyyyMMdd", year, month, day);
        String endDate = Utilities.generateDate("yyyyMMdd", year, month, day);

        /*Runs the query*/

        runSearchArticlesRequest(keyWords, sections, beginDate, endDate);

    }

    /**Runs Article Search API request**/

    private void runSearchArticlesRequest(String keyWords, List<String> sections, String beginDate, String endDate) {
        this.disposable = NYTStreams.streamGetSearchArticles(keyWords, sections, beginDate, endDate)
                .subscribeWith(new DisposableObserver<SearchAPIResponse>() {
                    @Override
                    public void onNext(SearchAPIResponse searchAPIResponse) {
                        if(!searchAPIResponse.getResponse().getDocs().isEmpty()) {
                            sendNotification();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("CHECK_API", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**Sends a notification to the phone**/

    @SuppressWarnings("deprecated")
    private void sendNotification(){

        /*Creates the notification builder*/

        NotificationCompat.Builder notificationBuilder;

        /*If the current SDK is Oreo or higher, then creates a chanel*/

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel
                    (CHANEL_NOTIFICATION, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(CHANEL_NAME);
            NotificationManager notificationManager=(NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

            notificationBuilder=new NotificationCompat.Builder(this.context, CHANEL_NOTIFICATION);
        }

        /*Else just creates the notification builder and sets the priority*/

        else{
            notificationBuilder=new NotificationCompat.Builder(this.context);
            notificationBuilder.setPriority(NotificationManager.IMPORTANCE_DEFAULT);
        }

        /*Sets notification contents*/

        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentTitle(this.context.getResources().getString(R.string.notification_title));
        notificationBuilder.setContentText(this.context.getResources().getString(R.string.notification_text));

        /*Send the notification*/

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this.context);
        notificationManagerCompat.notify(NOTIFICATION_ID, notificationBuilder.build());
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
