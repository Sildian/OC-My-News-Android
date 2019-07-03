package com.sildian.mynews.controller.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sildian.mynews.R;
import com.sildian.mynews.controller.activities.ArticleActivity;
import com.sildian.mynews.controller.activities.MainActivity;
import com.sildian.mynews.controller.activities.SettingsActivity;
import com.sildian.mynews.model.Article;
import com.sildian.mynews.model.UserSettings;
import com.sildian.mynews.model.articles_search_api.SearchAPIResponse;
import com.sildian.mynews.model.most_popular_api.MostPopularAPIResponse;
import com.sildian.mynews.model.top_stories_api.TopStoriesAPIResponse;
import com.sildian.mynews.utils.NYTStreams;
import com.sildian.mynews.utils.Utilities;
import com.sildian.mynews.view.ArticleAdapter;
import com.sildian.mynews.view.ItemClickSupport;
import com.sildian.mynews.view.MainFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.Icepick;
import icepick.State;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/************************************************************************************************
 * MainFragment
 * This fragment shows a list of articles downloaded from the New York Times API
 * Different lists of articles can be shown depending on the query
 ***********************************************************************************************/

public class MainFragment extends Fragment {

    /**Keys to transfer data within intents**/

    public static final String KEY_ARTICLE_URL="KEY_ARTICLE_URL";

    /**The id will define the behaviour of the fragment**/

    public static final int ID_TOP_STORIES=0;                   //The fragment will run the top stories API
    public static final int ID_MOST_POPULARS=1;                 //The fragment will run the most populars API
    public static final int ID_SEARCH=2;                        //THe fragment will run the articles search API

    /**Components**/

    @BindView(R.id.fragment_main_progress_bar) ProgressBar progressBar;
    @BindView(R.id.fragment_main_text_message) TextView messageText;
    @BindView(R.id.fragment_main_swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fragment_main_recycler_view) RecyclerView articlesRecyclerView;

    /**Attributes**/

    private MainActivity mainActivity;                          //The activity running the fragment
    @State int id;                                              //The id will define the behaviour of the fragment
    @State UserSettings userSettings;                           //The user settings
    private List<Article> articles;                             //The list of articles
    private ArticleAdapter articleAdapter;                      //The adapter to manage the recycler view
    private Disposable disposable;                              //The disposable which gets the response

    /**Constructor
     * @param id : the id will define the behaviour of the fragment
     */

    public MainFragment(int id, UserSettings userSettings) {
        this.id=id;
        this.userSettings=userSettings;
    }

    public MainFragment(){

    }

    /**Updates the user settings**/

    public void updateUserSettings(UserSettings userSettings){
        this.userSettings=userSettings;
    }

    /**Callback methods**/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity=(MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        Icepick.restoreInstanceState(this, savedInstanceState);
        initializeSwipeRefreshLayout();
        initializeArticlesRecyclerView();
        refreshQuery();
        return view;
    }

    @Override
    public void onDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()){
            this.disposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    /**Initializes the swipe refresh layout**/

    private void initializeSwipeRefreshLayout(){
        this.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark));
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshQuery();
            }
        });
    }

    /**Initializes the recycler view**/

    private void initializeArticlesRecyclerView(){

        /*Initializes the different items to create the recycler view*/

        this.articles=new ArrayList<Article>();
        this.articleAdapter=new ArticleAdapter(this.articles, Glide.with(this), mainActivity.getCheckedArticlesUrls());
        this.articlesRecyclerView.setAdapter(this.articleAdapter);
        this.articlesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*Initializes the listener : when the user clicks on an item, then opens ArticleActivity*/

        ItemClickSupport.addTo(this.articlesRecyclerView, R.layout.list_articles_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        /*If the article is not checked yet, then adds its url to the list of checked articles urls*/

                        if(!mainActivity.getCheckedArticlesUrls().contains(articles.get(position).getArticleUrl())) {
                            mainActivity.addCheckedArticleUrl(articles.get(position).getArticleUrl());
                            articleAdapter.notifyDataSetChanged();
                        }

                        /*Opens ArticleActivity*/

                        Intent articleActivityIntent=new Intent(getActivity(), ArticleActivity.class);
                        articleActivityIntent.putExtra(KEY_ARTICLE_URL, articles.get(position).getArticleUrl());
                        startActivity(articleActivityIntent);

                    }
                });
    }

    /**Refreshes the query depending on the current id**/

    public void refreshQuery(){
        switch(this.id) {
            case ID_TOP_STORIES:
                String section= Utilities.convertQueryWord(getString(R.string.section_name_default));
                runTopStoriesArticlesRequest(section);
                break;
            case ID_MOST_POPULARS:
                runMostPopularArticlesRequest();
                break;
            case ID_SEARCH:
                String keyWords=this.userSettings.getSearchKeyWords();
                ArrayList<String> sections=this.userSettings.getSearchSections();
                String beginDate=Utilities.convertDate("MM/dd/yyyy", "yyyyMMdd", this.userSettings.getSearchBeginDate());
                String endDate=Utilities.convertDate("MM/dd/yyyy", "yyyyMMdd", this.userSettings.getSearchEndDate());
                runSearchArticlesRequest(keyWords, sections, beginDate, endDate);
                break;
            default:
                String sectionName= Utilities.convertQueryWord(this.userSettings.getSheetsSections().get(this.id- MainFragmentAdapter.NB_SHEETS_BASE));
                runTopStoriesArticlesRequest(sectionName);
                break;
        }
    }

    /**Refreshes the screen before a request**/

    private void refreshScreenBeforeRequest(){
        this.articles.clear();
        this.articleAdapter.notifyDataSetChanged();
        if(!this.swipeRefreshLayout.isRefreshing()) {
            this.progressBar.setVisibility(View.VISIBLE);
        }
        this.messageText.setVisibility(View.GONE);
    }

    /**Refreshes the screen after a request returning success**/

    private void refreshScreenAfterRequestSuccess(){
        this.progressBar.setVisibility(View.GONE);
        this.swipeRefreshLayout.setRefreshing(false);
        this.articleAdapter.notifyDataSetChanged();
        if(this.articles.isEmpty()){
            this.messageText.setText(R.string.message_request_not_found);
            this.messageText.setVisibility(View.VISIBLE);
        }
    }

    /**Refreshes the screen after a request returning error**/

    private void refreshScreenAfterRequestError(){
        this.progressBar.setVisibility(View.GONE);
        this.articleAdapter.notifyDataSetChanged();
        this.messageText.setText(R.string.message_request_error);
        this.messageText.setVisibility(View.VISIBLE);
        this.swipeRefreshLayout.setRefreshing(false);
    }

    /**Refreshes the screen when trying to run a search request without parameters**/

    private void refreshScreenWhenSearchRequestHasNoParameter(){
        this.messageText.setText(R.string.message_request_no_parameter);
        this.messageText.setVisibility(View.VISIBLE);
        this.swipeRefreshLayout.setRefreshing(false);
    }

    /**Runs the query to get the articles from NYT top stories API
     * @param section : the section name
     */

    private void runTopStoriesArticlesRequest(String section){
        refreshScreenBeforeRequest();
        this.disposable= NYTStreams.streamGetTopStoriesArticles(section).subscribeWith(new DisposableObserver<TopStoriesAPIResponse>(){
            @Override
            public void onNext(TopStoriesAPIResponse topStoriesAPIResponse) {
                articles.addAll(topStoriesAPIResponse.getResults());
                refreshScreenAfterRequestSuccess();
            }

            @Override
            public void onError(Throwable e) {
                Log.d("CHECK_API", e.getMessage());
                refreshScreenAfterRequestError();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**Runs the query to get the articles from NYT Most popular API*/

    private void runMostPopularArticlesRequest(){
        refreshScreenBeforeRequest();
        this.disposable= NYTStreams.streamGetMostPopularArticles().subscribeWith(new DisposableObserver<MostPopularAPIResponse>(){
            @Override
            public void onNext(MostPopularAPIResponse mostPopularAPIResponse) {
                articles.addAll(mostPopularAPIResponse.getResults());
                refreshScreenAfterRequestSuccess();
            }

            @Override
            public void onError(Throwable e) {
                Log.d("CHECK_API", e.getMessage());
                refreshScreenAfterRequestError();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**Runs the query to get the articles from NYT Articles Search API
     * @param keyWords : the key words searched in the query. Each word must be separated by ','
     * @param sections : the list of sections to be used as a filter in the request
     * @param beginDate : the begin date format like 'aaaammdd'
     * @param endDate : the end date format like 'aaaammdd'
     */

    private void runSearchArticlesRequest(String keyWords, List<String> sections, String beginDate, String endDate){
        if(keyWords.isEmpty()||sections.isEmpty()){
            refreshScreenWhenSearchRequestHasNoParameter();
        }
        else {
            refreshScreenBeforeRequest();
            this.disposable = NYTStreams.streamGetSearchArticles(keyWords, sections, beginDate, endDate)
                    .subscribeWith(new DisposableObserver<SearchAPIResponse>() {
                        @Override
                        public void onNext(SearchAPIResponse searchAPIResponse) {
                            articles.addAll(searchAPIResponse.getResponse().getDocs());
                            refreshScreenAfterRequestSuccess();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("CHECK_API", e.getMessage());
                            refreshScreenAfterRequestError();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    /**Getters**/
    
    public int getMainFragmentId() {
        return id;
    }
}
