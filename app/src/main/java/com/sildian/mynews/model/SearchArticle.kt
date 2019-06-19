package com.sildian.mynews.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**************************************************************************************************
 * SearchArticle
 * Monitors the data from NYT Search API
 *************************************************************************************************/

class SearchArticle {

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
}