package com.sildian.mynews.model

/*************************************************************************************************
 * Article
 * This interface should be implemented within each kind of article to define which data
 * it will give
 *************************************************************************************************/

interface Article {

    fun getArticleSection():String?

    fun getArticleSubSection():String?

    fun getArticleTitle():String?

    fun getArticleDate():String?

    fun getArticleImageUrl():String?

    fun getArticleUrl():String?
}