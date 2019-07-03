package com.sildian.mynews.model.most_popular_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**************************************************************************************************
 * MostPopularAPIResponse
 * Monitors the data from NYT Most popular API
 *************************************************************************************************/

open class MostPopularAPIResponse {

    open @SerializedName("status") @Expose val status: String? = null
    open @SerializedName("copyright") @Expose val copyright: String? = null
    open @SerializedName("num_results") @Expose val numResults: Int? = null
    open @SerializedName("results") @Expose val results: List<MostPopularArticle>? = null
}