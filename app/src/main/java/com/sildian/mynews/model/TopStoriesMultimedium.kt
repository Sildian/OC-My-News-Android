package com.sildian.mynews.model

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**************************************************************************************************
 * TopStoriesMultimedium
 * Monitors the data from NYT Top stories API
 *************************************************************************************************/

class TopStoriesMultimedium {

    @SerializedName("url") @Expose var url:String??=null
    @SerializedName("format") @Expose var format:String?=null
    @SerializedName("height") @Expose var height:Int?=0
    @SerializedName("width") @Expose var width:Int?=0
    @SerializedName("type") @Expose var type:String?=null
    @SerializedName("subtype") @Expose var subtype:String?=null
    @SerializedName("caption") @Expose var caption:String?=null
    @SerializedName("copyright") @Expose var copyright:String?=null
}