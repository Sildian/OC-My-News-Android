package com.sildian.mynews.model.articles_search_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**************************************************************************************************
 * SearchMultimedium
 * Monitors the data from NYT Search API
 *************************************************************************************************/

class SearchMultimedium {

    @SerializedName("rank") @Expose val rank: Int? = null
    @SerializedName("subtype") @Expose val subtype: String? = null
    @SerializedName("caption") @Expose val caption: Any? = null
    @SerializedName("credit") @Expose val credit: Any? = null
    @SerializedName("type") @Expose val type: String? = null
    @SerializedName("url") @Expose val url: String? = null
    @SerializedName("height") @Expose val height: Int? = null
    @SerializedName("width") @Expose val width: Int? = null
    @SerializedName("legacy") @Expose val legacy: SearchLegacy? = null
    @SerializedName("subType") @Expose val subType: String? = null
    @SerializedName("crop_name") @Expose val cropName: String? = null
}