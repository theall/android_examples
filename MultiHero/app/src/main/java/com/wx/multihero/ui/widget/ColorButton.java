package com.wx.multihero.ui.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class ColorButton extends TouchableWidget {
    private int mColor = 0xFF000000;

    public ColorButton(int id, RectF boundingRect, Callback callback) {
        super(id, boundingRect, callback);
    }

    public void positionChanged(float dx, float dy) {

    }

    public void setColor(int color) {
        mColor = color;
    }

    public void render(Canvas canvas, Paint paint) {
        if(canvas != null) {
            int oldColor = paint.getColor();
            Paint.Style oldStyle = paint.getStyle();
            paint.setColor(mColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(mBoundingRect, paint);
            paint.setColor(oldColor);
            paint.setStyle(oldStyle);
        }
    }
}
