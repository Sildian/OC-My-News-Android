package com.sildian.mynews.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.sildian.mynews.R;
import com.sildian.mynews.model.Article;
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

    public void update(Article article, RequestManager glide){
        if(article.getArticleImageUrl()!=null) {
            glide.load(article.getArticleImageUrl()).apply(RequestOptions.noTransformation()).into(this.articleImage);
        }
        this.articleSectionText.setText(article.getArticleSection()+" > "+article.getArticleSubSection());
        this.articleDateText.setText(Utilities.convertDate(article.getArticleDate()));
        this.articleTitleText.setText(article.getArticleTitle());
    }
}
