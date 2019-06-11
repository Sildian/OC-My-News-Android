package com.sildian.mynews.model

class UserSettings (){

    var sheetsSections=emptyList<String>(); private set
    var searchKeyWords:String=""
    var searchSections=emptyList<String>(); private set
    var notificationKeyWords:String=""
    var notificationSections=emptyList<String>(); private set
    var notificationOn:Boolean=false

    private fun updateSections(sectionsArray:ArrayList<String>, section:String, sectionOn:Boolean) {

    }

    fun updateSheetsSections(section:String, sectionOn:Boolean){

    }

    fun updateSearchSections(section:String, sectionOn:Boolean){

    }

    fun updateNotificationSections(section:String, sectionOn:Boolean){

    }

}