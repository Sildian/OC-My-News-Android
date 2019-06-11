package com.sildian.mynews.model

import org.junit.Test

import org.junit.Assert.*

class UserSettingsTest {

    /**Constructor**/

    @Test
    fun given_none_when_newUserSettings_then_checkOK(){
        val userSettings:UserSettings=UserSettings()
        assertTrue(userSettings.sheetsSections.isEmpty())
        assertEquals("", userSettings.searchKeyWords)
        assertTrue(userSettings.searchSections.isEmpty())
        assertEquals("", userSettings.notificationKeyWords)
        assertTrue(userSettings.notificationSections.isEmpty())
        assertFalse(userSettings.notificationOn)
    }

    /**updateSheetsSections**/

    @Test
    fun given_addCookies_when_updateSheetsSections_then_checkCookie() {
        val userSettings:UserSettings=UserSettings()
        userSettings.updateSheetsSections("Cookies", true)
        assertTrue(userSettings.sheetsSections.contains("Cookies"))
    }

    @Test
    fun given_addAndRemoveCookies_when_updateSheetsSections_then_checkNoCookies() {
        val userSettings:UserSettings=UserSettings()
        userSettings.updateSheetsSections("Cookies", true)
        userSettings.updateSheetsSections("Cookies", false)
        assertFalse(userSettings.sheetsSections.contains("Cookies"))
    }

    @Test
    fun given_addCookiesTwice_when_updateSheetsSections_then_checkOnlyOneCookie() {
        val userSettings:UserSettings=UserSettings()
        userSettings.updateSheetsSections("Cookies", true)
        userSettings.updateSheetsSections("Cookies", true)
        assertTrue(userSettings.sheetsSections.size==1)
    }

    /**updateSearchSections**/

    @Test
    fun given_addCookies_when_updateSearchSections_then_checkCookie() {
        val userSettings:UserSettings=UserSettings()
        userSettings.updateSearchSections("Cookies", true)
        assertTrue(userSettings.searchSections.contains("Cookies"))
    }

    @Test
    fun given_addAndRemoveCookies_when_updateSearchSections_then_checkNoCookies() {
        val userSettings:UserSettings=UserSettings()
        userSettings.updateSearchSections("Cookies", true)
        userSettings.updateSearchSections("Cookies", false)
        assertFalse(userSettings.searchSections.contains("Cookies"))
    }

    @Test
    fun given_addCookiesTwice_when_updateSearchSections_then_checkOnlyOneCookie() {
        val userSettings:UserSettings=UserSettings()
        userSettings.updateSearchSections("Cookies", true)
        userSettings.updateSearchSections("Cookies", true)
        assertTrue(userSettings.searchSections.size==1)
    }

    /**UpdateNotificationSections**/

    @Test
    fun given_addCookies_when_updateNotificationSections_then_checkCookie() {
        val userSettings:UserSettings=UserSettings()
        userSettings.updateNotificationSections("Cookies", true)
        assertTrue(userSettings.notificationSections.contains("Cookies"))
    }

    @Test
    fun given_addAndRemoveCookies_when_updateNotificationSections_then_checkNoCookies() {
        val userSettings:UserSettings=UserSettings()
        userSettings.updateNotificationSections("Cookies", true)
        userSettings.updateNotificationSections("Cookies", false)
        assertFalse(userSettings.notificationSections.contains("Cookies"))
    }

    @Test
    fun given_addCookiesTwice_when_updateNotificationSections_then_checkOnlyOneCookie() {
        val userSettings:UserSettings=UserSettings()
        userSettings.updateNotificationSections("Cookies", true)
        userSettings.updateNotificationSections("Cookies", true)
        assertTrue(userSettings.notificationSections.size==1)
    }
}