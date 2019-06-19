package com.sildian.mynews.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**************************************************************************************************
 * SearchPerson
 * Monitors the data from NYT Search API
 *************************************************************************************************/

class SearchPerson {

    @SerializedName("firstname") @Expose val firstname: String? = null
    @SerializedName("middlename") @Expose val middlename: Any? = null
    @SerializedName("lastname") @Expose val lastname: String? = null
    @SerializedName("qualifier") @Expose val qualifier: Any? = null
    @SerializedName("title") @Expose val title: Any? = null
    @SerializedName("role") @Expose val role: String? = null
    @SerializedName("organization") @Expose val organization: String? = null
    @SerializedName("rank") @Expose val rank: Int? = null
}