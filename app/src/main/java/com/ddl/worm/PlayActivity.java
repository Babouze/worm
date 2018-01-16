package com.ddl.worm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

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
                switch (direction) {
                    case up:
                        onUpSwipe();
                        return true;
                    case left:
                        onLeftSwipe();
                        return true;
                    case down:
                        onDownSwipe();
                        return true;
                    case right:
                        onRightSwipe();
                        return true;
                }

                return super.onSwipe(direction);
            }
        });

//        m_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//                Toast.makeText(PlayActivity.this, Integer.toString(position),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });

        m_gridView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        m_swipeDetector.onTouchEvent(motionEvent);
        view.performClick();
        return true;
    }

    private void onDownSwipe() {
        //TODO: implement
    }

    private void onUpSwipe() {
        //TODO: implement
    }

    private void onRightSwipe() {
        //TODO: implement
    }

    private void onLeftSwipe() {
        //TODO: implement
    }
}
