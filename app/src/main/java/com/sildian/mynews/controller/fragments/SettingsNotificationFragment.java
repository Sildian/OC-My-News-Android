package com.sildian.mynews.controller.fragments;


import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;

import com.sildian.mynews.R;
import com.sildian.mynews.model.UserSettings;

import butterknife.BindView;

/*************************************************************************************************
 * SettingsNotificationFragment
 * Allows the user to set the notification parameters
 *************************************************************************************************/

public class SettingsNotificationFragment extends SettingsBaseFragment implements View.OnClickListener{

    /**Components**/

    @BindView(R.id.fragment_settings_notification_keywords) TextView keyWordsText;
    @BindView(R.id.fragment_settings_notification_sections) TableLayout sectionsLayout;
    @BindView(R.id.fragment_settings_notification_switch) Switch notificationSwitch;
    @BindView(R.id.fragment_settings_notification_button_validate) Button validateButton;

    /**Constructor**/

    public SettingsNotificationFragment(UserSettings userSettings) {
        super(userSettings);
    }

    /**Callback methods**/

    @Override
    public void onClick(View v) {
        if(v==this.validateButton) {
            validateSettings();
        }
    }

    /**Refreshes the screen**/

    protected void refreshScreen(){
        super.refreshScreen();
        this.keyWordsText.setText(this.userSettings.getNotificationKeyWords());
        this.notificationSwitch.setChecked(this.userSettings.getNotificationOn());
    }

    /**SettingsBaseFragment abstract methods**/

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_settings_notification;
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
        int nbSectionsChecked=updateUserSettingsSections();
        this.userSettings.setNotificationKeyWords(this.keyWordsText.getText().toString());
        this.userSettings.setNotificationOn(this.notificationSwitch.isChecked());
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
