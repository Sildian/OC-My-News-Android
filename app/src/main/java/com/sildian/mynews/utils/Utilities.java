package com.sildian.mynews.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    /**Converts a date's format
     * @param inputDateFormat : the input date format
     * @param outputDateFormat : the output date format
     * @param inputDate : the input date
     * @return the output date
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

    /**Generates a date according to a given format pattern and year, month, day
     * @param dateFormat : the format pattern
     * @param year : the year
     * @param month : the month
     * @param day : the day
     * @return : the date
     */

    public static String generateDate(String dateFormat, int year, int month, int day){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat format=new SimpleDateFormat(dateFormat);
        return format.format(calendar.getTime());
    }

    /**Gets the local date format pattern
     * @return a String containing the local date format's pattern
     */

    public static String getLocalDateFormatPattern(){
        DateFormat dateFormat=DateFormat.getDateInstance(DateFormat.SHORT);
        SimpleDateFormat simpleDateFormat=(SimpleDateFormat) dateFormat;
        String formatPattern=simpleDateFormat.toPattern();
        return formatPattern;
    }
}
