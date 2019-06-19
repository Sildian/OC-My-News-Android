package com.sildian.mynews.model.top_stories_api

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**************************************************************************************************
 * TopStoriesMultimedium
 * Monitors the data from NYT Top stories API
 *************************************************************************************************/

class TopStoriesMultimedium {

    @SerializedName("url") @Expose val url:String??=null
    @SerializedName("format") @Expose val format:String?=null
    @SerializedName("height") @Expose val height:Int?=0
    @SerializedName("width") @Expose val width:Int?=0
    @SerializedName("type") @Expose val type:String?=null
    @SerializedName("subtype") @Expose val subtype:String?=null
    @SerializedName("caption") @Expose val caption:String?=null
    @SerializedName("copyright") @Expose val copyright:String?=null
}