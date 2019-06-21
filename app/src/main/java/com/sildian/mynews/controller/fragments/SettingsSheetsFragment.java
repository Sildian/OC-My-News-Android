package com.sildian.mynews.controller.fragments;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.sildian.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SettingsSheetsFragment extends SettingsBaseFragment {

    @BindView(R.id.fragment_settings_sheets_sections) TableLayout sectionsLayout;

    public SettingsSheetsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_settings_sheets, container, false);
        ButterKnife.bind(this, view);
        super.onCreateView(inflater, container, savedInstanceState, this.sectionsLayout);
        return view;
    }
}
