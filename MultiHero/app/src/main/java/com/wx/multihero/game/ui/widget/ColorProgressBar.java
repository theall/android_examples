package com.wx.multihero.game.ui.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wx.multihero.game.base.Utils;

public class ColorProgressBar extends AbstractProgressBar {
    private int mBackgroundColor;
    private int mBorderColor;
    private int mForegroundColor;
    private RectF mTempRect;

    public ColorProgressBar(Widget parent) {
        super(Style.COLOR, parent);

        mTempRect = new RectF();
        mBackgroundColor = Color.GRAY;
        mForegroundColor = Color.BLUE;
        mBorderColor = Color.BLACK;
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
    }
    public int getBorderColor() {
        return mBorderColor;
    }

    public void setBorderColor(int borderColor) {
        mBorderColor = borderColor;
    }
    public int getForegroundColor() {
        return mForegroundColor;
    }

    public void setForegroundColor(int foregroundColor) {
        mForegroundColor = foregroundColor;
    }

    public void setColor(int back, int fore) {
        setColor(back, fore, back);
    }

    public void setColor(int back, int fore, int border) {
        mBackgroundColor = back;
        mForegroundColor = fore;
        mBorderColor = border;
    }

    @Override
    public void setBoundingRect(RectF rect) {
        super.setBoundingRect(rect);

        mDrawingRect = rect;
        float adjusted = Utils.getRealWidth(1);
        mDrawingRect.left += adjusted;
        mDrawingRect.right -= adjusted;
        mDrawingRect.top += adjusted;
        mDrawingRect.bottom -= adjusted;
    }

    @Override
    public void render(Canvas canvas, Paint paint) {
        Paint.Style oldStyle = paint.getStyle();
        int oldColor = paint.getColor();
        paint.setColor(mBackgroundColor);
        canvas.drawRect(mBoundingRect, paint);
        paint.setColor(mBorderColor);
        canvas.drawRect(mBoundingRect, paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(mForegroundColor);
        mTempRect.set(mDrawingRect);
        mTempRect.right = mDrawingRect.left + mDrawingRect.width() * mProgress;
        canvas.drawRect(mTempRect, paint);
        paint.setStyle(oldStyle);
        paint.setColor(oldColor);
    }
}
