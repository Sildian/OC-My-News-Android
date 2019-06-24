package com.sildian.mynews.model

import android.os.Parcel
import org.junit.Assert.*
import org.junit.Test

class UserSettingsInstrumentTest{

    @Test
    fun given_userSettingsBeforeTransfer_when_createFromParcel_then_checkUserSettingsAfterTransfer(){

        val userSettingsBeforeTransfer:UserSettings= UserSettings()
        userSettingsBeforeTransfer.updateSheetsSections("Food", true)
        userSettingsBeforeTransfer.searchKeyWords="Donut"
        userSettingsBeforeTransfer.updateSearchSections("Food", true)
        userSettingsBeforeTransfer.notificationKeyWords="Canada"
        userSettingsBeforeTransfer.updateNotificationSections("World", true)
        userSettingsBeforeTransfer.notificationOn=true

        val parcel: Parcel = Parcel.obtain()
        userSettingsBeforeTransfer.writeToParcel(parcel, userSettingsBeforeTransfer.describeContents())
        parcel.setDataPosition(0)

        val userSettingsAfterTransfer:UserSettings=UserSettings.CREATOR.createFromParcel(parcel)
        assertEquals(1, userSettingsAfterTransfer.sheetsSections.size)
        assertEquals("Donut", userSettingsAfterTransfer.searchKeyWords)
        assertEquals(1, userSettingsAfterTransfer.searchSections.size)
        assertEquals("Canada", userSettingsAfterTransfer.notificationKeyWords)
        assertEquals(1, userSettingsAfterTransfer.notificationSections.size)
        assertTrue(userSettingsAfterTransfer.notificationOn)
    }
}