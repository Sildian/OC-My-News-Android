package com.sildian.mynews.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**************************************************************************************************
 * MostPopularAPIResponse
 * Monitors the data from NYT Most popular API
 *************************************************************************************************/

class MostPopularArticle {

    @SerializedName("url") @Expose var url: String? = null
    @SerializedName("adx_keywords") @Expose var adxKeywords: String? = null
    @SerializedName("subsection") @Expose var subsection: String? = null
    @SerializedName("share_count") @Expose var shareCount: Int? = null
    @SerializedName("count_type") @Expose var countType: String? = null
    @SerializedName("column") @Expose var column: Any? = null
    @SerializedName("eta_id") @Expose var etaId: Int? = null
    @SerializedName("section") @Expose var section: String? = null
    @SerializedName("id") @Expose var id: Int? = null
    @SerializedName("asset_id") @Expose var assetId: Int? = null
    @SerializedName("nytdsection") @Expose var nytdsection: String? = null
    @SerializedName("byline") @Expose var byline: String? = null
    @SerializedName("type") @Expose var type: String? = null
    @SerializedName("title") @Expose var title: String? = null
    @SerializedName("abstract") @Expose var _abstract: String? = null
    @SerializedName("published_date") @Expose var publishedDate: String? = null
    @SerializedName("source") @Expose var source: String? = null
    @SerializedName("updated") @Expose var updated: String? = null
    @SerializedName("des_facet") @Expose var desFacet: List<String>? = null
    @SerializedName("org_facet") @Expose var orgFacet: List<String>? = null
    @SerializedName("per_facet") @Expose var perFacet: String? = null
    @SerializedName("geo_facet") @Expose var geoFacet: String? = null
    @SerializedName("media") @Expose var media: List<MostPopularMedium>? = null
    @SerializedName("uri") @Expose var uri: String? = null
}