package com.sildian.mynews.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**************************************************************************************************
 * SearchHeadline
 * Monitors the data from NYT Search API
 *************************************************************************************************/

class SearchHeadline {

    @SerializedName("main") @Expose val main: String? = null
    @SerializedName("kicker") @Expose val kicker: Any? = null
    @SerializedName("content_kicker") @Expose val contentKicker: Any? = null
    @SerializedName("print_headline") @Expose val printHeadline: String? = null
    @SerializedName("name") @Expose val name: Any? = null
    @SerializedName("seo") @Expose val seo: Any? = null
    @SerializedName("sub") @Expose val sub: Any? = null
}