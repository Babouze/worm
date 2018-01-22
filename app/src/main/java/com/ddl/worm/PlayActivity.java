package com.ddl.worm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.ddl.worm.utils.ImageAdapter;
import com.ddl.worm.utils.OnSwipeListener;

public class PlayActivity extends AppCompatActivity implements View.OnTouchListener {

    GridView m_gridView;
    private GestureDetectorCompat m_swipeDetector;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        m_gridView = findViewById(R.id.grid);
        m_gridView.setAdapter(new ImageAdapter(this));

        m_swipeDetector = new GestureDetectorCompat(this, new OnSwipeListener() {
            @Override
            public boolean onSwipe(Direction direction) {
                ((ImageAdapter) m_gridView.getAdapter()).makeWormMove(direction);
                return super.onSwipe(direction);
            }
        });

        m_gridView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        m_swipeDetector.onTouchEvent(motionEvent);
        view.performClick();
        return true;
    }

    public void getCoordonates(View v) {
        Toast.makeText(this, "indice : " + ImageAdapter.mHeadPosition, Toast.LENGTH_LONG);
    }
}
