package com.sildian.mynews.model.utils;

import com.sildian.mynews.model.TopStoriesArticle;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface NYTAPI {

    @GET("/{section}.json")
    Observable<List<TopStoriesArticle>> getTopStoriesArticles(@Path("section") String section);
}
