package com.sildian.mynews.model

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Multimedium {

    @SerializedName("url") @Expose val url:String=""
    @SerializedName("format") @Expose val format:String=""
    @SerializedName("height") @Expose val height:Int=0
    @SerializedName("width") @Expose val width:Int=0
    @SerializedName("type") @Expose val type:String=""
    @SerializedName("subtype") @Expose val subtype:String=""
    @SerializedName("caption") @Expose val caption:String=""
    @SerializedName("copyright") @Expose val copyright:String=""
}