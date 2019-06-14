package com.sildian.mynews.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.sildian.mynews.R;
import com.sildian.mynews.model.TopStoriesArticle;
import com.sildian.mynews.utils.Utilities;

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
     * @param glide : glide manager to display the image
     */

    public void update(TopStoriesArticle article, RequestManager glide){
        if(!article.getMultimedia().isEmpty()) {
            glide.load(article.getMultimedia().get(0).getUrl()).apply(RequestOptions.noTransformation()).into(articleImage);
        }
        this.articleSectionText.setText(article.getSection()+" > "+article.getSubsection());
        this.articleDateText.setText(Utilities.convertDate(article.getPublishedDate()));
        this.articleTitleText.setText(article.getTitle());
    }
}
