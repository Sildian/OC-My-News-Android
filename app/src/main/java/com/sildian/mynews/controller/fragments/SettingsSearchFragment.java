package com.sildian.mynews.controller.fragments;


import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
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

public class SettingsSearchFragment extends SettingsBaseFragment implements View.OnClickListener, View.OnTouchListener {

    /**Components**/

    @BindView(R.id.fragment_settings_search_keywords) TextView keyWordsText;
    @BindView(R.id.fragment_settings_search_sections) TableLayout sectionsLayout;
    @BindView(R.id.fragment_settings_search_begin_date) Spinner beginDateText;
    @BindView(R.id.fragment_settings_search_end_date) Spinner endDateText;
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
        this.beginDateText.setOnTouchListener(this);
        this.endDateText.setOnTouchListener(this);
        this.validateButton.setOnClickListener(this);
        refreshScreen();
        return view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v==beginDateText||v==endDateText){
            showDatePickerDialog(v);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v==validateButton) {
            for (CheckBox sectionCheckBox : this.sectionsCheckBoxes) {
                this.userSettings.updateSearchSections(sectionCheckBox.getText().toString(), sectionCheckBox.isChecked());
            }
            getActivity().finish();
        }
    }

    /**Refreshes the screen**/

    private void refreshScreen(){
        super.refreshScreen(this.userSettings.getSearchSections());
    }

    /**Shows a Date picker dialog**/

    private void showDatePickerDialog(View v) {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
}
