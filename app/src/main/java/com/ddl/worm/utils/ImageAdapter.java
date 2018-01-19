package com.ddl.worm.utils;

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
    private int mHeadPosition;

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
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
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
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(90, 90));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        } else {
            imageView = (ImageView) convertView;
        }

        // Set ressource
        imageView.setImageResource(mThumbIds[position]);

        // Register the view
        mThumbsViews[position] = imageView;

        // Save the head position
        if (mThumbIds[position] == R.drawable.worm_head)
            mHeadPosition = position;

        return imageView;
    }

    private Pair<Float, Float> getLastPositionInDirection(final OnSwipeListener.Direction p_direction) {
        int l_lastPosition;
        switch (p_direction) {
            case up:
                l_lastPosition = mHeadPosition - ROW_WIDTH;
                break;
            case down:
                l_lastPosition = mHeadPosition + ROW_WIDTH;
                break;
            case left:
                l_lastPosition = mHeadPosition - (mHeadPosition % ROW_WIDTH);
                break;
            case right:
                l_lastPosition = mHeadPosition + ((ROW_WIDTH - 1) - (mHeadPosition % ROW_WIDTH));
                break;

            default:
                l_lastPosition = mHeadPosition;
        }

        return new Pair<>(mThumbsViews[l_lastPosition].getX(), mThumbsViews[l_lastPosition].getY());
    }

    private void setWormHeadPosition(final OnSwipeListener.Direction p_direction) {
        mThumbsViews[mHeadPosition].setImageResource(R.drawable.empty);
        mThumbIds[mHeadPosition] = R.drawable.empty;

        switch (p_direction) {
            case up:
                mHeadPosition = mHeadPosition - ROW_WIDTH;
                break;
            case down:
                mHeadPosition = mHeadPosition + ROW_WIDTH;
                break;
            case left:
                mHeadPosition = mHeadPosition - (mHeadPosition % ROW_WIDTH);
                break;
            case right:
                mHeadPosition = mHeadPosition + ((ROW_WIDTH - 1) - (mHeadPosition % ROW_WIDTH));
                break;
        }

        mThumbsViews[mHeadPosition].setImageResource(R.drawable.worm_head);
        mThumbIds[mHeadPosition] = R.drawable.worm_head;
    }

    public void makeWormMove(final OnSwipeListener.Direction p_direction) {
        ImageView l_wormHead = getWormHeadView();

        if (l_wormHead == null)
            return;

        try {
            Pair<Float, Float> l_coord = getLastPositionInDirection(p_direction);
            TranslateAnimation l_animation = new TranslateAnimation(l_wormHead.getX(), l_coord.first, l_wormHead.getY(), l_coord.second);
            l_animation.setDuration(300);
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
            R.drawable.worm_head, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty,
            R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty,
            R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty,
            R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty,
            R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty,
            R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty,
            R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty,
            R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty,
            R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty
    };
}