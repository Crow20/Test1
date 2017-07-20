package com.sike.courses.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.sike.courses.R;
import com.sike.courses.service.MyService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by agritsenko on 13.07.2017.
 */

public class PrefActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    SharedPreferences sp;
    protected int counter = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Настройки");
        PreferenceManager.setDefaultValues(this, R.xml.pref, false);
        addPreferencesFromResource(R.xml.pref);
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(sp.getBoolean("autoupdate", false)){
            counter++;
            if(counter>1){
                stopService(new Intent(this, MyService.class));
                startService(new Intent(this, MyService.class).putExtra("time", Integer.valueOf(sp.getString("timeupdate", "1"))));
            }else{
                startService(new Intent(this, MyService.class).putExtra("time", Integer.valueOf(sp.getString("timeupdate", "1"))));
            }
            Toast.makeText(getApplicationContext(), "Автообновление включено", Toast.LENGTH_SHORT).show();
        }else {
            stopService(new Intent(this, MyService.class));
            Toast.makeText(getApplicationContext(), "Автообновление отключено", Toast.LENGTH_SHORT).show();
        }

    }

}
