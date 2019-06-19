package com.sildian.mynews.model.utils;

import com.sildian.mynews.model.MostPopularAPIResponse;
import com.sildian.mynews.model.SearchAPIResponse;
import com.sildian.mynews.model.TopStoriesAPIResponse;

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

    public static final String BASE_URL="https://api.nytimes.com/svc/";
    public static final String API_KEY="QH5GqvMuy9GV395zpsXITpglhNYpQndn";

    public static final Retrofit retrofit =new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    @GET("topstories/v2/{section}.json")
    Observable<TopStoriesAPIResponse> getTopStoriesArticles(@Path("section") String section, @Query("api-key") String apiKey);

    @GET("mostpopular/v2/shared/{period}.json")
    Observable<MostPopularAPIResponse> getMostPopularArticles(@Path("period") String period, @Query("api-key") String apiKey);

    @GET("search/v2/articlesearch.json")
    Observable<SearchAPIResponse> getSearchArticlesWithNoDate
            (@Query("api-key") String apiKey, @Query("q") String keyWords, @Query("fq") String sectionsFilter);

    @GET("search/v2/articlesearch.json")
    Observable<SearchAPIResponse> getSearchArticlesWithBeginDate
            (@Query("api-key") String apiKey, @Query("q") String keyWords, @Query("fq") String sectionsFilter,
             @Query("begin_date") String beginDate);

    @GET("search/v2/articlesearch.json")
    Observable<SearchAPIResponse> getSearchArticlesWithEndDate
            (@Query("api-key") String apiKey, @Query("q") String keyWords, @Query("fq") String sectionsFilter,
             @Query("end_date") String endDate);

    @GET("search/v2/articlesearch.json")
    Observable<SearchAPIResponse> getSearchArticlesWithBeginAndEndDates
            (@Query("api-key") String apiKey, @Query("q") String keyWords, @Query("fq") String sectionsFilter,
             @Query("begin_date") String beginDate, @Query("end_date") String endDate);
}
