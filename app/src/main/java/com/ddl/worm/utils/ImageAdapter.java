package com.ddl.worm.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.ddl.worm.R;

public class ImageAdapter extends BaseAdapter {
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

    public ImageView getWormHeadView() {
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

    public boolean makeWormMove(OnSwipeListener.Direction p_direction, GridView p_gridView) {
        try {
            ImageView l_wormHead = getWormHeadView();
            TranslateAnimation l_animation = new TranslateAnimation(l_wormHead.getTranslationX(), l_wormHead.getTranslationX() + 700, l_wormHead.getTranslationY(), l_wormHead.getTranslationY());
            l_animation.setDuration(1000);
            l_animation.setInterpolator(new AccelerateInterpolator());
            l_wormHead.startAnimation(l_animation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
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