package com.sildian.mynews.model.utils;

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
}
