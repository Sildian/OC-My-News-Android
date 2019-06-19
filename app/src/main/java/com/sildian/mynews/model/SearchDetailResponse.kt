package com.sildian.mynews.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**************************************************************************************************
 * SearchDetailResponse
 * Monitors the data from NYT Search API
 *************************************************************************************************/

class SearchDetailResponse {

    @SerializedName("docs") @Expose val docs: List<SearchArticle>? = null
}