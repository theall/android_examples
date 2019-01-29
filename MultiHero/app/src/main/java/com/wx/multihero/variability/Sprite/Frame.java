package com.wx.multihero.variability.Sprite;

import android.graphics.Bitmap;

public class Frame extends Sprite {
    private int mDuration;

    public Frame() {
        super();
        mDuration = 0;
    }

    public Frame(int duration, Bitmap bitmap) {
        super(bitmap);
        mDuration = duration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public int getDuration() {
        return mDuration;
    }
}
