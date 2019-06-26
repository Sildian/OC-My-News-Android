package com.sildian.mynews.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*************************************************************************************************
 * Utilities
 * This class provides with static utilities static methods to be used elsewhere
 ************************************************************************************************/

public class Utilities {

    /**Converts a query word to make it ready to be sent to the API
     * @param inputQueryWord : this word can contain UPPER CASES and " "
     * @return an outputQueryWord ready to be sent to the API, full lower cases and without " "
     */

    public static String convertQueryWord(String inputQueryWord){
        String temporaryQueryWord=inputQueryWord.replace(" ", "");
        String outputQueryWord=temporaryQueryWord.toLowerCase();
        return outputQueryWord;
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

    /**Converts a date's format from the article to the date to display
     * @param inputDateFormat : the input date format
     * @param outputDateFormat : the output date format
     * @param inputDate : the date received from an article
     * @return a String containing the date to display
     */

    public static String convertDate(String inputDateFormat, String outputDateFormat, String inputDate){

        String outputDate=null;

        if(!inputDate.isEmpty()) {

            SimpleDateFormat inputFormat = new SimpleDateFormat(inputDateFormat);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputDateFormat);

            Date date = new Date();
            try {
                date = inputFormat.parse(inputDate);
            } catch (ParseException e) {
                Log.d("CHECK_DATE", e.getMessage());
                return null;
            }

            outputDate = outputFormat.format(date);
        }

        return outputDate;
    }
}
