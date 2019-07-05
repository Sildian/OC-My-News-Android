package com.sildian.mynews.controller.fragments;


import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sildian.mynews.R;
import com.sildian.mynews.model.UserSettings;
import com.sildian.mynews.view.DatePickerFragment;

import butterknife.BindView;

/*************************************************************************************************
 * SettingsSearchFragment
 * Allows the user to set search parameters
 *************************************************************************************************/

public class SettingsSearchFragment extends SettingsBaseFragment implements View.OnClickListener, View.OnFocusChangeListener {

    /**Components**/

    @BindView(R.id.fragment_settings_search_keywords) TextView keyWordsText;
    @BindView(R.id.fragment_settings_search_recycler_view) RecyclerView sectionsRecyclerView;
    @BindView(R.id.fragment_settings_search_begin_date) EditText beginDateText;
    @BindView(R.id.fragment_settings_search_end_date) EditText endDateText;
    @BindView(R.id.fragment_settings_search_button_validate) Button validateButton;

    /**Constructor**/

    public SettingsSearchFragment(UserSettings userSettings) {
        super(userSettings);
    }

    public SettingsSearchFragment() {
        super();
    }

    /**Callback methods**/

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus) {
            if (v == beginDateText || v == endDateText) {
                showDatePickerDialog(v);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v==this.validateButton) {
            validateSettings();
        }
    }

    /**Refreshes the screen**/

    @Override
    protected void refreshScreen(){
        this.keyWordsText.setText(this.userSettings.getSearchKeyWords());
        this.beginDateText.setText(this.userSettings.getSearchBeginDate());
        this.endDateText.setText(this.userSettings.getSearchEndDate());
    }

    /**Shows a Date picker dialog**/

    private void showDatePickerDialog(View v) {
        DialogFragment dialogFragment = new DatePickerFragment(v);
        dialogFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    /**SettingsBaseFragment abstract methods**/

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_settings_search;
    }

    @Override
    protected RecyclerView getSectionsRecyclerView() {
        return this.sectionsRecyclerView;
    }

    @Override
    protected void initializeViews() {
        this.beginDateText.setInputType(InputType.TYPE_NULL);
        this.beginDateText.setOnFocusChangeListener(this);
        this.endDateText.setInputType(InputType.TYPE_NULL);
        this.endDateText.setOnFocusChangeListener(this);
        this.validateButton.setOnClickListener(this);
    }

    @Override
    protected boolean updateUserSettings() {
        int nbSectionsChecked=updateUserSettingsSections();
        this.userSettings.setSearchKeyWords(this.keyWordsText.getText().toString());
        this.userSettings.setSearchBeginDate(this.beginDateText.getText().toString());
        this.userSettings.setSearchEndDate(this.endDateText.getText().toString());
        return nbSectionsChecked>0;
    }

    @Override
    public void validateSettings() {

        /*Updates the user settings*/

        boolean sectionsChecked=updateUserSettings();

        /*If no key words or no sections are selected, then shows the caution dialog. Else finishes the activity.*/

        if(this.keyWordsText.getText().toString().isEmpty()||!sectionsChecked){
            showCautionDialog();
        } else {
            finishActivity(true);
        }
    }
}
