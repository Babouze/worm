package com.ddl.hat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ddl.worm.R;

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
    }

    public void startPlayActivity(View v) {
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }

    public void startSettingsActivity(View v) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}