package com.sildian.mynews.controller.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sildian.mynews.R;
import com.sildian.mynews.model.utils.services.TopStoriesArticlesService;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_SECTION="SECTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar)findViewById(R.id.activity_main_toolbar));

        Intent topStoriesIntent=new Intent(MainActivity.this, TopStoriesArticlesService.class);
        topStoriesIntent.putExtra(KEY_SECTION, "arts");
        startService(topStoriesIntent);

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
}
