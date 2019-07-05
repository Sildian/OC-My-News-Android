package com.sildian.mynews.controller.fragments;


import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.sildian.mynews.R;
import com.sildian.mynews.model.UserSettings;

import butterknife.BindView;

/*************************************************************************************************
 * SettingsSheetsFragment
 * Allows the user to add new sheets in the menu by selecting sections
 *************************************************************************************************/

public class SettingsSheetsFragment extends SettingsBaseFragment implements View.OnClickListener{

    /**Components**/

    @BindView(R.id.fragment_settings_sheets_recycler_view) RecyclerView sectionsRecyclerView;
    @BindView(R.id.fragment_settings_sheets_button_validate) Button validateButton;

    /**Constructor**/

    public SettingsSheetsFragment(UserSettings userSettings) {
        super(userSettings);
    }

    public SettingsSheetsFragment() {
        super();
    }

    /**Callback methods**/

    @Override
    public void onClick(View v) {
        if(v==validateButton) {
            validateSettings();
        }
    }

    /**SettingsBaseFragment abstract methods**/

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_settings_sheets;
    }

    @Override
    protected RecyclerView getSectionsRecyclerView() {
        return this.sectionsRecyclerView;
    }

    @Override
    protected void initializeViews() {
        this.validateButton.setOnClickListener(this);
    }

    @Override
    protected void refreshScreen() {

    }

    @Override
    protected boolean updateUserSettings() {
        updateUserSettingsSections();
        return true;
    }

    @Override
    public void validateSettings() {
        updateUserSettings();
        finishActivity(true);
    }
}
