package com.sildian.mynews.model.most_popular_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**************************************************************************************************
 * MostPopularMediaMetadatum
 * Monitors the data from NYT Most popular API
 *************************************************************************************************/

class MostPopularMediaMetadatum {

    @SerializedName("url") @Expose val url: String? = null
    @SerializedName("format") @Expose val format: String? = null
    @SerializedName("height") @Expose val height: Int? = null
    @SerializedName("width") @Expose val width: Int? = null
}