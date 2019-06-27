package com.sildian.mynews.controller.fragments;


import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.sildian.mynews.R;
import com.sildian.mynews.controller.activities.SettingsActivity;
import com.sildian.mynews.model.UserSettings;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/*************************************************************************************************
 * SettingsBaseFragment
 * The base fragment for showing the settings options.
 * The settings options can be shown to add new sheets,
 * to search articles or to activate/deactivate notifications
 *************************************************************************************************/

public abstract class SettingsBaseFragment extends Fragment {

    /**Components**/

    protected TableLayout sectionsTableLayout;          //The table
    protected List<TableRow> sectionsTableRows;         //The rows in the table
    protected List<CheckBox> sectionsCheckBoxes;        //The check boxes

    /**Attributes**/

    protected UserSettings userSettings;                //The user settings

    /**Abstract methods**/

    protected abstract int getFragmentLayout();         //Gets the fragment layout
    protected abstract TableLayout getSectionsLayout(); //Gets the sections layout
    protected abstract void initializeViews();          //Initializes the views in the layout
    protected abstract boolean updateUserSettings();    //Updates the user settings
    public abstract void validateSettings();            //Validates the settings and eventually finishes the activity

    /**Constructor**/

    public SettingsBaseFragment(UserSettings userSettings) {
        this.sectionsTableRows=new ArrayList<>();
        this.sectionsCheckBoxes=new ArrayList<>();
        this.userSettings=userSettings;
    }

    /**Callback methods**/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this, view);
        generateSectionsCheckBoxes(getSectionsLayout());
        initializeViews();
        refreshScreen();
        return view;
    }

    /**Generates check boxes allowing to select the sections
     * @param sectionsTableLayout : the base Table layout
     * The number of check boxes depends on R.array.sections_names's size
     * 2 check boxes are shown by row
     */


    protected void generateSectionsCheckBoxes(TableLayout sectionsTableLayout){

        this.sectionsTableLayout=sectionsTableLayout;

        String[] sections= getResources().getStringArray(R.array.sections_names);       //List of sections
        View sectionTableRowTemplate;                                                   //Gets a template to create a row in the table
        View sectionCheckBoxTemplate;                                                   //Gets a template to create a check box

        /*For each item in the list...*/

        for(int i=0;i<sections.length;i++){

            /*If the current item id is not a multiple of 2, then creates a new row in the table*/

            if(((double)i+1)%2!=0){
                sectionTableRowTemplate=getLayoutInflater()
                        .inflate(R.layout.checkbox_section_row_template, this.sectionsTableLayout, false);
                this.sectionsTableRows.add(sectionTableRowTemplate.findViewById(R.id.checkbox_section_row));
                this.sectionsTableLayout.addView(this.sectionsTableRows.get(this.sectionsTableRows.size()-1));
            }

            /*Then adds a check box in the last row*/

            sectionCheckBoxTemplate=getLayoutInflater()
                    .inflate(R.layout.checkbox_section_template, this.sectionsTableRows.get(this.sectionsTableRows.size()-1), false);
            this.sectionsCheckBoxes.add(sectionCheckBoxTemplate.findViewById(R.id.checkbox_section));
            this.sectionsCheckBoxes.get(this.sectionsCheckBoxes.size()-1).setText(sections[i]);
            this.sectionsTableRows.get(this.sectionsTableRows.size()-1)
                    .addView(this.sectionsCheckBoxes.get(this.sectionsCheckBoxes.size()-1));
        }
    }

    /**Refreshes the screen*/

    protected void refreshScreen(){

        /*Creates a list of sections and gets the activity*/

        List<String> sections;
        SettingsActivity settingsActivity=(SettingsActivity) getActivity();

        /*Feeds the list of sections depending on the activity's id*/

        switch(settingsActivity.getId()){
            case SettingsActivity.ID_SHEETS:
                sections=this.userSettings.getSheetsSections();
                break;
            case SettingsActivity.ID_SEARCH:
                sections=this.userSettings.getSearchSections();
                break;
            case SettingsActivity.ID_NOTIFICATIONS:
                sections=this.userSettings.getNotificationSections();
                break;
            default:
                sections=new ArrayList();
                break;
        }

        /*Then for each check box, sets it true or false*/

        for(CheckBox sectionCheckBox:this.sectionsCheckBoxes){
            if(sections.contains(sectionCheckBox.getText().toString())){
                sectionCheckBox.setChecked(true);
            }
        }
    }

    /**Updates the user settings sections
     * @return the number of checked sections
     */

    protected int updateUserSettingsSections(){

        /*Initializes the number of checked sections to 0 and gets the activity*/

        int nbSectionsChecked=0;
        SettingsActivity settingsActivity=(SettingsActivity) getActivity();

        /*For each check box, updates the user settings section depending on the activity's id*/

        for (CheckBox sectionCheckBox : this.sectionsCheckBoxes) {
            switch (settingsActivity.getId()){
                case SettingsActivity.ID_SHEETS:
                    this.userSettings.updateSheetsSections(sectionCheckBox.getText().toString(), sectionCheckBox.isChecked());
                    break;
                case SettingsActivity.ID_SEARCH:
                    this.userSettings.updateSearchSections(sectionCheckBox.getText().toString(), sectionCheckBox.isChecked());
                    break;
                case SettingsActivity.ID_NOTIFICATIONS:
                    this.userSettings.updateNotificationSections(sectionCheckBox.getText().toString(), sectionCheckBox.isChecked());
                    break;
                default:
                    break;
            }

            /*Then if the current check box is checked, increases the number of checked sections*/

            if(sectionCheckBox.isChecked()){
                nbSectionsChecked++;
            }
        }
        return nbSectionsChecked;
    }

    /**Shows a dialog box notifying the use that some fields are empty**/

    protected void showCautionDialog(){
        AlertDialog.Builder cautionDialog=new AlertDialog.Builder(getActivity());
        cautionDialog.setTitle(R.string.dialog_settings_caution_title);
        cautionDialog.setMessage(R.string.dialog_settings_caution_message);
        cautionDialog.setNeutralButton(R.string.dialog_neutral_button_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        cautionDialog.create().show();
    }

    /**Finishes the activity
     * @param success : true if finishes with success
     */

    protected void finishActivity(boolean success){
        SettingsActivity settingsActivity=(SettingsActivity) getActivity();
        if(success){
            settingsActivity.setActivityResultSuccess();
        }
        else{
            settingsActivity.setActivityResultAbort();
        }
        settingsActivity.finish();
    }
}
