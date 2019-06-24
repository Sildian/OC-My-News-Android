package com.sildian.mynews.model.most_popular_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sildian.mynews.model.Article
import com.sildian.mynews.utils.Utilities

/**************************************************************************************************
 * MostPopularArticle
 * Monitors the data from NYT Most popular API
 *************************************************************************************************/

class MostPopularArticle : Article {

    @SerializedName("url") @Expose val url: String? = null
    @SerializedName("adx_keywords") @Expose val adxKeywords: String? = null
    @SerializedName("subsection") @Expose val subsection: String? = null
    @SerializedName("share_count") @Expose val shareCount: Int? = null
    @SerializedName("count_type") @Expose val countType: String? = null
    @SerializedName("column") @Expose val column: Any? = null
    @SerializedName("eta_id") @Expose val etaId: Int? = null
    @SerializedName("section") @Expose val section: String? = null
    @SerializedName("id") @Expose val id: Long? = null
    @SerializedName("asset_id") @Expose val assetId: Long? = null
    @SerializedName("nytdsection") @Expose val nytdsection: String? = null
    @SerializedName("byline") @Expose val byline: String? = null
    @SerializedName("type") @Expose val type: String? = null
    @SerializedName("title") @Expose val title: String? = null
    @SerializedName("abstract") @Expose val _abstract: String? = null
    @SerializedName("published_date") @Expose val publishedDate: String? = null
    @SerializedName("source") @Expose val source: String? = null
    @SerializedName("updated") @Expose val updated: String? = null
    @SerializedName("media") @Expose val media: List<MostPopularMedium>? = null
    @SerializedName("uri") @Expose val uri: String? = null

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
        val inputDateFormat:String="yyyy-MM-dd"
        val outputDateFormat:String="MM/dd/yyyy"
        return Utilities.convertDate(inputDateFormat, outputDateFormat, this.publishedDate)
    }

    override fun getArticleImageUrl(): String? {
        val media:List<MostPopularMedium>?=this.media
        val mediaMetadata: List<MostPopularMediaMetadatum>?
        if(!media.isNullOrEmpty()) {
            mediaMetadata = media.get(0).mediaMetadata
            if(!mediaMetadata.isNullOrEmpty()) {
                return mediaMetadata.get(0).url
            }
        }
        return null
    }

    override fun getArticleUrl(): String? {
        return this.url
    }
}