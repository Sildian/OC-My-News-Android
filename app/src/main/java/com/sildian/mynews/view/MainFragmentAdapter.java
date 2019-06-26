package com.sildian.mynews.view;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sildian.mynews.R;
import com.sildian.mynews.controller.activities.MainActivity;
import com.sildian.mynews.controller.fragments.MainFragment;
import com.sildian.mynews.model.UserSettings;

/************************************************************************************************
 * MainFragmentAdapter
 * Monitors the fragments to be displayed in MainActivity with the view pager
 ***********************************************************************************************/

public class MainFragmentAdapter extends FragmentPagerAdapter {

    /**Attributes**/

    private UserSettings userSettings;              //The user settings

    /**Constructor**/

    public MainFragmentAdapter(FragmentManager fragmentManager, UserSettings userSettings){
        super(fragmentManager);
        this.userSettings=userSettings;
    }

    /**Adapter methods**/

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String[] pagesNames=MainActivity.APPLICATION.getResources().getStringArray(R.array.view_pager_pages_names);
        String pageName= pagesNames[position];
        return pageName;
    }

    @Override
    public MainFragment getItem(int position) {
        return new MainFragment(position, this.userSettings);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        MainFragment fragment=(MainFragment) object;
        if(fragment!=null){
            fragment.updateUserSettings(this.userSettings);
            fragment.refreshQuery();
        }
        return super.getItemPosition(object);
    }

    /**Updates the user settings and updates the fragments**/

    public void updateUserSettings(UserSettings userSettings){
        this.userSettings=userSettings;
        notifyDataSetChanged();
    }
}
