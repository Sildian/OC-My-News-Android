package com.sildian.mynews.model

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class TopStoriesArticle {

    @SerializedName("section") @Expose val section:String=""
    @SerializedName("subsection") @Expose val subsection:String=""
    @SerializedName("title") @Expose val title:String="";
    @SerializedName("abstract") @Expose val _abstract:String=""
    @SerializedName("url") @Expose val url:String=""
    @SerializedName("byline") @Expose val byline:String=""
    @SerializedName("item_type") @Expose val itemType:String=""
    @SerializedName("updated_date") @Expose val updatedDate:String=""
    @SerializedName("created_date") @Expose val createdDate:String=""
    @SerializedName("published_date") @Expose val publishedDate:String=""
    @SerializedName("material_type_facet") @Expose val materialTypeFacet:String=""
    @SerializedName("kicker") @Expose val kicker:String=""
    @SerializedName("des_facet") @Expose val desFacet=listOf<String>()
    @SerializedName("org_facet") @Expose val orgFacet=listOf<Object>()
    @SerializedName("per_facet") @Expose val perFacet=listOf<Object>()
    @SerializedName("geo_facet") @Expose val geoFacet=listOf<String>()
    @SerializedName("multimedia") @Expose val multimedia=listOf<Multimedium>()
    @SerializedName("short_url") @Expose val shortUrl:String=""
}