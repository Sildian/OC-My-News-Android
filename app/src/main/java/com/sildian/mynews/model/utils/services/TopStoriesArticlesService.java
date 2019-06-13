package com.sildian.mynews.model.utils.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.sildian.mynews.controller.activities.MainActivity;
import com.sildian.mynews.model.TopStoriesAPIResponse;
import com.sildian.mynews.model.utils.NYTStreams;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/*************************************************************************************************
 * TopStoriesArticlesService
 * This service runs a query to get the articles from NYT Top stories API
 ************************************************************************************************/

public class TopStoriesArticlesService extends Service {

    /**Attributes**/

    private Disposable disposable;                      //The disposable which gets the response

    /**Constructor**/

    public TopStoriesArticlesService() {
    }

    /**Callbacks methods**/

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String section=intent.getStringExtra(MainActivity.KEY_SECTION);
        runTopStoriesArticlesRequest(section);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        Log.i("CHECK_API", "Destroyed");
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
        super.onDestroy();
    }

    /**Runs the query to get the articles
     * @param section : the section name
     */

    public void runTopStoriesArticlesRequest(String section){
        this.disposable= NYTStreams.streamGetTopStoriesArticles(section).subscribeWith(new DisposableObserver<TopStoriesAPIResponse>(){
            @Override
            public void onNext(TopStoriesAPIResponse topStoriesAPIResponse) {
                Log.i("CHECK_API", topStoriesAPIResponse.getResults().get(0).getSection());
                Log.i("CHECK_API", topStoriesAPIResponse.getResults().get(0).getTitle());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.i("CHECK_API", "Completed");
                stopSelf();
            }
        });
    }
}
