package com.sildian.mynews.utils;


import com.sildian.mynews.controller.activities.MainActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*************************************************************************************************
 * Utilities
 * This class provides with static utilities static methods to be used elsewhere
 ************************************************************************************************/

public class Utilities {

    /**Converts a date's format from the article to the date to display
     * @param inputDate : a String containing the date received from an article
     * @return a String containing the date to display
     */

    public static String convertDate(String inputDate){

        SimpleDateFormat inputFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH-mm");
        DateFormat outputFormat=android.text.format.DateFormat.getDateFormat(MainActivity.APPLICATION);

        Date date=new Date();
        try {
            date = inputFormat.parse(inputDate);
        }
        catch(ParseException e){}

        String outputDate=outputFormat.format(date);

        return outputDate;
    }

    /**Generates a query filter to be sent to an API
     * @param filterName : the filter name
     * @param filters : the list of filters
     * @return a String ready to be sent to the API
     */

    public static String generateQueryFilter(String filterName, List<String> filters){

        StringBuilder filterBuilder=new StringBuilder();
        filterBuilder.append(filterName+":(");

        for(String filter:filters){
            filterBuilder.append("\""+filter+"\"");
        }
        filterBuilder.append(")");

        return filterBuilder.toString();
    }
}
