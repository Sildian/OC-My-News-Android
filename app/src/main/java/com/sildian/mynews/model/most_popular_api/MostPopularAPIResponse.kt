package com.sildian.mynews.model.most_popular_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**************************************************************************************************
 * MostPopularAPIResponse
 * Monitors the data from NYT Most popular API
 *************************************************************************************************/

class MostPopularAPIResponse {

    @SerializedName("status") @Expose val status: String? = null
    @SerializedName("copyright") @Expose val copyright: String? = null
    @SerializedName("num_results") @Expose val numResults: Int? = null
    @SerializedName("results") @Expose val results: List<MostPopularArticle>? = null
}