package com.sildian.mynews.controller.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;

import com.sildian.mynews.R;
import com.sildian.mynews.model.UserSettings;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_settings_notification, container, false);
        ButterKnife.bind(this, view);
        super.generateSectionsCheckBoxes(this.sectionsLayout);
        this.validateButton.setOnClickListener(this);
        refreshScreen();
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v==validateButton) {
            for (CheckBox sectionCheckBox : this.sectionsCheckBoxes) {
                this.userSettings.updateNotificationSections(sectionCheckBox.getText().toString(), sectionCheckBox.isChecked());
            }
            this.userSettings.setNotificationKeyWords(this.keyWordsText.getText().toString());
            this.userSettings.setNotificationOn(this.notificationSwitch.isChecked());
            getActivity().finish();
        }
    }

    /**Refreshes the screen**/

    private void refreshScreen(){
        super.refreshScreen(this.userSettings.getNotificationSections());
        this.keyWordsText.setText(this.userSettings.getNotificationKeyWords());
        this.notificationSwitch.setChecked(this.userSettings.getNotificationOn());
    }
}
