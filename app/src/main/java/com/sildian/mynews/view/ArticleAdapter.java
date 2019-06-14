package com.sildian.mynews.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.sildian.mynews.R;
import com.sildian.mynews.model.TopStoriesArticle;

import java.util.List;

/************************************************************************************************
 * ArticleAdapter
 * Monitors the articles data within a recycler view
 ***********************************************************************************************/

public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

    /**Attributes**/

    private List<TopStoriesArticle> topStoriesArticles;             //The list of articles
    private RequestManager glide;                                   //The request manager to display images

    /**Constructor**/

    public ArticleAdapter(List<TopStoriesArticle> topStoriesArticles, RequestManager glide){
        this.topStoriesArticles=topStoriesArticles;
        this.glide=glide;
    }

    /**Callback methods**/

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.list_articles_item, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position, @NonNull List<Object> payloads) {
        holder.update(this.topStoriesArticles.get(position), this.glide);
    }

    @Override
    public int getItemCount() {
        return this.topStoriesArticles.size();
    }
}
