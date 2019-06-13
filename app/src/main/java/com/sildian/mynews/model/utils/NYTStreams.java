package com.sildian.mynews.model.utils;

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

    public static Observable<TopStoriesAPIResponse> streamFetchTopStoriesArticles(String section){
        NYTAPI nytApi=NYTAPI.retrofit.create(NYTAPI.class);
        return nytApi.getTopStoriesArticles(section, NYTAPI.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
