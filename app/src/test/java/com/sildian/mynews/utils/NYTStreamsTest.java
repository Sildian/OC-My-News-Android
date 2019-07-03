package com.sildian.mynews.utils;


import com.sildian.mynews.model.articles_search_api.SearchAPIResponse;
import com.sildian.mynews.model.articles_search_api.SearchArticle;
import com.sildian.mynews.model.articles_search_api.SearchDetailResponse;
import com.sildian.mynews.model.most_popular_api.MostPopularAPIResponse;
import com.sildian.mynews.model.most_popular_api.MostPopularArticle;
import com.sildian.mynews.model.top_stories_api.TopStoriesAPIResponse;
import com.sildian.mynews.model.top_stories_api.TopStoriesArticle;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class NYTStreamsTest {

    /**Top stories API**/

    @Test
    public void given_lookForSectionArts_when_runTopStoriesArticlesRequest_then_checkResponse(){

        List<TopStoriesArticle> articles=new ArrayList<>();
        TopStoriesArticle article=new TopStoriesArticle();
        articles.add(article);

        TopStoriesAPIResponse topStoriesAPIResponse= Mockito.mock(TopStoriesAPIResponse.class);
        Mockito.when(topStoriesAPIResponse.getSection()).thenReturn("arts");
        Mockito.when(topStoriesAPIResponse.getResults()).thenReturn(articles);

        assertEquals("arts", topStoriesAPIResponse.getSection());
        assertTrue(topStoriesAPIResponse.getResults().size()>0);
    }

    /**Most popular API**/

    @Test
    public void given_lookForMostPopularArticles_when_runMostPopularArticlesRequest_then_checkResponse(){

        List<MostPopularArticle> articles=new ArrayList<>();
        MostPopularArticle article=new MostPopularArticle();
        articles.add(article);

        MostPopularAPIResponse mostPopularAPIResponse= Mockito.mock(MostPopularAPIResponse.class);
        Mockito.when(mostPopularAPIResponse.getResults()).thenReturn(articles);

        assertTrue(mostPopularAPIResponse.getResults().size()>0);
    }

    /**Articles Search API**/

    @Test
    public void given_lookForFishAndChickenInFoodAndArts_when_runSearchArticlesRequest_then_checkResponse() {

        List<SearchArticle> articles=new ArrayList<>();
        SearchArticle article=new SearchArticle();
        articles.add(article);

        SearchDetailResponse detailResponse=Mockito.mock(SearchDetailResponse.class);
        Mockito.when(detailResponse.getDocs()).thenReturn(articles);

        SearchAPIResponse searchAPIResponse=Mockito.mock(SearchAPIResponse.class);
        Mockito.when(searchAPIResponse.getResponse()).thenReturn(detailResponse);

        assertTrue(searchAPIResponse.getResponse().getDocs().size()>0);
    }
}