package com.sildian.mynews.model.articles_search_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**************************************************************************************************
 * SearchDetailResponse
 * Monitors the data from NYT Search API
 *************************************************************************************************/

open class SearchDetailResponse {

    open @SerializedName("docs") @Expose val docs: List<SearchArticle>? = null
}