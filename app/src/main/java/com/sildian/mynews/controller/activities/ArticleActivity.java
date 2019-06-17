package com.sildian.mynews.controller.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sildian.mynews.R;
import com.sildian.mynews.controller.fragments.ArticleFragment;

/*************************************************************************************************
 * ArticleActivity
 * This activity displays a fragment which shows an article
 ************************************************************************************************/

public class ArticleActivity extends AppCompatActivity {

    /**The fragment**/

    public ArticleFragment articleFragment;

    /**Callback methods**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        setSupportActionBar(findViewById(R.id.activity_article_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        displayArticleFragment();
    }

    /**Displays the fragment**/

    private void displayArticleFragment(){
        this.articleFragment=(ArticleFragment)getSupportFragmentManager().findFragmentById(R.id.activity_article_fragment);
        if(this.articleFragment==null){
            this.articleFragment=new ArticleFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_article_fragment, this.articleFragment)
                    .commit();
        }
    }
}
