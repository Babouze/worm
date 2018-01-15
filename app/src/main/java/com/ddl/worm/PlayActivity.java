package com.ddl.worm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.ddl.worm.utils.ImageAdapter;

public class PlayActivity extends AppCompatActivity {
    GridView m_gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        m_gridView = findViewById(R.id.grid);
        m_gridView.setAdapter(new ImageAdapter(this));

        m_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(PlayActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
