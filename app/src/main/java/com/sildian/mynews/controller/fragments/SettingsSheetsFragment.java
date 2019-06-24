package com.sildian.mynews.controller.fragments;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.sildian.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/*************************************************************************************************
 * SettingsSheetsFragment
 * Allows the user to add new sheets in the menu by selecting sections
 *************************************************************************************************/

public class SettingsSheetsFragment extends SettingsBaseFragment {

    /**Components**/

    @BindView(R.id.fragment_settings_sheets_sections) TableLayout sectionsLayout;

    /**Constructor**/

    public SettingsSheetsFragment() {

    }

    /**Callback methods**/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_settings_sheets, container, false);
        ButterKnife.bind(this, view);
        generateSectionsCheckBoxes(this.sectionsLayout);
        return view;
    }
}
