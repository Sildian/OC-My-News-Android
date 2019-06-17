package com.sildian.mynews.view;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sildian.mynews.R;
import com.sildian.mynews.controller.activities.MainActivity;
import com.sildian.mynews.controller.fragments.MainFragment;

/************************************************************************************************
 * MainFragmentAdapter
 * Monitors the fragments to be displayed in MainActivity with the view pager
 ***********************************************************************************************/

public class MainFragmentAdapter extends FragmentPagerAdapter {

    /**Constructor**/

    public MainFragmentAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    /**Adapter methods**/

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String[] pagesNames=MainActivity.APPLICATION.getResources().getStringArray(R.array.view_pager_pages_names);
        String pageName= pagesNames[position];
        return pageName;
    }

    @Override
    public Fragment getItem(int position) {
        return new MainFragment(position);
    }
}
