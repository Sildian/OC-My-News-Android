package com.sildian.mynews.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.sildian.mynews.controller.activities.MainActivity;
import com.sildian.mynews.model.UserSettings;
import com.sildian.mynews.model.articles_search_api.SearchAPIResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/*************************************************************************************************
 * NotificationReceiver
 * When called, this receiver run an Article search request using
 * the user settings notification keywords and sections, and using the date of today.
 * Then if at least one article is found, send a notification to the phone.
 ************************************************************************************************/

public class NotificationReceiver extends BroadcastReceiver {

    /**Attributes**/

    private UserSettings userSettings;              //The user settings
    private Disposable disposable;                  //The disposable which gets the response

    /**Callback methods**/

    @Override
    public void onReceive(Context context, Intent intent) {

        /*Gets the data from the intent*/

        this.userSettings=intent.getParcelableExtra(MainActivity.KEY_NOTIFICATION_USER);

        /*Sets the parameters to be sent to the query*/

        String keyWords=this.userSettings.getNotificationKeyWords();
        ArrayList<String> sections=this.userSettings.getNotificationSections();
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        String beginDate=Utilities.generateDate("yyyyMMdd", year, month, day);
        String endDate=Utilities.generateDate("yyyyMMdd", year, month, day);

        /*Runs the query*/

        runSearchArticlesRequest(keyWords, sections, beginDate, endDate);
    }

    /**Runs Article Search API request**/

    private void runSearchArticlesRequest(String keyWords, List<String> sections, String beginDate, String endDate) {
        this.disposable = NYTStreams.streamGetSearchArticles(keyWords, sections, beginDate, endDate)
                .subscribeWith(new DisposableObserver<SearchAPIResponse>() {
                    @Override
                    public void onNext(SearchAPIResponse searchAPIResponse) {
                        Log.i("CHECK_NOTIFICATION", "Articles found");
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
}
