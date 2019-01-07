package com.wx.multihero.ui.widget;

import android.graphics.RectF;

import com.wx.multihero.base.Renderable;

public abstract class Widget implements Renderable {
    protected RectF mBoundingRect;
    protected RectF mDrawingRect;
    protected int mId;

    public Widget(int id, RectF boundingRect) {
        mBoundingRect = new RectF(boundingRect);
        mDrawingRect = new RectF(boundingRect);
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public boolean touchTest(float x, float y) {
        return mBoundingRect.contains(x, y);
    }

    public RectF getBoundingRect() {
        return mBoundingRect;
    }

    public void setBoundingRect(RectF rect) {
        float dx = rect.left - mBoundingRect.left;
        float dy = rect.top - mBoundingRect.top;
        mBoundingRect.set(rect);
        positionChanged(dx, dy);
    }

    public RectF getDrawingRect() {
        return mDrawingRect;
    }

    public void setDrawingRect(RectF rect) {
        mDrawingRect.set(rect);
    }

    public void moveTo(float x, float y) {
        if(mBoundingRect == null)
            return;

        float dx = x - mBoundingRect.left;
        float dy = y - mBoundingRect.top;
        mBoundingRect.offset(dx, dy);

        if(mDrawingRect != null) {
            mDrawingRect.offset(dx, dy);
        }
        positionChanged(dx, dy);
    }

    public void offset(float dx, float dy) {
        if(mBoundingRect != null)
            mBoundingRect.offset(dx, dy);

        if(mDrawingRect != null)
            mDrawingRect.offset(dx, dy);

        positionChanged(dx, dy);
    }

    public abstract void positionChanged(float dx, float dy);
}
