package com.sildian.mynews.view;

import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.sildian.mynews.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**************************************************************************************************
 * DatePickerFragment
 * Provides a dialog allowing the user to pick a Date in a calendar
 *************************************************************************************************/

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    /**Attributes**/

    private DatePickerDialog datePickerDialog;          //The date picker dialog
    private EditText callView;                          //The view calling the dialog
    private int year;                                   //The date picker's init year
    private int month;                                  //The date picker's init month
    private int day;                                    //The date picker's init day

    /**Constructor
     * @param v : the view calling the dialog
     */

    public DatePickerFragment(View v){
        this.callView=(EditText)v;
    }

    /**Callback methods**/

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initializeDate();
        this.datePickerDialog=new DatePickerDialog(getActivity(), R.style.DatePickerTheme, this, this.year, this.month, this.day);
        return this.datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        String dateToDisplay=generateDateToDisplay(year, month, day);
        this.callView.setText(dateToDisplay);
        this.callView.clearFocus();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        this.callView.setText("");
        this.callView.clearFocus();
        super.onCancel(dialog);
    }

    /**Initializes the date picker's date**/

    private void initializeDate(){

        /*Creates an instance of calendar*/

        Calendar calendar=Calendar.getInstance();

        /*If the callView is empty, then sets the date to the current date*/

        if(this.callView.getText().toString().isEmpty()) {
            this.year = calendar.get(Calendar.YEAR);
            this.month = calendar.get(Calendar.MONTH);
            this.day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        /*Else then sets the date to the existing date in the callView*/

        else {
            SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");
            try {
                calendar.setTime(dateFormat.parse(this.callView.getText().toString()));
            }
            catch(ParseException e){
                Log.d("CHECK_DATE", e.getMessage());
                callView.setText("");
                initializeDate();
            }
            this.year=calendar.get(Calendar.YEAR);
            this.month=calendar.get(Calendar.MONTH);
            this.day=calendar.get(Calendar.DAY_OF_MONTH);
        }
    }

    /**Generates a date to display in the callView
     * @param year : the year
     * @param month : the month
     * @param day : the day
     * @return the date to display
     */

    private String generateDateToDisplay(int year, int month, int day){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(calendar.getTime());
    }
}
