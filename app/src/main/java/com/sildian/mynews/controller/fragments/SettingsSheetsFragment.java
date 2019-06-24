package com.sildian.mynews.controller.fragments;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;

import com.sildian.mynews.R;
import com.sildian.mynews.model.UserSettings;

import butterknife.BindView;
import butterknife.ButterKnife;

/*************************************************************************************************
 * SettingsSheetsFragment
 * Allows the user to add new sheets in the menu by selecting sections
 *************************************************************************************************/

public class SettingsSheetsFragment extends SettingsBaseFragment implements View.OnClickListener{

    /**Components**/

    @BindView(R.id.fragment_settings_sheets_sections) TableLayout sectionsLayout;
    @BindView(R.id.fragment_settings_sheet_button_validate) Button validateButton;

    /**Constructor**/

    public SettingsSheetsFragment(UserSettings userSettings) {
        super(userSettings);
    }

    /**Callback methods**/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_settings_sheets, container, false);
        ButterKnife.bind(this, view);
        super.generateSectionsCheckBoxes(this.sectionsLayout);
        this.validateButton.setOnClickListener(this);
        refreshScreen();
        return view;
    }

    @Override
    public void onClick(View v) {
        for(CheckBox sectionCheckBox:this.sectionsCheckBoxes){
            this.userSettings.updateSheetsSections(sectionCheckBox.getText().toString(), sectionCheckBox.isChecked());
        }
        getActivity().finish();
    }

    /**Refreshes the screen**/

    private void refreshScreen(){
        super.refreshScreen(this.userSettings.getSheetsSections());
    }
}
