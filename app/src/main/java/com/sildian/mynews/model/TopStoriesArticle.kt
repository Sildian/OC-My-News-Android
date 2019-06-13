package com.sildian.mynews.model

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**************************************************************************************************
 * TopStoriesArticle
 * Monitors the data from NYT Top stories API
 *************************************************************************************************/

class TopStoriesArticle {

    @SerializedName("section") @Expose var section:String??=null
    @SerializedName("subsection") @Expose var subsection:String??=null
    @SerializedName("title") @Expose var title:String?=null
    @SerializedName("abstract") @Expose var _abstract:String?=null
    @SerializedName("url") @Expose var url:String?=null
    @SerializedName("byline") @Expose var byline:String?=null
    @SerializedName("item_type") @Expose var itemType:String?=null
    @SerializedName("updated_date") @Expose var updatedDate:String?=null
    @SerializedName("created_date") @Expose var createdDate:String?=null
    @SerializedName("published_date") @Expose var publishedDate:String?=null
    @SerializedName("material_type_facet") @Expose var materialTypeFacet:String?=null
    @SerializedName("kicker") @Expose var kicker:String?=null
    @SerializedName("des_facet") @Expose var desFacet:List<String>?=null
    @SerializedName("org_facet") @Expose var orgFacet:List<Object>?=null
    @SerializedName("per_facet") @Expose var perFacet:List<Object>?=null
    @SerializedName("geo_facet") @Expose var geoFacet:List<String>?=null
    @SerializedName("multimedia") @Expose var multimedia:List<TopStoriesMultimedium>?=null
    @SerializedName("short_url") @Expose var shortUrl:String?=null
}