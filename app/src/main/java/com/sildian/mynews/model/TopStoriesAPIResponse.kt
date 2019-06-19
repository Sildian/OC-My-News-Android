package com.sildian.mynews.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**************************************************************************************************
 * TopStoriesAPIResponse
 * Monitors the data from NYT Top stories API
 *************************************************************************************************/

class TopStoriesAPIResponse {

    @SerializedName("status") @Expose val status: String? = null
    @SerializedName("copyright") @Expose val copyright: String? = null
    @SerializedName("section") @Expose val section: String? = null
    @SerializedName("last_updated") @Expose val lastUpdated: String? = null
    @SerializedName("num_results") @Expose val numResults: Int? = null
    @SerializedName("results") @Expose val results: List<TopStoriesArticle>? = null
}