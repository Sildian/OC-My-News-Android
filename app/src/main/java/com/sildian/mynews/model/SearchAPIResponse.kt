package com.sildian.mynews.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**************************************************************************************************
 * SearchAPIResponse
 * Monitors the data from NYT Search API
 *************************************************************************************************/

class SearchAPIResponse {

    @SerializedName("status") @Expose val status: String? = null
    @SerializedName("copyright") @Expose val copyright: String? = null
    @SerializedName("response") @Expose val response: SearchDetailResponse? = null
}