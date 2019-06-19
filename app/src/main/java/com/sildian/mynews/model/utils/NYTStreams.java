package com.sildian.mynews.model.utils;

import androidx.annotation.Nullable;

import com.sildian.mynews.model.MostPopularAPIResponse;
import com.sildian.mynews.model.SearchAPIResponse;
import com.sildian.mynews.model.TopStoriesAPIResponse;

import java.util.List;
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

    /**Gets the articles from NYT Article Search API
     * @param keyWords : the key words searched in the query. Each word must be separated by ','
     * @param sections : the list of sections to be used as a filter in the request
     * @param beginDate : the begin date format like 'aaaammdd'
     * @param endDate : the end date format like 'aaaammdd'
     * @return the response containing the list of articles
     */

    public static Observable<SearchAPIResponse> streamGetSearchArticles
            (String keyWords, List<String> sections, @Nullable String beginDate, @Nullable String endDate){
        return null;
    }
}
