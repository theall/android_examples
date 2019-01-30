package com.wx.multihero.variability.Sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.wx.multihero.base.Renderable;
import com.wx.multihero.base.Stepable;

public class Sprite implements Renderable,Stepable {
    private Bitmap bitmap;
    public float x;
    public float y;
    public enum Anchor {
        LEFT_TOP,
        CENTER,
        CENTER_BOTTOM
    }
    private Anchor mAnchor = Anchor.CENTER;
    public Sprite() {
        x = 0;
        y = 0;
    }

    public Sprite(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void move(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void render(Canvas canvas, Paint paint) {
        if(bitmap != null) {
            float x = this.x;
            float y = this.y;
            if(mAnchor == Anchor.CENTER) {
                x -= bitmap.getWidth()/2;
                y -= bitmap.getHeight()/2;
            } else if(mAnchor == Anchor.CENTER_BOTTOM) {
                x -= bitmap.getWidth()/2;
                y -= bitmap.getHeight();
            }
            canvas.drawBitmap(bitmap, x, y, paint);
        }
    }

    public void step() {

    }
}
