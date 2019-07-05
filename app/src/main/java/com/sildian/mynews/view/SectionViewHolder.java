package com.sildian.mynews.view;

import android.view.View;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.sildian.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/************************************************************************************************
 * SectionViewHolder
 * Displays the sections checkboxes
 ***********************************************************************************************/

public class SectionViewHolder extends RecyclerView.ViewHolder {

    /**Components**/

    @BindView(R.id.checkbox_section) CheckBox sectionCheckBox;

    /**Constructor**/

    public SectionViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    /**Updates
     * @param sectionName : the section name
     * @param sectionStatus : the section status (checked or not checked)
     */

    public void update(String sectionName, boolean sectionStatus){
        this.sectionCheckBox.setText(sectionName);
        this.sectionCheckBox.setChecked(sectionStatus);
    }
}
