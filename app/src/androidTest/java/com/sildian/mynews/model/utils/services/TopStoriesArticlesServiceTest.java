package com.sildian.mynews.model.utils.services;

import com.sildian.mynews.model.TopStoriesArticle;
import com.sildian.mynews.model.utils.NYTStreams;

import org.junit.Test;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static java.sql.DriverManager.println;
import static org.junit.Assert.*;

public class TopStoriesArticlesServiceTest {

    @Test
    public void given_lookForSectionArts_when_fetchTopStoriesArticles_then_checkArtsArticles(){

        Observable<List<TopStoriesArticle>> observableTopStoriesArticles= NYTStreams.streamFetchTopStoriesArticles("arts");
        TestObserver<List<TopStoriesArticle>> testObserver=new TestObserver<>();
        observableTopStoriesArticles.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .assertComplete();

        List<TopStoriesArticle> topStoriesArticles=testObserver.values().get(0);
        assertTrue(topStoriesArticles.size()>0);
        assertTrue(topStoriesArticles.get(0).getSection()=="arts");
        println("Articles : "+topStoriesArticles.size());
        println("Section : "+topStoriesArticles.get(0).getSection());
    }
}
