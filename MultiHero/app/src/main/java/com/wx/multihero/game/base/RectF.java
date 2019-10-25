package com.wx.multihero.game.base;

public class RectF {
    public float left;
    public float top;
    public float bottom;
    public float right;

    public RectF()
    {
        left = 0;
        top = 0;
        bottom = 0;
        right = 0;
    }

    public void offset(float dx, float dy) {
        left += dx;
        right += dx;
        bottom += dy;
        top += dy;
    }

    public void copyFrom(RectF rect) {
        left = rect.left;
        right = rect.right;
        top = rect.top;
        bottom = rect.bottom;
    }
}
