package com.ddl.hat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ddl.hat.level.Levels;
import com.google.gson.Gson;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: add instabug support

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // Get the version name from package
        String versionName = "0.0";
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // Set the version name on home activity
        try {
            ((TextView) findViewById(R.id.version)).setText(String.format("Version %s", versionName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //save levels to JSON
        //TODO: ne pas effectuer le traitement suivant s'il a déjà été fait
        SharedPreferences mPrefs = getSharedPreferences(getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = mPrefs.edit();
        Gson gson = new Gson();
        for (int i = 1; i < Levels.m_levels.size(); i++) {
            ed.putString("level" + i, gson.toJson(Levels.m_levels.get(i, null)));
        }
        ed.commit();
    }

    public void startPlayActivity(View v) {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("level", 1);
        startActivity(intent);
    }

    public void startSettingsActivity(View v) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void startLevelsActivity(View view) {
        Intent intent = new Intent(this, LevelsActivity.class);
        startActivity(intent);
    }
}