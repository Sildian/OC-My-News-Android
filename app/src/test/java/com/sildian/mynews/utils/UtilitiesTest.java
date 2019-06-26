package com.sildian.mynews.utils;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UtilitiesTest {

    /**convertQueryWord**/

    @Test
    public void given_RealEstate_when_convertQueryWord_then_checkResult() {
        String word="Real estate";
        String result=Utilities.convertQueryWord(word);
        assertEquals("realestate", result);
    }

    /**generateQueryFilter**/

    @Test
    public void given_sectionsFoodAndArts_when_generateQueryFilter_then_checkResult() {

        String filterName="section_name.contains";
        ArrayList<String> filters=new ArrayList<String>();
        filters.add("food");
        filters.add("arts");

        String result=Utilities.generateQueryFilter(filterName, filters);
        assertEquals("section_name.contains:(\"food\"\"arts\")", result);
    }

    /**convertDate**/

    @Test
    public void given_simpleDateFormat_when_convertDate_then_checkResult(){
        String inputDateFormat="yyyy-MM-dd";
        String outputDateFormat="MM/dd/yyyy";
        String date="2019-06-20";
        String result=Utilities.convertDate(inputDateFormat, outputDateFormat, date);
        assertEquals("06/20/2019", result);
    }

    @Test
    public void given_complexDateFormat_when_convertDate_then_checkResult(){
        String inputDateFormat="yyyy-MM-dd'T'HH:mm:ss";
        String outputDateFormat="MM/dd/yyyy";
        String date="2019-06-20T05:27:03-04:00";
        String result=Utilities.convertDate(inputDateFormat, outputDateFormat, date);
        assertEquals("06/20/2019", result);
    }

    /**generateDate**/

    @Test
    public void given_dateToDisplay_when_generateDate_then_checkResult(){
        String dateFormat="MM/dd/yyyy";
        int year=2019;
        int month=5;
        int day=20;
        String date=Utilities.generateDate(dateFormat, year, month, day);
        assertEquals("06/20/2019", date);
    }

    @Test
    public void given_dateToQuery_when_generateDate_then_checkResult(){
        String dateFormat="yyyyMMdd";
        int year=2019;
        int month=5;
        int day=20;
        String date=Utilities.generateDate(dateFormat, year, month, day);
        assertEquals("20190620", date);
    }
}