package com.sildian.mynews.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**************************************************************************************************
 * MostPopularAPIResponse
 * Monitors the data from NYT Most popular API
 *************************************************************************************************/

class MostPopularAPIResponse {

    @SerializedName("status") @Expose var status: String? = null
    @SerializedName("copyright") @Expose var copyright: String? = null
    @SerializedName("num_results") @Expose var numResults: Int? = null
    @SerializedName("results") @Expose var results: List<MostPopularArticle>? = null
}