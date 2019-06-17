package com.sildian.mynews.model.utils;

import com.sildian.mynews.model.MostPopularAPIResponse;
import com.sildian.mynews.model.TopStoriesAPIResponse;

import org.junit.Test;

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
}