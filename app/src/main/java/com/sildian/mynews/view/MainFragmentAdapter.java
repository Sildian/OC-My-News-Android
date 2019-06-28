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

    /**Static attributes**/

    public static final int NB_SHEETS_BASE=3;      //The base number of sheets

    /**Attributes**/

    private UserSettings userSettings;              //The user settings
    private int nbSheets;                           //THe number of sheets

    /**Constructor**/

    public MainFragmentAdapter(FragmentManager fragmentManager, UserSettings userSettings){
        super(fragmentManager);
        this.userSettings=userSettings;
        this.nbSheets=NB_SHEETS_BASE+this.userSettings.getSheetsSections().size();
    }

    /**Adapter methods**/

    @Override
    public int getCount() {
        return this.nbSheets;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String pageName;
        if(position<NB_SHEETS_BASE) {
            String[] pagesNames = MainActivity.APPLICATION.getResources().getStringArray(R.array.view_pager_pages_names);
            pageName = pagesNames[position];
        }
        else{
            pageName=this.userSettings.getSheetsSections().get(position-NB_SHEETS_BASE);
        }
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
            if(fragment.getMainFragmentId()==MainFragment.ID_SEARCH) {
                fragment.refreshQuery();
            }
        }
        return super.getItemPosition(object);
    }

    /**Updates the user settings and updates the fragments**/

    public void updateUserSettings(UserSettings userSettings){
        this.userSettings=userSettings;
        this.nbSheets=NB_SHEETS_BASE+this.userSettings.getSheetsSections().size();
        notifyDataSetChanged();
    }
}
