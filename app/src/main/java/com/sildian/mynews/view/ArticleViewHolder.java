package com.sildian.mynews.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sildian.mynews.R;
import com.sildian.mynews.model.TopStoriesArticle;

import butterknife.BindView;
import butterknife.ButterKnife;

/************************************************************************************************
 * ArticleViewHolder
 * Displays the items related to an article within a recycler view
 ***********************************************************************************************/

public class ArticleViewHolder extends RecyclerView.ViewHolder {

    /**Components**/

    @BindView(R.id.list_articles_item_image) ImageView articleImage;
    @BindView(R.id.list_articles_item_text_section) TextView articleSectionText;
    @BindView(R.id.list_articles_item_text_date) TextView articleDateText;
    @BindView(R.id.list_articles_item_text_title) TextView articleTitleText;

    /**Constructor**/

    public ArticleViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    /**Updates with an article
     * @param article : the article
     */

    public void update(TopStoriesArticle article){
        this.articleSectionText.setText(article.getSection());
        this.articleDateText.setText(article.getPublishedDate());
        this.articleTitleText.setText(article.getTitle());
    }
}
