package com.sildian.mynews.controller.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sildian.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/*************************************************************************************************
 * ArticleFragment
 * This fragment shows an article
 ************************************************************************************************/

public class ArticleFragment extends Fragment {

    /**Components**/

    @BindView(R.id.fragment_article_web_view) WebView articleWebView;

    /**Attributes**/

    private String articleUrl;                          //The article's URL

    /**Constructor**/

    public ArticleFragment() {

    }

    /**Callback methods**/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_article, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeArticleWebView();
    }

    /**Initializes the web view by loading the URL**/

    private void initializeArticleWebView(){
        Intent intent=getActivity().getIntent();
        this.articleUrl=intent.getStringExtra(MainFragment.KEY_ARTICLE_URL);
        this.articleWebView.setWebViewClient(new WebViewClient());
        this.articleWebView.loadUrl(this.articleUrl);
    }
}
