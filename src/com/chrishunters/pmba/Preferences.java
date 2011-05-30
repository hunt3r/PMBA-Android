package com.chrishunters.pmba;
//import android.app.Activity;
//import android.content.SharedPreferences;
import android.os.Bundle;
//import android.preference.Preference;
import android.preference.PreferenceActivity;
//import android.preference.Preference.OnPreferenceClickListener;
//import android.widget.Toast;
 
public class Preferences extends PreferenceActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        	super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.layout.preferences);
            
        }
}