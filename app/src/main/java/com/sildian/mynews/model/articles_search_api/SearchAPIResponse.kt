package com.sildian.mynews.model.articles_search_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**************************************************************************************************
 * SearchAPIResponse
 * Monitors the data from NYT Search API
 *************************************************************************************************/

open class SearchAPIResponse {

    open @SerializedName("status") @Expose val status: String? = null
    open @SerializedName("copyright") @Expose val copyright: String? = null
    open @SerializedName("response") @Expose val response: SearchDetailResponse? = null
}