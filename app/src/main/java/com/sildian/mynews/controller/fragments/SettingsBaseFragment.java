package com.sildian.mynews.controller.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.sildian.mynews.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*************************************************************************************************
 * SettingsBaseFragment
 * The base fragment for showing the settings options.
 * The settings options can be shown to add new sheets,
 * to search articles or to activate/deactivate notifications
 *************************************************************************************************/

public class SettingsBaseFragment extends Fragment {

    /**Components**/

    @BindView(R.id.fragment_settings_base_table) TableLayout sectionsTableLayout;
    private List<TableRow> sectionsTableRows;
    private List<CheckBox> sectionsCheckBoxes;

    /**Constructor**/

    public SettingsBaseFragment() {
        this.sectionsTableRows=new ArrayList<>();
        this.sectionsCheckBoxes=new ArrayList<>();
    }

    /**Callback methods**/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_settings_base, container, false);
        ButterKnife.bind(this, view);
        generateSectionsCheckBoxes();
        return view;
    }

    /**Generates check boxes allowing to select the sections
     * The number of check boxes depends on R.array.sections_names's size
     * 2 check boxes are shown by row**/

    private void generateSectionsCheckBoxes(){

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
}
