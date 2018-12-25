package com.wx.multihero.ui.widget;

import android.graphics.RectF;

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
        mBoundingRect.set(rect);
    }

    public RectF getDrawingRect() {
        return mDrawingRect;
    }

    public void setDrawingRect(RectF rect) {
        mDrawingRect.set(rect);
    }
}
