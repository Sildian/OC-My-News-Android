package com.sildian.mynews.model.utils;

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
}