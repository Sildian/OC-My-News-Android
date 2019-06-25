package com.sildian.mynews.controller.fragments;


import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import com.sildian.mynews.R;
import com.sildian.mynews.model.UserSettings;
import com.sildian.mynews.view.DatePickerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/*************************************************************************************************
 * SettingsSearchFragment
 * Allows the user to set search parameters
 *************************************************************************************************/

public class SettingsSearchFragment extends SettingsBaseFragment implements View.OnClickListener, View.OnFocusChangeListener {

    /**Components**/

    @BindView(R.id.fragment_settings_search_keywords) TextView keyWordsText;
    @BindView(R.id.fragment_settings_search_sections) TableLayout sectionsLayout;
    @BindView(R.id.fragment_settings_search_begin_date) EditText beginDateText;
    @BindView(R.id.fragment_settings_search_end_date) EditText endDateText;
    @BindView(R.id.fragment_settings_search_button_validate) Button validateButton;

    /**Constructor**/

    public SettingsSearchFragment(UserSettings userSettings) {
        super(userSettings);
    }

    /**Callback methods**/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_settings_search, container, false);
        ButterKnife.bind(this, view);
        super.generateSectionsCheckBoxes(this.sectionsLayout);
        this.beginDateText.setInputType(InputType.TYPE_NULL);
        this.beginDateText.setOnFocusChangeListener(this);
        this.endDateText.setInputType(InputType.TYPE_NULL);
        this.endDateText.setOnFocusChangeListener(this);
        this.validateButton.setOnClickListener(this);
        refreshScreen();
        return view;
    }

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
        if(v==validateButton) {
            for (CheckBox sectionCheckBox : this.sectionsCheckBoxes) {
                this.userSettings.updateSearchSections(sectionCheckBox.getText().toString(), sectionCheckBox.isChecked());
            }
            this.userSettings.setSearchKeyWords(this.keyWordsText.getText().toString());
            this.userSettings.setSearchBeginDate(this.beginDateText.getText().toString());
            this.userSettings.setSearchEndDate(this.endDateText.getText().toString());
            getActivity().finish();
        }
    }

    /**Refreshes the screen**/

    private void refreshScreen(){
        super.refreshScreen(this.userSettings.getSearchSections());
        this.keyWordsText.setText(this.userSettings.getSearchKeyWords());
        this.beginDateText.setText(this.userSettings.getSearchBeginDate());
        this.endDateText.setText(this.userSettings.getSearchEndDate());
    }

    /**Shows a Date picker dialog**/

    private void showDatePickerDialog(View v) {
        DialogFragment dialogFragment = new DatePickerFragment(v);
        dialogFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
}
