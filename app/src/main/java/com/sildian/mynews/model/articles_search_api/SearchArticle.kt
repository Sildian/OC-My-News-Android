package com.sildian.mynews.model.articles_search_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sildian.mynews.model.Article
import com.sildian.mynews.utils.Utilities

/**************************************************************************************************
 * SearchArticle
 * Monitors the data from NYT Search API
 *************************************************************************************************/

class SearchArticle : Article {

    @SerializedName("web_url") @Expose val webUrl: String? = null
    @SerializedName("snippet") @Expose val snippet: String? = null
    @SerializedName("lead_paragraph") @Expose val leadParagraph: String? = null
    @SerializedName("abstract") @Expose val _abstract: String? = null
    @SerializedName("print_page") @Expose val printPage: String? = null
    @SerializedName("blog") @Expose val blog: SearchBlog? = null
    @SerializedName("source") @Expose val source: String? = null
    @SerializedName("multimedia") @Expose val multimedia: List<SearchMultimedium>? = null
    @SerializedName("headline") @Expose val headline: SearchHeadline? = null
    @SerializedName("keywords") @Expose val keywords: List<SearchKeyword>? = null
    @SerializedName("pub_date") @Expose val pubDate: String? = null
    @SerializedName("document_type") @Expose val documentType: String? = null
    @SerializedName("news_desk") @Expose val newsDesk: String? = null
    @SerializedName("section_name") @Expose val sectionName: String? = null
    @SerializedName("byline") @Expose val byline: SearchByline? = null
    @SerializedName("type_of_material") @Expose val typeOfMaterial: String? = null
    @SerializedName("_id") @Expose val id: String? = null
    @SerializedName("word_count") @Expose val wordCount: Int? = null
    @SerializedName("uri") @Expose val uri: String? = null

    override fun getArticleSection(): String? {
        return this.sectionName
    }

    override fun getArticleSubSection(): String? {
        return ""
    }

    override fun getArticleTitle(): String? {
        if (this.headline != null) {
            return this.headline.main
        }else{
            return null
        }
    }

    override fun getArticleDate(): String? {
        val inputDateFormat:String="yyyy-MM-dd'T'HH:mm:ss"
        val outputDateFormat:String="MM/dd/yyyy"
        return Utilities.convertDate(inputDateFormat, outputDateFormat, this.pubDate)
    }

    override fun getArticleImageUrl(): String? {
        if(!this.multimedia.isNullOrEmpty()) {
            return "https://static01.nyt.com/"+this.multimedia.get(0).url
        }else{
            return null
        }
    }

    override fun getArticleUrl(): String? {
        return this.webUrl
    }
}