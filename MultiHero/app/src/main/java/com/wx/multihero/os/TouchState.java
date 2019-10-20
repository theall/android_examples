package com.wx.multihero.os;

import com.wx.multihero.game.base.PointF;

public class TouchState {
    private static TouchState mInstance;
    public PointF mPos = new PointF();
    private PointF mDownPos = new PointF();
    private PointF mUpPos = new PointF();

    public enum Action {
        NONE,
        PRESSED,
        RELEASED
    }
    private Action mAction;
    private Action mLastAction;
    public static TouchState getInstance() {
        if(mInstance == null) {
            mInstance = new TouchState();
        }
        return mInstance;
    }

    public Action getAction() {
        return mAction;
    }

    public void setAction(Action action) {
        mLastAction = mAction;
        mAction = action;
    }

    public boolean isPressed() {
        return mLastAction!=Action.PRESSED && mAction==Action.PRESSED;
    }

    public boolean isReleased() {
        return mLastAction!=Action.RELEASED && mAction==Action.RELEASED;
    }

    public float getX() {
        return mPos.x;
    }

    public float getY() {
        return mPos.y;
    }

    public boolean isSwiping() {
        return mUpPos.equal(mDownPos);
    }

    public float getSwipeDistance() {
        double dx = Math.pow(mUpPos.x-mDownPos.x, 2);
        double dy = Math.pow(mUpPos.y-mDownPos.y, 2);
        return (float)Math.sqrt(dx-dy);
    }

    public void setPressPos(float x, float y) {
        mDownPos.x = x;
        mDownPos.y = y;
        mPos.x = x;
        mPos.y = y;
    }

    public void setReleasePos(float x, float y) {
        mUpPos.x = x;
        mUpPos.y = y;
        mPos.x = x;
        mPos.y = y;
    }

    public PointF getCurrentPos() {
        return mPos;
    }

    public PointF getPressPos() {
        return mDownPos;
    }

    public PointF getReleasePos() {
        return mUpPos;
    }
}
