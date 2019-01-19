package com.wx.multihero.ui.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wx.multihero.base.Renderable;
import com.wx.multihero.base.Utils;

public class ProgressBar extends Widget implements Renderable {
    private float mProgress;
    private RectF mTempRect;
    public ProgressBar(int id, RectF boundingRect) {
        super(id, boundingRect);
        mProgress = 0;
        mDrawingRect.inset(Utils.getRealWidth(2), Utils.getRealHeight(1));
        mTempRect = new RectF(mDrawingRect);
    }

    public void setProgress(float progress) {
        mProgress = progress;
    }

    public void render(Canvas canvas, Paint paint) {
        Paint.Style oldStyle = paint.getStyle();
        int oldColor = paint.getColor();
        paint.setColor(Color.GREEN);
        canvas.drawRect(mBoundingRect, paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        mTempRect.set(mDrawingRect);
        mTempRect.right = mDrawingRect.left + mDrawingRect.width() * mProgress;
        canvas.drawRect(mTempRect, paint);
        paint.setStyle(oldStyle);
        paint.setColor(oldColor);
    }

    public void positionChanged(float dx, float dy) {

    }
}
