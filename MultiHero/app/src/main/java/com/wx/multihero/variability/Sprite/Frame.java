package com.wx.multihero.variability.Sprite;

import android.graphics.Bitmap;

public class Frame {
    public int duration;
    public Bitmap bitmap;
    
    public Frame() {
        duration = -1;
    }

    public Frame(int duration, Bitmap bitmap) {
        this.duration = duration;
        this.bitmap = bitmap;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
