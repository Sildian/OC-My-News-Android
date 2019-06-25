package com.sildian.mynews.model

import android.os.Parcel
import android.os.Parcelable

/************************************************************************************************
 * UserSettings
 * This class is used to store and monitor the user's settings
 ***********************************************************************************************/

class UserSettings : Parcelable {

    var sheetsSections=arrayListOf<String>(); private set               //The additional sections in the main menu
    var searchKeyWords:String=""                                        //The key words in the search options
    var searchBeginDate:String=""                                       //The begin date in the search options
    var searchEndDate:String=""                                         //The end date in the search options
    var searchSections=arrayListOf<String>(); private set               //The sections in the search options
    var notificationKeyWords:String=""                                  //The key words in the notification options
    var notificationSections=arrayListOf<String>(); private set         //The sections in the notification options
    var notificationOn:Boolean=false                                    //Notifications on or off

    /**Default constructor**/

    constructor(){

    }

    /**Constructor as parcelable**/

    constructor(parcel: Parcel){
        parcel.readStringList(this.sheetsSections)
        this.searchKeyWords=parcel.readString()
        this.searchBeginDate=parcel.readString()
        this.searchEndDate=parcel.readString()
        parcel.readStringList(this.searchSections)
        this.notificationKeyWords=parcel.readString()
        parcel.readStringList(this.notificationSections)
        when(parcel.readInt()){
            0 -> this.notificationOn=false
            else -> this.notificationOn=true
        }
    }

    /**Write as parcelable**/

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        if(dest!=null) {
            dest.writeStringList(this.sheetsSections)
            dest.writeString(this.searchKeyWords)
            dest.writeString(this.searchBeginDate)
            dest.writeString(this.searchEndDate)
            dest.writeStringList(this.searchSections)
            dest.writeString(this.notificationKeyWords)
            dest.writeStringList(this.notificationSections)
            when (this.notificationOn) {
                false -> dest.writeInt(0)
                else -> dest.writeInt(1)
            }
        }
    }

    /**Describe contents as parcelable**/

    override fun describeContents(): Int {
        return 0
    }

    /**Parcelable creator**/

    companion object CREATOR:Parcelable.Creator<UserSettings>{

        override fun createFromParcel(source: Parcel): UserSettings {
            return UserSettings(source)
        }

        override fun newArray(size: Int): Array<UserSettings?> {
            return arrayOfNulls(size)
        }
    }

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