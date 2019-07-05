package com.sildian.mynews.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sildian.mynews.R;

import java.util.List;

/************************************************************************************************
 * SectionAdapter
 * Monitors the sections data within a recycler view
 ***********************************************************************************************/

public class SectionAdapter extends RecyclerView.Adapter<SectionViewHolder>{

    /**Attributes**/

    private List<String> sectionsNames;                 //The sections names
    private List<String> userSectionsChecked;           //The list containing the checked sections

    /**Constructor**/

    public SectionAdapter(List<String> sectionsNames, List<String> userSectionsChecked){
        this.sectionsNames=sectionsNames;
        this.userSectionsChecked=userSectionsChecked;
    }

    /**Adapter methods**/

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.checkbox_section_template, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder holder, int position, @NonNull List<Object> payloads) {
        boolean sectionChecked=false;
        if(this.userSectionsChecked.contains(this.sectionsNames.get(position))){
            sectionChecked=true;
        }
        holder.update(this.sectionsNames.get(position), sectionChecked);
    }

    @Override
    public int getItemCount() {
        return this.sectionsNames.size();
    }
}
