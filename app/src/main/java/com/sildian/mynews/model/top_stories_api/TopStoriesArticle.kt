package com.sildian.mynews.model.top_stories_api

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sildian.mynews.model.Article
import com.sildian.mynews.utils.Utilities

/**************************************************************************************************
 * TopStoriesArticle
 * Monitors the data from NYT Top stories API
 *************************************************************************************************/

class TopStoriesArticle : Article {

    @SerializedName("section") @Expose val section:String?=null
    @SerializedName("subsection") @Expose val subsection:String?=null
    @SerializedName("title") @Expose val title:String?=null
    @SerializedName("abstract") @Expose val _abstract:String?=null
    @SerializedName("url") @Expose val url:String?=null
    @SerializedName("byline") @Expose val byline:String?=null
    @SerializedName("item_type") @Expose val itemType:String?=null
    @SerializedName("updated_date") @Expose val updatedDate:String?=null
    @SerializedName("created_date") @Expose val createdDate:String?=null
    @SerializedName("published_date") @Expose val publishedDate:String?=null
    @SerializedName("material_type_facet") @Expose val materialTypeFacet:String?=null
    @SerializedName("kicker") @Expose val kicker:String?=null
    @SerializedName("multimedia") @Expose val multimedia:List<TopStoriesMultimedium>?=null
    @SerializedName("short_url") @Expose val shortUrl:String?=null

    override fun getArticleSection(): String? {
        return this.section
    }

    override fun getArticleSubSection(): String? {
        return this.subsection
    }

    override fun getArticleTitle(): String? {
        return this.title
    }

    override fun getArticleDate(): String? {
        val inputDateFormat:String="yyyy-MM-dd'T'HH:mm:ss"
        val outputDateFormat:String=Utilities.getLocalDateFormatPattern()
        return Utilities.convertDate(inputDateFormat, outputDateFormat, this.publishedDate)
    }

    override fun getArticleImageUrl(): String? {
        if(!this.multimedia.isNullOrEmpty()){
            return this.multimedia.get(0).url
        }else{
            return null
        }
    }

    override fun getArticleUrl(): String? {
        return this.shortUrl
    }
}