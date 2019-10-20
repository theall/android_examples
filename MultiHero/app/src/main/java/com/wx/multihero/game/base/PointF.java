package com.wx.multihero.game.base;

public class PointF {
    public float x;
    public float y;

    public PointF() {

    }

    public PointF(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean equal(PointF point) {
        return x==point.x && y==point.y;
    }
}
