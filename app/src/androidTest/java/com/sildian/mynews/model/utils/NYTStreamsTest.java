package com.sildian.mynews.model.utils;

import android.util.Log;

import com.sildian.mynews.model.most_popular_api.MostPopularAPIResponse;
import com.sildian.mynews.model.articles_search_api.SearchAPIResponse;
import com.sildian.mynews.model.top_stories_api.TopStoriesAPIResponse;

import org.junit.Test;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.*;

public class NYTStreamsTest {

    /**Top stories API**/

    @Test
    public void given_lookForSectionArts_when_runTopStoriesArticlesRequest_then_checkResponse(){

        Observable<TopStoriesAPIResponse> observableTopStoriesAPIResponse= NYTStreams.streamGetTopStoriesArticles("arts");
        TestObserver<TopStoriesAPIResponse> testObserver=new TestObserver<>();
        observableTopStoriesAPIResponse.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        if(testObserver.errorCount()>0){
            for(Throwable error:testObserver.errors()) {
                Log.d("CHECK_API", error.getMessage());
            }
        }

        TopStoriesAPIResponse topStoriesAPIResponse=testObserver.values().get(0);
        assertEquals("arts", topStoriesAPIResponse.getSection());
        assertTrue(topStoriesAPIResponse.getResults().size()>0);
    }

    /**Most popular API**/

    @Test
    public void given_lookForMostPopularArticles_when_runMostPopularArticlesRequest_then_checkResponse(){

        Observable<MostPopularAPIResponse> observableMostPopularAPIResponse=NYTStreams.streamGetMostPopularArticles();
        TestObserver<MostPopularAPIResponse> testObserver=new TestObserver<>();
        observableMostPopularAPIResponse.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        if(testObserver.errorCount()>0){
            for(Throwable error:testObserver.errors()) {
                Log.d("CHECK_API", error.getMessage());
            }
        }

        MostPopularAPIResponse mostPopularAPIResponse=testObserver.values().get(0);
        assertTrue(mostPopularAPIResponse.getResults().size()>0);
    }

    /**Articles Search API**/

    @Test
    public void given_lookForFishAndChickenInFoodAndArts_when_runSearchArticlesRequest_then_checkResponse(){

        String keyWords="fish, chicken";
        ArrayList<String> sections=new ArrayList<String>();
        sections.add("food");
        sections.add("arts");

        Observable<SearchAPIResponse> observableSearchAPIResponse
                =NYTStreams.streamGetSearchArticles(keyWords, sections, null, null);
        TestObserver<SearchAPIResponse> testObserver=new TestObserver<>();
        observableSearchAPIResponse.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        if(testObserver.errorCount()>0){
            for(Throwable error:testObserver.errors()) {
                Log.d("CHECK_API", error.getMessage());
            }
        }

        SearchAPIResponse searchAPIResponse=testObserver.values().get(0);
        assertTrue(searchAPIResponse.getResponse().getDocs().size()>0);
    }

    @Test
    public void given_lookForFishAndChickenInFoodAndArtsAfter20190101_when_runSearchArticlesRequest_then_checkResponse(){

        String keyWords="fish, chicken";
        ArrayList<String> sections=new ArrayList<String>();
        sections.add("food");
        sections.add("arts");

        Observable<SearchAPIResponse> observableSearchAPIResponse
                =NYTStreams.streamGetSearchArticles(keyWords, sections, "20190101", null);
        TestObserver<SearchAPIResponse> testObserver=new TestObserver<>();
        observableSearchAPIResponse.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        if(testObserver.errorCount()>0){
            for(Throwable error:testObserver.errors()) {
                Log.d("CHECK_API", error.getMessage());
            }
        }

        SearchAPIResponse searchAPIResponse=testObserver.values().get(0);
        assertTrue(searchAPIResponse.getResponse().getDocs().size()>0);
    }

    @Test
    public void given_lookForFishAndChickenInFoodAndArtsBefore20190530_when_runSearchArticlesRequest_then_checkResponse(){

        String keyWords="fish, chicken";
        ArrayList<String> sections=new ArrayList<String>();
        sections.add("food");
        sections.add("arts");

        Observable<SearchAPIResponse> observableSearchAPIResponse
                =NYTStreams.streamGetSearchArticles(keyWords, sections, null, "20190530");
        TestObserver<SearchAPIResponse> testObserver=new TestObserver<>();
        observableSearchAPIResponse.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        if(testObserver.errorCount()>0){
            for(Throwable error:testObserver.errors()) {
                Log.d("CHECK_API", error.getMessage());
            }
        }

        SearchAPIResponse searchAPIResponse=testObserver.values().get(0);
        assertTrue(searchAPIResponse.getResponse().getDocs().size()>0);
    }

    @Test
    public void given_lookForFishAndChickenInFoodAndArtsAfter20190101Before20190531_when_runSearchArticlesRequest_then_checkResponse(){

        String keyWords="fish, chicken";
        ArrayList<String> sections=new ArrayList<String>();
        sections.add("food");
        sections.add("arts");

        Observable<SearchAPIResponse> observableSearchAPIResponse
                =NYTStreams.streamGetSearchArticles(keyWords, sections, "20190101", "20190531");
        TestObserver<SearchAPIResponse> testObserver=new TestObserver<>();
        observableSearchAPIResponse.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        if(testObserver.errorCount()>0){
            for(Throwable error:testObserver.errors()) {
                Log.d("CHECK_API", error.getMessage());
            }
        }

        SearchAPIResponse searchAPIResponse=testObserver.values().get(0);
        assertTrue(searchAPIResponse.getResponse().getDocs().size()>0);
    }
}