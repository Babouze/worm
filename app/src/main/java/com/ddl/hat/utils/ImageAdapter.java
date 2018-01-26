package com.ddl.hat.utils;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.ddl.worm.R;

public class ImageAdapter extends BaseAdapter {
    private static int ROW_WIDTH = 10;
    private Context mContext;
    private ImageView[] mThumbsViews;
    private static int mHeadPosition;

    public ImageAdapter(Context c) {
        mContext = c;
        mThumbsViews = new ImageView[mThumbIds.length];
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

    private ImageView getWormHeadView() {
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
                l_lastPosition = mHeadPosition - (ROW_WIDTH * mHeadPosition / 10);
                l_toReturn = new Pair<>((float) Animation.RELATIVE_TO_SELF, mThumbsViews[l_lastPosition].getY() - mThumbsViews[mHeadPosition].getY());
                l_rotation = 270f;
                break;
            case down:
                l_lastPosition = (mThumbIds.length - ROW_WIDTH) + mHeadPosition % ROW_WIDTH;
                l_toReturn = new Pair<>((float) Animation.RELATIVE_TO_SELF, mThumbsViews[l_lastPosition].getY() - mThumbsViews[mHeadPosition].getY());
                l_rotation = 90f;
                break;
            case left:
                l_lastPosition = mHeadPosition - (mHeadPosition % ROW_WIDTH);
                l_toReturn = new Pair<>(mThumbsViews[l_lastPosition].getX() - mThumbsViews[mHeadPosition].getX(), (float) Animation.RELATIVE_TO_SELF);
                l_rotation = 180f;
                break;
            case right:
                l_lastPosition = mHeadPosition + ((ROW_WIDTH - 1) - (mHeadPosition % ROW_WIDTH));
                l_toReturn = new Pair<>(mThumbsViews[l_lastPosition].getX() - mThumbsViews[mHeadPosition].getX(), (float) Animation.RELATIVE_TO_SELF);
                l_rotation = 0f;
                break;
        }

        mThumbsViews[mHeadPosition].setRotation(l_rotation);
        return l_toReturn;
    }

    private void setWormHeadPosition(final OnSwipeListener.Direction p_direction) {
        float l_rotation = 0f;
        int l_basePosition = mHeadPosition;
        mThumbsViews[mHeadPosition].setImageResource(R.drawable.empty);
        mThumbIds[mHeadPosition] = R.drawable.empty;

        switch (p_direction) {
            case up:
                mHeadPosition = mHeadPosition - (ROW_WIDTH * (mHeadPosition / ROW_WIDTH));
                l_rotation = 270f;
                for (int position = l_basePosition; position > mHeadPosition; position -= ROW_WIDTH) {
                    mThumbsViews[position].setImageResource(R.drawable.stone);
                    mThumbIds[position] = R.drawable.stone;
                    mThumbsViews[position].setRotation(l_rotation);
                }
                break;
            case down:
                mHeadPosition = (mThumbIds.length - ROW_WIDTH) + mHeadPosition % ROW_WIDTH;
                l_rotation = 90f;
                for (int position = l_basePosition; position < mHeadPosition; position += ROW_WIDTH) {
                    mThumbsViews[position].setImageResource(R.drawable.stone);
                    mThumbIds[position] = R.drawable.stone;
                    mThumbsViews[position].setRotation(l_rotation);
                }
                break;
            case left:
                mHeadPosition = mHeadPosition - (mHeadPosition % ROW_WIDTH);
                l_rotation = 180f;
                for (int position = l_basePosition; position > mHeadPosition; position--) {
                    mThumbsViews[position].setImageResource(R.drawable.stone);
                    mThumbIds[position] = R.drawable.stone;
                    mThumbsViews[position].setRotation(l_rotation);
                }
                break;
            case right:
                mHeadPosition = mHeadPosition + ((ROW_WIDTH - 1) - (mHeadPosition % ROW_WIDTH));
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

    public void makeWormMove(final OnSwipeListener.Direction p_direction) {
        ImageView l_wormHead = getWormHeadView();

        if (l_wormHead == null)
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
                    setWormHeadPosition(p_direction);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

            l_wormHead.startAnimation(l_animation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty,
            R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty,
            R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty,
            R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty,
            R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty,
            R.drawable.hat, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty,
            R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty,
            R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty,
            R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty,
            R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.gem_squared, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty,
            R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty,
            R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty,
            R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty,
            R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty
    };
}