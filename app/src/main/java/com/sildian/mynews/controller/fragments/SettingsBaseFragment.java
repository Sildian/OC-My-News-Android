package com.sildian.mynews.controller.fragments;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.sildian.mynews.R;
import com.sildian.mynews.controller.activities.SettingsActivity;
import com.sildian.mynews.model.UserSettings;
import com.sildian.mynews.view.SectionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import icepick.Icepick;
import icepick.State;

/*************************************************************************************************
 * SettingsBaseFragment
 * The base fragment for showing the settings options.
 * The settings options can be shown to add new sheets,
 * to search articles or to activate/deactivate notifications
 *************************************************************************************************/

public abstract class SettingsBaseFragment extends Fragment {

    /**Components**/

    protected SectionAdapter sectionAdapter;                    //The sections recycler view's adapter

    /**Attributes**/

    @State UserSettings userSettings;                           //The user settings

    /**Abstract methods**/

    protected abstract int getFragmentLayout();                 //Gets the fragment layout
    protected abstract RecyclerView getSectionsRecyclerView();  //Gets the sections recycler view
    protected abstract void initializeViews();                  //Initializes the views in the layout
    protected abstract void refreshScreen();                    //Refreshes the components in the screen
    protected abstract boolean updateUserSettings();            //Updates the user settings
    public abstract void validateSettings();                    //Validates the settings and eventually finishes the activity

    /**Constructor**/

    public SettingsBaseFragment(UserSettings userSettings) {
        this.userSettings=userSettings;
    }

    public SettingsBaseFragment(){
        this.userSettings=null;
    }

    /**Callback methods**/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this, view);
        Icepick.restoreInstanceState(this, savedInstanceState);
        initializeViews();
        initializeSectionsRecyclerView();
        refreshScreen();
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    protected void initializeSectionsRecyclerView(){

        /*Gets the full list of sections names*/

        String[] sectionsNames=getResources().getStringArray(R.array.sections_names);
        List<String> sectionsNamesList=new ArrayList<>();
        for(String sectionName:sectionsNames){
            sectionsNamesList.add(sectionName);
        }

        /*Sets the adapter with the sections names and the correct user settings list to feed the checkboxes*/

        SettingsActivity settingsActivity=(SettingsActivity) getActivity();
        switch(settingsActivity.getId()){
            case SettingsActivity.ID_SHEETS:
                this.sectionAdapter=new SectionAdapter(sectionsNamesList, this.userSettings.getSheetsSections());
                break;
            case SettingsActivity.ID_SEARCH:
                this.sectionAdapter=new SectionAdapter(sectionsNamesList, this.userSettings.getSearchSections());
                break;
            case SettingsActivity.ID_NOTIFICATIONS:
                this.sectionAdapter=new SectionAdapter(sectionsNamesList, this.userSettings.getNotificationSections());
                break;
            default:
                this.sectionAdapter=new SectionAdapter(sectionsNamesList, this.userSettings.getSheetsSections());
                break;
        }

        /*Sets the adapter and the layout manager to the recycler view*/

        getSectionsRecyclerView().setAdapter(this.sectionAdapter);
        getSectionsRecyclerView().setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    /**Gets an item from the sections recycler view
     * @param position : the position of the item
     * @return : the component contained in the item
     */

    protected CheckBox getItemFromSectionRecyclerView(int position){
        View view=getSectionsRecyclerView().getChildAt(position);
        CheckBox sectionCheckbox=view.findViewById(R.id.checkbox_section);
        return sectionCheckbox;
    }

    /**Updates the user settings sections
     * @return the number of checked sections
     */

    protected int updateUserSettingsSections(){

        /*Initializes the number of checked sections to 0 and gets the activity*/

        int nbSectionsChecked=0;
        SettingsActivity settingsActivity=(SettingsActivity) getActivity();

        /*For each check box, updates the user settings section depending on the activity's id*/

        for(int i=0;i<this.sectionAdapter.getItemCount();i++){

            switch (settingsActivity.getId()){
                case SettingsActivity.ID_SHEETS:
                    this.userSettings.updateSheetsSections
                            (getItemFromSectionRecyclerView(i).getText().toString(), getItemFromSectionRecyclerView(i).isChecked());
                    break;
                case SettingsActivity.ID_SEARCH:
                    this.userSettings.updateSearchSections
                            (getItemFromSectionRecyclerView(i).getText().toString(), getItemFromSectionRecyclerView(i).isChecked());
                    break;
                case SettingsActivity.ID_NOTIFICATIONS:
                    this.userSettings.updateNotificationSections
                            (getItemFromSectionRecyclerView(i).getText().toString(), getItemFromSectionRecyclerView(i).isChecked());
                    break;
                default:
                    break;
            }

            /*Then if the current check box is checked, increases the number of checked sections*/

            if(getItemFromSectionRecyclerView(i).isChecked()){
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
