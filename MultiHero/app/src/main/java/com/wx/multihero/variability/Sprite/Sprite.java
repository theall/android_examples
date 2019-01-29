package com.wx.multihero.variability.Sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.wx.multihero.base.Renderable;
import com.wx.multihero.base.Stepable;

public class Sprite implements Renderable,Stepable {
    private Bitmap mBitmap;
    private float mX;
    private float mY;

    public Sprite() {
        mX = 0;
        mY = 0;
    }

    public Sprite(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public void move(float x, float y) {
        mX = x;
        mY = y;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public void render(Canvas canvas, Paint paint) {
        if(mBitmap != null) {
            canvas.drawBitmap(mBitmap, mX, mY, paint);
        }
    }

    public void step() {

    }
}
