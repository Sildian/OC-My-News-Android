package com.sildian.mynews.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**************************************************************************************************
 * MostPopularAPIResponse
 * Monitors the data from NYT Most popular API
 *************************************************************************************************/

class MostPopularMediaMetadatum {

    @SerializedName("url") @Expose var url: String? = null
    @SerializedName("format") @Expose var format: String? = null
    @SerializedName("height") @Expose var height: Int? = null
    @SerializedName("width") @Expose var width: Int? = null
}