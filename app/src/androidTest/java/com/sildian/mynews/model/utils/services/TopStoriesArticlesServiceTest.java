package com.sildian.mynews.model.utils.services;

import androidx.test.runner.AndroidJUnit4;

import com.sildian.mynews.model.TopStoriesAPIResponse;
import com.sildian.mynews.model.utils.NYTStreams;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class TopStoriesArticlesServiceTest {

    @Test
    public void given_lookForSectionArts_when_fetchTopStoriesArticles_then_checkResponse(){

        Observable<TopStoriesAPIResponse> observableTopStoriesAPIResponse= NYTStreams.streamFetchTopStoriesArticles("arts");
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
