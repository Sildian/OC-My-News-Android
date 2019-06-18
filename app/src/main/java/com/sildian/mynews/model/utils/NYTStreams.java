package com.sildian.mynews.model.utils;

import com.sildian.mynews.model.MostPopularAPIResponse;
import com.sildian.mynews.model.TopStoriesAPIResponse;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/*************************************************************************************************
 * NYTStreams
 * Provides with the streams to communicate with the New York Times AI
 ************************************************************************************************/

public class NYTStreams {

    /**Gets the articles from NYT Top stories API
     * @param section : the section name
     * @return the response containing the list of articles
     */

    public static Observable<TopStoriesAPIResponse> streamGetTopStoriesArticles(String section){
        NYTAPI nytApi=NYTAPI.retrofit.create(NYTAPI.class);
        return nytApi.getTopStoriesArticles(section, NYTAPI.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    /**Gets the articles from NYT Most popular API
     * @return the response containing the list of articles
     */

    public static Observable<MostPopularAPIResponse> streamGetMostPopularArticles(){
        NYTAPI nytApi=NYTAPI.retrofit.create(NYTAPI.class);
        return nytApi.getMostPopularArticles("1", NYTAPI.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
