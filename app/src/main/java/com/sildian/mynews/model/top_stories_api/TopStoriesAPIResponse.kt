package com.sildian.mynews.model.top_stories_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**************************************************************************************************
 * TopStoriesAPIResponse
 * Monitors the data from NYT Top stories API
 *************************************************************************************************/

open class TopStoriesAPIResponse {

    open @SerializedName("status") @Expose val status: String? = null
    open @SerializedName("copyright") @Expose val copyright: String? = null
    open @SerializedName("section") @Expose val section: String? = null
    open @SerializedName("last_updated") @Expose val lastUpdated: String? = null
    open @SerializedName("num_results") @Expose val numResults: Int? = null
    open @SerializedName("results") @Expose val results: List<TopStoriesArticle>? = null
}