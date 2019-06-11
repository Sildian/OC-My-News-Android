package com.sildian.mynews.model

/************************************************************************************************
 * UserSettings
 * This class is used to store and monitor the user's settings
 ***********************************************************************************************/

class UserSettings (){

    var sheetsSections=arrayListOf<String>(); private set               //The additional sections in the main menu
    var searchKeyWords:String=""                                        //The key words in the search options
    var searchSections=arrayListOf<String>(); private set               //The sections in the search options
    var notificationKeyWords:String=""                                  //The key words in the notification options
    var notificationSections=arrayListOf<String>(); private set         //The sections in the notification options
    var notificationOn:Boolean=false                                    //Notifications on or off

    /** Updates a list of sections
     * @param sectionsList : the list of sections to be updated
     * @param section : the section to be added or removed from the list
     * @param sectionOn : is the section on or off?
     */

    private fun updateSections(sectionsList:ArrayList<String>, section:String, sectionOn:Boolean) {

        val i:Int=sectionsList.indexOf(section)

        when{
            i==-1 && sectionOn -> sectionsList.add(section)
            i!=-1 && !sectionOn -> sectionsList.remove(section)
        }
    }

    /**Updates the sheetsSection list**/

    fun updateSheetsSections(section:String, sectionOn:Boolean){
        updateSections(this.sheetsSections, section, sectionOn)
    }

    /**Updates the searchSections list **/

    fun updateSearchSections(section:String, sectionOn:Boolean){
        updateSections(this.searchSections, section, sectionOn)
    }

    /**Updates the notificationSections list**/

    fun updateNotificationSections(section:String, sectionOn:Boolean){
        updateSections(this.notificationSections, section, sectionOn)
    }

}