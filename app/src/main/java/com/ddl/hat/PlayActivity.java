package com.ddl.hat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.ddl.hat.level.Levels;
import com.ddl.hat.utils.ImageAdapter;
import com.ddl.hat.utils.OnSwipeListener;
import com.ddl.hat.R;

import java.util.Locale;

public class PlayActivity extends AppCompatActivity implements View.OnTouchListener {

    GridView m_gridView;
    private GestureDetectorCompat m_swipeDetector;
    private int m_moves;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        m_gridView = findViewById(R.id.grid);

        // If we reach the last level
        if (getIntent().getIntExtra("level", Levels.m_levels.size() + 1) <= Levels.m_levels.size()) {
            m_gridView.setAdapter(new ImageAdapter(this, getIntent()));
        } else {
            Intent intent = new Intent(this, HomeActivity.class);
            this.startActivity(intent);
        }

        m_swipeDetector = new GestureDetectorCompat(this, new OnSwipeListener() {
            @Override
            public boolean onSwipe(Direction direction) {
                ((ImageAdapter) m_gridView.getAdapter()).makeHatMove(direction);
                addMove();
                return super.onSwipe(direction);
            }
        });

        m_gridView.setOnTouchListener(this);

        m_moves = 0;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        m_swipeDetector.onTouchEvent(motionEvent);
        view.performClick();
        return true;
    }

    private void addMove() {
        m_moves++;
        ((TextView) findViewById(R.id.moves)).setText(String.format(Locale.FRANCE, "Moves : %d", m_moves));
    }
}
