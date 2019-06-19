package com.sildian.mynews.model.articles_search_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**************************************************************************************************
 * SearchKeyword
 * Monitors the data from NYT Search API
 *************************************************************************************************/

class SearchKeyword {

    @SerializedName("name") @Expose val name: String? = null
    @SerializedName("value") @Expose val value: String? = null
    @SerializedName("rank") @Expose val rank: Int? = null
    @SerializedName("major") @Expose val major: String? = null
}