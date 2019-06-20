package com.sildian.mynews.model.utils;

import com.sildian.mynews.model.most_popular_api.MostPopularAPIResponse;
import com.sildian.mynews.model.articles_search_api.SearchAPIResponse;
import com.sildian.mynews.model.top_stories_api.TopStoriesAPIResponse;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/*************************************************************************************************
 * NYTAPI
 * This interface provides with methods to communicate with the New York Times API
 ************************************************************************************************/

public interface NYTAPI {

    /**Static data**/

    public static final String BASE_URL="https://api.nytimes.com/svc/";
    public static final String TOP_STORIES_API_URL="topstories/v2/{section}.json";
    public static final String MOST_POPULAR_API_URL="mostpopular/v2/shared/{period}.json";
    public static final String ARTICLES_SEARCH_API_URL="search/v2/articlesearch.json";
    public static final String API_KEY="QH5GqvMuy9GV395zpsXITpglhNYpQndn";

    /**Retrofit builder**/

    public static final Retrofit retrofit =new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    /**Top stories API**/

    @GET(TOP_STORIES_API_URL)
    Observable<TopStoriesAPIResponse> getTopStoriesArticles(@Path("section") String section, @Query("api-key") String apiKey);

    /**Most popular API**/

    @GET(MOST_POPULAR_API_URL)
    Observable<MostPopularAPIResponse> getMostPopularArticles(@Path("period") String period, @Query("api-key") String apiKey);

    /**Articles Search API
     * Different queries exists, giving the choice to fill the begin date and end date or not**/

    @GET(ARTICLES_SEARCH_API_URL)
    Observable<SearchAPIResponse> getSearchArticlesWithNoDate
            (@Query("api-key") String apiKey, @Query("sort") String sort, @Query("q") String keyWords, @Query("fq") String sectionsFilter);

    @GET(ARTICLES_SEARCH_API_URL)
    Observable<SearchAPIResponse> getSearchArticlesWithBeginDate
            (@Query("api-key") String apiKey, @Query("sort") String sort, @Query("q") String keyWords, @Query("fq") String sectionsFilter,
             @Query("begin_date") String beginDate);

    @GET(ARTICLES_SEARCH_API_URL)
    Observable<SearchAPIResponse> getSearchArticlesWithEndDate
            (@Query("api-key") String apiKey, @Query("sort") String sort, @Query("q") String keyWords, @Query("fq") String sectionsFilter,
             @Query("end_date") String endDate);

    @GET(ARTICLES_SEARCH_API_URL)
    Observable<SearchAPIResponse> getSearchArticlesWithBeginDateAndEndDate
            (@Query("api-key") String apiKey, @Query("sort") String sort, @Query("q") String keyWords, @Query("fq") String sectionsFilter,
             @Query("begin_date") String beginDate, @Query("end_date") String endDate);
}
