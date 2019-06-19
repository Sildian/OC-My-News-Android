package com.sildian.mynews.model.articles_search_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**************************************************************************************************
 * SearchByLine
 * Monitors the data from NYT Search API
 *************************************************************************************************/

class SearchByline {

    @SerializedName("original") @Expose val original: String? = null
    @SerializedName("person") @Expose val person: List<SearchPerson>? = null
    @SerializedName("organization") @Expose val organization: Any? = null
}