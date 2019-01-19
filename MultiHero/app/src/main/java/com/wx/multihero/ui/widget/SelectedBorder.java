package com.wx.multihero.ui.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class SelectedBorder extends Widget {
    protected Widget mHost;
    public SelectedBorder(int id, RectF boundingRect) {
        super(id, boundingRect);
    }

    public void setHost(Widget host) {
        mHost = host;
        if(host != null) {
            mBoundingRect.set(host.getBoundingRect());
            mBoundingRect.inset(-4, -3);
        }
    }

    public void positionChanged(float dx, float dy) {

    }

    public void render(Canvas canvas, Paint paint) {
        int oldColor = paint.getColor();
        Paint.Style oldStyle = paint.getStyle();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(mBoundingRect, paint);
        paint.setColor(oldColor);
        paint.setStyle(oldStyle);
    }
}
