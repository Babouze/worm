package com.ddl.hat.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.ddl.hat.HomeActivity;
import com.ddl.hat.PlayActivity;
import com.ddl.hat.R;
import com.ddl.hat.level.Level;

import java.util.Arrays;

public class ImageAdapter extends BaseAdapter {
    private static int ROW_WIDTH = 10;
    private static int mHeadPosition;
    private Context mContext;
    private ImageView[] mThumbsViews;
    private boolean m_isWin = false;
    private Integer[] mThumbIds = null;
    private int m_levelNumber;
    private Integer[] m_gems = {R.drawable.gem_squared, R.drawable.gem_diamond, R.drawable.gem_drop, R.drawable.gem_octo};

    public ImageAdapter(Context c, Intent p_intent) {
        mContext = c;
        Level l_level = Level.getLevel(c, p_intent.getIntExtra("level", 1));

        if (l_level == null) { // Go to the home screen
            Intent intent = new Intent(mContext, HomeActivity.class);
            mContext.startActivity(intent);
        } else {
            m_levelNumber = l_level.getLevelNumber();
            mThumbIds = l_level.getmThumbIds();
            mThumbsViews = new ImageView[mThumbIds.length];
        }
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        ImageView imageView;
        imageView = new ImageView(mContext);
        imageView.setLayoutParams(new GridView.LayoutParams(90, 90));
        imageView.setPadding(0, 0, 0, 0);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    public long getItemId(int position) {
        return 0;
    }

    private ImageView getHatHeadView() {
        try {
            return mThumbsViews[mHeadPosition];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (mThumbsViews[position] != null) {
            imageView = mThumbsViews[position];
        } else if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(90, 90));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        // Set ressource
        imageView.setImageResource(mThumbIds[position]);

        // Register the view
        mThumbsViews[position] = imageView;

        // Save the head position
        if (mThumbIds[position] == R.drawable.hat)
            mHeadPosition = position;

        return imageView;
    }

    private Pair<Float, Float> getLastPositionInDirection(final OnSwipeListener.Direction p_direction) {
        float l_rotation = 0f;
        int l_lastPosition;
        Pair<Float, Float> l_toReturn = null;
        switch (p_direction) {
            case up:
                l_lastPosition = getObstaclePosition(mHeadPosition - (ROW_WIDTH * mHeadPosition / 10), p_direction);
                l_toReturn = new Pair<>((float) Animation.RELATIVE_TO_SELF, mThumbsViews[l_lastPosition].getY() - mThumbsViews[mHeadPosition].getY());
                l_rotation = 270f;
                break;
            case down:
                l_lastPosition = getObstaclePosition((mThumbIds.length - ROW_WIDTH) + mHeadPosition % ROW_WIDTH, p_direction);
                l_toReturn = new Pair<>((float) Animation.RELATIVE_TO_SELF, mThumbsViews[l_lastPosition].getY() - mThumbsViews[mHeadPosition].getY());
                l_rotation = 90f;
                break;
            case left:
                l_lastPosition = getObstaclePosition(mHeadPosition - (mHeadPosition % ROW_WIDTH), p_direction);
                l_toReturn = new Pair<>(mThumbsViews[l_lastPosition].getX() - mThumbsViews[mHeadPosition].getX(), (float) Animation.RELATIVE_TO_SELF);
                l_rotation = 180f;
                break;
            case right:
                l_lastPosition = getObstaclePosition(mHeadPosition + ((ROW_WIDTH - 1) - (mHeadPosition % ROW_WIDTH)), p_direction);
                l_toReturn = new Pair<>(mThumbsViews[l_lastPosition].getX() - mThumbsViews[mHeadPosition].getX(), (float) Animation.RELATIVE_TO_SELF);
                l_rotation = 0f;
                break;
        }

        mThumbsViews[mHeadPosition].setRotation(l_rotation);
        return l_toReturn;
    }

    private int getObstaclePosition(int p_lastPosition, final OnSwipeListener.Direction p_direction) {
        int l_lastPosition = mHeadPosition;
        int l_coef = 0;

        try {
            switch (p_direction) {
                case up:
                    l_coef = -ROW_WIDTH;
                    break;
                case down:
                    l_coef = ROW_WIDTH;
                    break;
                case left:
                    l_coef = -1;
                    break;
                case right:
                    l_coef = 1;
                    break;
            }

            for (int position = mHeadPosition + l_coef; position != p_lastPosition + l_coef && position >= 0; position += l_coef) {
                if (Arrays.asList(m_gems).contains(mThumbIds[position])) {
                    m_isWin = true;
                    l_lastPosition = position;
                }

                if (mThumbIds[position] != R.drawable.empty)
                    break;
                l_lastPosition = position;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return l_lastPosition;
    }

    private void setHatHeadPosition(final OnSwipeListener.Direction p_direction) {
        float l_rotation = 0f;
        int l_basePosition = mHeadPosition;
        mThumbsViews[mHeadPosition].setImageResource(R.drawable.empty);
        mThumbIds[mHeadPosition] = R.drawable.empty;

        switch (p_direction) {
            case up:
                mHeadPosition = getObstaclePosition(mHeadPosition - (ROW_WIDTH * mHeadPosition / 10), p_direction);
                l_rotation = 270f;
                for (int position = l_basePosition; position > mHeadPosition; position -= ROW_WIDTH) {
                    mThumbsViews[position].setImageResource(R.drawable.stone);
                    mThumbIds[position] = R.drawable.stone;
                    mThumbsViews[position].setRotation(l_rotation);
                }
                break;
            case down:
                mHeadPosition = getObstaclePosition((mThumbIds.length - ROW_WIDTH) + mHeadPosition % ROW_WIDTH, p_direction);
                l_rotation = 90f;
                for (int position = l_basePosition; position < mHeadPosition; position += ROW_WIDTH) {
                    mThumbsViews[position].setImageResource(R.drawable.stone);
                    mThumbIds[position] = R.drawable.stone;
                    mThumbsViews[position].setRotation(l_rotation);
                }
                break;
            case left:
                mHeadPosition = getObstaclePosition(mHeadPosition - (mHeadPosition % ROW_WIDTH), p_direction);
                l_rotation = 180f;
                for (int position = l_basePosition; position > mHeadPosition; position--) {
                    mThumbsViews[position].setImageResource(R.drawable.stone);
                    mThumbIds[position] = R.drawable.stone;
                    mThumbsViews[position].setRotation(l_rotation);
                }
                break;
            case right:
                mHeadPosition = getObstaclePosition(mHeadPosition + ((ROW_WIDTH - 1) - (mHeadPosition % ROW_WIDTH)), p_direction);
                l_rotation = 0f;
                for (int position = l_basePosition; position < mHeadPosition; position++) {
                    mThumbsViews[position].setImageResource(R.drawable.stone);
                    mThumbIds[position] = R.drawable.stone;
                    mThumbsViews[position].setRotation(l_rotation);
                }
                break;
        }

        mThumbsViews[mHeadPosition].setImageResource(R.drawable.hat);
        mThumbsViews[mHeadPosition].setRotation(l_rotation);
        mThumbIds[mHeadPosition] = R.drawable.hat;
    }

    public void makeHatMove(final OnSwipeListener.Direction p_direction) {
        ImageView l_hatHead = getHatHeadView();

        if (l_hatHead == null)
            return;

        try {
            Pair<Float, Float> l_coord = getLastPositionInDirection(p_direction);
            TranslateAnimation l_animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, l_coord.first, Animation.RELATIVE_TO_SELF, l_coord.second);
            l_animation.setDuration(Math.abs((long) (l_coord.first + l_coord.second)) / 10); // TODO: remove /10 before release the app
            l_animation.setInterpolator(new LinearInterpolator());
            l_animation.setFillAfter(false);
            l_animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    setHatHeadPosition(p_direction);
                    if (m_isWin)
                        makeLevelEnd();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

            l_hatHead.startAnimation(l_animation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeLevelEnd() {
        Intent intent = new Intent(mContext, PlayActivity.class);
        intent.putExtra("level", m_levelNumber + 1);
        mContext.startActivity(intent);
    }
}