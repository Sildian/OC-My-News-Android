package com.sildian.mynews.model.utils;

import com.sildian.mynews.model.MostPopularAPIResponse;
import com.sildian.mynews.model.SearchAPIResponse;
import com.sildian.mynews.model.TopStoriesAPIResponse;

import org.junit.Test;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.*;

public class NYTQueriesRunnerTest {

    @Test
    public void given_lookForSectionArts_when_runTopStoriesArticlesRequest_then_checkResponse(){

        Observable<TopStoriesAPIResponse> observableTopStoriesAPIResponse= NYTStreams.streamGetTopStoriesArticles("arts");
        TestObserver<TopStoriesAPIResponse> testObserver=new TestObserver<>();
        observableTopStoriesAPIResponse.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        TopStoriesAPIResponse topStoriesAPIResponse=testObserver.values().get(0);
        assertEquals("arts", topStoriesAPIResponse.getSection());
        assertTrue(topStoriesAPIResponse.getResults().size()>0);
    }

    @Test
    public void given_lookForMostPopularArticles_when_runMostPopularArticlesRequest_then_checkResponse(){

        Observable<MostPopularAPIResponse> observableMostPopularAPIResponse=NYTStreams.streamGetMostPopularArticles();
        TestObserver<MostPopularAPIResponse> testObserver=new TestObserver<>();
        observableMostPopularAPIResponse.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        MostPopularAPIResponse mostPopularAPIResponse=testObserver.values().get(0);
        assertTrue(mostPopularAPIResponse.getResults().size()>0);
    }

    @Test
    public void given_lookForFishAndChickenInFoodAndArts_when_runSearchArticlesRequest_then_checkResponse(){

        String keyWords="fish, chicken";
        ArrayList<String> sections=new ArrayList<String>();
        sections.add("food");
        sections.add("arts");

        Observable<SearchAPIResponse> observableSearchAPIResponse=NYTStreams.streamGetSearchArticles(keyWords, sections, null, null);
        TestObserver<SearchAPIResponse> testObserver=new TestObserver<>();
        observableSearchAPIResponse.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        SearchAPIResponse searchAPIResponse=testObserver.values().get(0);
        assertTrue(searchAPIResponse.getResponse().getDocs().size()>0);
    }

    @Test
    public void given_lookForFishAndChickenInFoodAndArtsAfter20190101_when_runSearchArticlesRequest_then_checkResponse(){

        String keyWords="fish, chicken";
        ArrayList<String> sections=new ArrayList<String>();
        sections.add("food");
        sections.add("arts");

        Observable<SearchAPIResponse> observableSearchAPIResponse=NYTStreams.streamGetSearchArticles(keyWords, sections, "20190101", null);
        TestObserver<SearchAPIResponse> testObserver=new TestObserver<>();
        observableSearchAPIResponse.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        SearchAPIResponse searchAPIResponse=testObserver.values().get(0);
        assertTrue(searchAPIResponse.getResponse().getDocs().size()>0);
    }

    @Test
    public void given_lookForFishAndChickenInFoodAndArtsBefore20190530_when_runSearchArticlesRequest_then_checkResponse(){

        String keyWords="fish, chicken";
        ArrayList<String> sections=new ArrayList<String>();
        sections.add("food");
        sections.add("arts");

        Observable<SearchAPIResponse> observableSearchAPIResponse=NYTStreams.streamGetSearchArticles(keyWords, sections, null, "20190530");
        TestObserver<SearchAPIResponse> testObserver=new TestObserver<>();
        observableSearchAPIResponse.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        SearchAPIResponse searchAPIResponse=testObserver.values().get(0);
        assertTrue(searchAPIResponse.getResponse().getDocs().size()>0);
    }

    @Test
    public void given_lookForFishAndChickenInFoodAndArtsAfter20190101Before20190530_when_runSearchArticlesRequest_then_checkResponse(){

        String keyWords="fish, chicken";
        ArrayList<String> sections=new ArrayList<String>();
        sections.add("food");
        sections.add("arts");

        Observable<SearchAPIResponse> observableSearchAPIResponse=NYTStreams.streamGetSearchArticles(keyWords, sections, "20190101", "20190530");
        TestObserver<SearchAPIResponse> testObserver=new TestObserver<>();
        observableSearchAPIResponse.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        SearchAPIResponse searchAPIResponse=testObserver.values().get(0);
        assertTrue(searchAPIResponse.getResponse().getDocs().size()>0);
    }
}