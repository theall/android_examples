package com.wx.multihero.ui.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class ProgressBar extends Widget implements Renderable {
    private float mProgress;
    public ProgressBar(int id, RectF boundingRect) {
        super(id, boundingRect);
        mProgress = 0;
    }

    public void setProgress(float progress) {
        mProgress = progress;
    }

    public void render(Canvas canvas, Paint paint) {
        Paint.Style oldStyle = paint.getStyle();
        int oldColor = paint.getColor();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        RectF drawingRect = new RectF(mBoundingRect);
        drawingRect.right = drawingRect.left + drawingRect.width() * mProgress;
        canvas.drawRect(drawingRect, paint);
        paint.setStyle(oldStyle);
        paint.setColor(oldColor);
    }
}
