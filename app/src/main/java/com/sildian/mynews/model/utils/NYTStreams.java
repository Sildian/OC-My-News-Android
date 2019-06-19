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

        /*Creates the sections filter String with the list of String*/

        StringBuilder sectionsFilterBuilder=new StringBuilder();
        sectionsFilterBuilder.append("section_name.contains:(");
        for(String section:sections){
            sectionsFilterBuilder.append("\""+section+"\"");
        }
        sectionsFilterBuilder.append(")");
        String sectionsFilter=sectionsFilterBuilder.toString();

        /*Runs the query*/

        NYTAPI nytApi=NYTAPI.retrofit.create(NYTAPI.class);

        /*Case if both begin date and end date are null*/

        if(beginDate==null&&endDate==null) {
            return nytApi.getSearchArticlesWithNoDate(NYTAPI.API_KEY, keyWords, sectionsFilter)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(10, TimeUnit.SECONDS);
        }

        /*Case if only end date is null*/

        else if(endDate==null){
            return nytApi.getSearchArticlesWithBeginDate(NYTAPI.API_KEY, keyWords, sectionsFilter, beginDate)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(10, TimeUnit.SECONDS);
        }

        /*Case if only begin date is null*/

        else if(beginDate==null) {
            return nytApi.getSearchArticlesWithEndDate(NYTAPI.API_KEY, keyWords, sectionsFilter, endDate)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(10, TimeUnit.SECONDS);
        }

        /*Case if both begin date and end date are given*/

        else{
            return nytApi.getSearchArticlesWithBeginAndEndDates(NYTAPI.API_KEY, keyWords, sectionsFilter, beginDate, endDate)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(10, TimeUnit.SECONDS);
        }
    }
}
