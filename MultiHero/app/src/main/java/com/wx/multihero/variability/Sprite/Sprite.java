package com.wx.multihero.variability.Sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.wx.multihero.base.Renderable;
import com.wx.multihero.base.Stepable;

public class Sprite implements Renderable, Stepable {
    public Bitmap bitmap;
    public float x;
    public float y;

    public Sprite() {
        x = 0;
        y = 0;
    }

    public Sprite(Bitmap _bitmap) {
        bitmap = _bitmap;
    }

    public void render(Canvas canvas, Paint paint) {
        if(bitmap != null) {
            canvas.drawBitmap(bitmap, x, y, paint);
        }
    }

    public void step() {

    }
}
