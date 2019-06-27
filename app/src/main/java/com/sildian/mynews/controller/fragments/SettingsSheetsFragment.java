package com.sildian.mynews.controller.fragments;


import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import com.sildian.mynews.R;
import com.sildian.mynews.model.UserSettings;

import butterknife.BindView;

/*************************************************************************************************
 * SettingsSheetsFragment
 * Allows the user to add new sheets in the menu by selecting sections
 *************************************************************************************************/

public class SettingsSheetsFragment extends SettingsBaseFragment implements View.OnClickListener{

    /**Components**/

    @BindView(R.id.fragment_settings_sheets_sections) TableLayout sectionsLayout;
    @BindView(R.id.fragment_settings_sheets_button_validate) Button validateButton;

    /**Constructor**/

    public SettingsSheetsFragment(UserSettings userSettings) {
        super(userSettings);
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
    protected TableLayout getSectionsLayout() {
        return this.sectionsLayout;
    }

    @Override
    protected void initializeViews() {
        this.validateButton.setOnClickListener(this);
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
