package com.sildian.mynews.controller.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.sildian.mynews.R;
import com.sildian.mynews.controller.fragments.SettingsBaseFragment;
import com.sildian.mynews.controller.fragments.SettingsNotificationFragment;
import com.sildian.mynews.controller.fragments.SettingsSearchFragment;
import com.sildian.mynews.controller.fragments.SettingsSheetsFragment;
import com.sildian.mynews.model.UserSettings;

/*************************************************************************************************
 * SettingsActivity
 * This activity shows the settings options.
 * The settings options can be shown to add new sheets,
 * to search articles or to activate/deactivate notifications
 *************************************************************************************************/

public class SettingsActivity extends AppCompatActivity {

    /**The id will define which fragment is shown**/

    public static final int ID_SHEETS=0;                 //SettingsSheetsFragment will be shown
    public static final int ID_SEARCH=1;                 //SettingsSearchFragment will be shown
    public static final int ID_NOTIFICATIONS=2;          //SettingsNotificationsFragment will be shown

    /**The fragment**/

    private SettingsBaseFragment settingsFragment;

    /**Attributes**/

    private int id;                                     //This id defines which fragment will be shown
    private UserSettings userSettings;                  //The user settings

    /**Callback methods**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setSupportActionBar(findViewById(R.id.activity_settings_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.id=getIntent().getIntExtra(MainActivity.KEY_SETTINGS_ID, ID_SHEETS);
        this.userSettings=getIntent().getParcelableExtra(MainActivity.KEY_SETTINGS_USER);
        displaySettingsFragment();
    }

    @Override
    public void onBackPressed() {
        showBackDialog();
    }

    @Override
    public boolean onSupportNavigateUp() {
        showBackDialog();
        return true;
    }

    /**Sets the result that the activity returns after finishing with success**/

    public void setActivityResultSuccess(){
        Intent resultIntent=new Intent();
        resultIntent.putExtra(MainActivity.KEY_SETTINGS_ID, this.id);
        resultIntent.putExtra(MainActivity.KEY_SETTINGS_USER, this.userSettings);
        setResult(RESULT_OK, resultIntent);
    }

    /**Sets the result that the activity returns after finishing by aborting**/

    public void setActivityResultAbort(){
        Intent resultIntent=new Intent();
        resultIntent.putExtra(MainActivity.KEY_SETTINGS_ID, this.id);
        setResult(RESULT_CANCELED, resultIntent);
    }

    /**Displays the fragment depending on the id**/

    private void displaySettingsFragment(){

        String[] settingsPagesNames=getResources().getStringArray(R.array.settings_pages_names);
        getSupportActionBar().setTitle(settingsPagesNames[id]);

        switch(this.id){

            case ID_SHEETS:
                this.settingsFragment=(SettingsSheetsFragment)getSupportFragmentManager().findFragmentById(R.id.activity_settings_fragment);
                if(this.settingsFragment==null){
                    this.settingsFragment=new SettingsSheetsFragment(this.userSettings);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.activity_settings_fragment, this.settingsFragment)
                            .commit();
                }
                break;

            case ID_SEARCH:
                this.settingsFragment=(SettingsSearchFragment)getSupportFragmentManager().findFragmentById(R.id.activity_settings_fragment);
                if(this.settingsFragment==null){
                    this.settingsFragment=new SettingsSearchFragment(this.userSettings);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.activity_settings_fragment, this.settingsFragment)
                            .commit();
                }
                break;

            case ID_NOTIFICATIONS:
                this.settingsFragment=(SettingsNotificationFragment)getSupportFragmentManager().findFragmentById(R.id.activity_settings_fragment);
                if(this.settingsFragment==null){
                    this.settingsFragment=new SettingsNotificationFragment(this.userSettings);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.activity_settings_fragment, this.settingsFragment)
                            .commit();
                }
                break;

            default:
                break;
        }
    }

    /**Shows a dialog box asking confirmation to leave the activity**/

    public void showBackDialog(){
        AlertDialog.Builder backDialog=new AlertDialog.Builder(this);
        backDialog.setTitle(R.string.dialog_settings_back_title);
        backDialog.setMessage(R.string.dialog_settings_back_message);
        backDialog.setNegativeButton(R.string.dialog_negative_button_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setActivityResultAbort();
                finish();
            }
        });
        backDialog.setPositiveButton(R.string.dialog_positive_button_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                settingsFragment.validateSettings();
            }
        });
        backDialog.create().show();
    }

    /**Getters**/

    public int getId() {
        return id;
    }
}
