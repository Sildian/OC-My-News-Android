package com.sildian.mynews.utils;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UtilitiesTest {

    @Test
    public void given_RealEstate_when_convertQueryWord_then_checkResult() {
        String word="Real estate";
        String result=Utilities.convertQueryWord(word);
        assertEquals("realestate", result);
    }

    @Test
    public void given_sectionsFoodAndArts_when_generateQueryFilter_then_checkResult() {

        String filterName="section_name.contains";
        ArrayList<String> filters=new ArrayList<String>();
        filters.add("food");
        filters.add("arts");

        String result=Utilities.generateQueryFilter(filterName, filters);
        assertEquals("section_name.contains:(\"food\"\"arts\")", result);
    }
}