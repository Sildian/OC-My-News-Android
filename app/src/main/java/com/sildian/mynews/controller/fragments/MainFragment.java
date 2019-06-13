package com.sildian.mynews.controller.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sildian.mynews.R;
import com.sildian.mynews.model.TopStoriesAPIResponse;
import com.sildian.mynews.model.TopStoriesArticle;
import com.sildian.mynews.model.utils.NYTQueriesRunner;
import com.sildian.mynews.view.ArticleAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/************************************************************************************************
 * MainFragment
 * This fragment shows a list of articles downloaded from the New York Times API
 * Different lists of articles can be shown depending on the query
 ***********************************************************************************************/

public class MainFragment extends Fragment implements NYTQueriesRunner.NYTQueryResponseListener {

    /**Components**/

    @BindView(R.id.fragment_main_recycler_view) RecyclerView articlesRecyclerView;

    /**Attributes**/

    private List<TopStoriesArticle> topStoriesArticles;         //The list of articles
    private ArticleAdapter articleAdapter;                      //The adapter to manage the recycler view
    private NYTQueriesRunner queriesRunner;                     //The queries runner running the NYT API

    public MainFragment() {

    }

    /**Callback methods**/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        initializeArticlesRecyclerView();
        initializeQueriesRunner();
        startTopStoriesQuery();

        return view;
    }

    /**Initializes the recycler view**/

    private void initializeArticlesRecyclerView(){
        this.topStoriesArticles=new ArrayList<TopStoriesArticle>();
        this.articleAdapter=new ArticleAdapter(this.topStoriesArticles);
        this.articlesRecyclerView.setAdapter(this.articleAdapter);
        this.articlesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**Initializes the queries runner**/

    private void initializeQueriesRunner(){
        this.queriesRunner=new NYTQueriesRunner(this);
    }

    /**Starts a query to get the top stories articles**/

    private void startTopStoriesQuery(){
        this.queriesRunner.runTopStoriesArticlesRequest("arts");
    }

    /**Callbacks from NYTQueriesRunner**/

    @Override
    public void onResponse(TopStoriesAPIResponse topStoriesAPIResponse) {
        this.topStoriesArticles.clear();
        this.topStoriesArticles.addAll(topStoriesAPIResponse.getResults());
        this.articleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {

    }
}
