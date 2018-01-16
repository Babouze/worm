package com.ddl.worm.utils;

import android.animation.AnimatorSet;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.ddl.worm.R;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private AnimatorSet mCurrentAnimator;

    public ImageAdapter(Context c) {
        mContext = c;
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
        for (int i = 0; i < this.getCount(); i++) {
            if (mThumbIds[i] == R.drawable.worm_head) {
                return ((ImageView) this.getItem(i));
            }
        }
        return null;
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

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    public boolean makeWormMove(OnSwipeListener.Direction p_direction, GridView p_gridView) {
        try {
//            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.worm_move);
//            getWormHeadView().startAnimation(animation);
            GridLayoutAnimationController l_animation = new GridLayoutAnimationController(AnimationUtils.loadAnimation(mContext, R.anim.worm_move));
            p_gridView.startAnimation(l_animation.getAnimation());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.border_top, R.drawable.border_top, R.drawable.border_top, R.drawable.border_top, R.drawable.border_top, R.drawable.border_top, R.drawable.border_top, R.drawable.border_top, R.drawable.border_top, R.drawable.border_top,
            R.drawable.border_left, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.border_right,
            R.drawable.border_left, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.border_right,
            R.drawable.border_left, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.border_right,
            R.drawable.border_left, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.border_right,
            R.drawable.border_left, R.drawable.worm_head, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.border_right,
            R.drawable.border_left, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.border_right,
            R.drawable.border_left, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.border_right,
            R.drawable.border_left, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.border_right,
            R.drawable.border_left, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.border_right,
            R.drawable.border_left, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.border_right,
            R.drawable.border_left, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.border_right,
            R.drawable.border_left, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.empty, R.drawable.border_right,
            R.drawable.border_bottom, R.drawable.border_bottom, R.drawable.border_bottom, R.drawable.border_bottom, R.drawable.border_bottom, R.drawable.border_bottom, R.drawable.border_bottom, R.drawable.border_bottom, R.drawable.border_bottom, R.drawable.border_bottom
    };
}