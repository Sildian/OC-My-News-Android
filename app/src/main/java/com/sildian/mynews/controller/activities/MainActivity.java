package com.sildian.mynews.controller.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.sildian.mynews.R;
import com.sildian.mynews.view.MainFragmentAdapter;

/*************************************************************************************************
 * MainActivity
 * This activity displays fragments running queries to get lists of articles from the
 * New York Times API.
 ************************************************************************************************/

public class MainActivity extends AppCompatActivity {

    /**Static attributes**/

    public static Application APPLICATION;

    /**Callback methods**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APPLICATION=getApplication();
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.activity_main_toolbar));
        initializeViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_main_add:
                Log.i("CHECK_MENU", "Add");
                break;
            case R.id.menu_main_search:
                Log.i("CHECK_MENU", "Search");
                break;
            case R.id.menu_main_notifications:
                Log.i("CHECK_MENU", "Notifications");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**Initializes the view pager and displays the fragments**/

    private void initializeViewPager(){
        ViewPager viewPager=findViewById(R.id.activity_main_view_pager);
        viewPager.setAdapter(new MainFragmentAdapter(getSupportFragmentManager()));
        TabLayout tabLayout=findViewById(R.id.activity_main_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
