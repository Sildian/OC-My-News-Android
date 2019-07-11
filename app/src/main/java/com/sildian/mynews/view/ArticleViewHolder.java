package com.sildian.mynews.view;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.sildian.mynews.R;
import com.sildian.mynews.model.Article;

import java.util.List;

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

    /**Other attributes**/

    private List<String> checkedArticlesUrls;           //The list of all URL related to already checked articles

    /**Constructor**/

    public ArticleViewHolder(View itemView, List<String> checkedArticlesUrls){
        super(itemView);
        this.checkedArticlesUrls=checkedArticlesUrls;
        ButterKnife.bind(this, itemView);
    }

    /**Updates with an article
     * @param article : the article
     * @param glide : glide manager to display the image
     */

    public void update(Article article, RequestManager glide){

        /*Updates the articles items*/

        if(article.getArticleImageUrl()!=null) {
            glide.load(article.getArticleImageUrl()).apply(RequestOptions.noTransformation()).into(this.articleImage);
        }
        if(article.getArticleSubSection().equals("")){
            this.articleSectionText.setText(article.getArticleSection());
        }else {
            this.articleSectionText.setText(article.getArticleSection() + " > " + article.getArticleSubSection());
        }
        this.articleDateText.setText(article.getArticleDate());
        this.articleTitleText.setText(article.getArticleTitle());

        /*If the article is already checked, then changes the text's color*/

        if(this.checkedArticlesUrls.contains(article.getArticleUrl())){
            this.articleSectionText.setTextColor(Color.DKGRAY);
            this.articleDateText.setTextColor(Color.DKGRAY);
            this.articleTitleText.setTextColor(Color.GRAY);
        }
    }
}
