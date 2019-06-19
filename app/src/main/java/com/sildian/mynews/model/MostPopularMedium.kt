package com.sildian.mynews.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**************************************************************************************************
 * MostPopularMedium
 * Monitors the data from NYT Most popular API
 *************************************************************************************************/

class MostPopularMedium {

    @SerializedName("type") @Expose val type: String? = null
    @SerializedName("subtype") @Expose val subtype: String? = null
    @SerializedName("caption") @Expose val caption: String? = null
    @SerializedName("copyright") @Expose val copyright: String? = null
    @SerializedName("approved_for_syndication") @Expose val approvedForSyndication: Int? = null
    @SerializedName("media-metadata") @Expose val mediaMetadata: List<MostPopularMediaMetadatum>? = null
}