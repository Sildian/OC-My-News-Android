package com.sildian.mynews.model.articles_search_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**************************************************************************************************
 * SearchLegacy
 * Monitors the data from NYT Search API
 *************************************************************************************************/

class SearchLegacy {

    @SerializedName("xlarge") @Expose val xlarge: String? = null
    @SerializedName("xlargewidth") @Expose val xlargewidth: Int? = null
    @SerializedName("xlargeheight") @Expose val xlargeheight: Int? = null
    @SerializedName("thumbnail") @Expose val thumbnail: String? = null
    @SerializedName("thumbnailwidth") @Expose val thumbnailwidth: Int? = null
    @SerializedName("thumbnailheight") @Expose val thumbnailheight: Int? = null
    @SerializedName("widewidth") @Expose val widewidth: Int? = null
    @SerializedName("wideheight") @Expose val wideheight: Int? = null
    @SerializedName("wide") @Expose val wide: String? = null
}