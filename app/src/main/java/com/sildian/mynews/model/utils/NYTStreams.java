package com.sildian.mynews.model.utils;

import androidx.annotation.Nullable;

import com.sildian.mynews.model.MostPopularAPIResponse;
import com.sildian.mynews.model.SearchAPIResponse;
import com.sildian.mynews.model.TopStoriesAPIResponse;
import com.sildian.mynews.utils.Utilities;

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

    /**Gets the articles from NYT Articles Search API
     * @param keyWords : the key words searched in the query. Each word must be separated by ','
     * @param sections : the list of sections to be used as a filter in the request
     * @param beginDate : the begin date format like 'aaaammdd'
     * @param endDate : the end date format like 'aaaammdd'
     * @return the response containing the list of articles
     */

    public static Observable<SearchAPIResponse> streamGetSearchArticles
            (String keyWords, List<String> sections, @Nullable String beginDate, @Nullable String endDate){

        /*Generates the sections filter String with the list of String*/

        String sectionsFilter= Utilities.generateQueryFilter("section_name.contains", sections);

        /*Runs the query. A different query runs depending on if beginDate and endDate contains values or not*/

        if(beginDate==null&&endDate==null) {
            return streamGetSearchArticlesWithNoDate(keyWords, sectionsFilter);
        }
        else if(endDate==null){
            return streamGetSearchArticlesWithBeginDate(keyWords, sectionsFilter, beginDate);
        }
        else if(beginDate==null) {
            return streamGetSearchArticlesWithEndDate(keyWords, sectionsFilter, endDate);
        }
        else{
            return streamGetSearchArticlesWithBeginDateAndEndDate(keyWords, sectionsFilter, beginDate, endDate);
        }
    }

    /**NYT Articles Search API : case no date**/

    private static Observable<SearchAPIResponse> streamGetSearchArticlesWithNoDate(String keyWords, String sectionsFilter){

        NYTAPI nytApi=NYTAPI.retrofit.create(NYTAPI.class);
        return nytApi.getSearchArticlesWithNoDate(NYTAPI.API_KEY, keyWords, sectionsFilter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    /**NYT Articles Search API : case begin date only**/

    private static Observable<SearchAPIResponse> streamGetSearchArticlesWithBeginDate
            (String keyWords, String sectionsFilter, String beginDate){

        NYTAPI nytApi=NYTAPI.retrofit.create(NYTAPI.class);
        return nytApi.getSearchArticlesWithBeginDate(NYTAPI.API_KEY, keyWords, sectionsFilter, beginDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    /**NYT Articles Search API : case end date only**/

    private static Observable<SearchAPIResponse> streamGetSearchArticlesWithEndDate
            (String keyWords, String sectionsFilter, String endDate){

        NYTAPI nytApi=NYTAPI.retrofit.create(NYTAPI.class);
        return nytApi.getSearchArticlesWithEndDate(NYTAPI.API_KEY, keyWords, sectionsFilter, endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    /**NYT Articles Search API : case begin date and end date**/

    private static Observable<SearchAPIResponse> streamGetSearchArticlesWithBeginDateAndEndDate
            (String keyWords, String sectionsFilter, String beginDate, String endDate){

        NYTAPI nytApi=NYTAPI.retrofit.create(NYTAPI.class);
        return nytApi.getSearchArticlesWithBeginDateAndEndDate(NYTAPI.API_KEY, keyWords, sectionsFilter, beginDate, endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
