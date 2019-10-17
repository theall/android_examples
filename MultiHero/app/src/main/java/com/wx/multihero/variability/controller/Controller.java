package com.wx.multihero.variability.controller;

import com.wx.multihero.base.Button;

public abstract class Controller {
    private int[] mLastState;
    private int[] mState;
    private int mStickFrames;
    public static final int BUTTON_COUNT = Button.COUNT.ordinal();
    public static final int STICKY_FRAMES = 10;
    public Controller() {
        mStickFrames = STICKY_FRAMES;
        mState = new int[BUTTON_COUNT];
        mLastState = new int[BUTTON_COUNT];
        for(int i=0;i<BUTTON_COUNT;i++) {
            mState[i] = 0;
        }
    }

    public int getStickFrames() {
        return mStickFrames;
    }

    public void setStickyFrames(int stickyFrames) {
        mStickFrames = stickyFrames;
    }

    public abstract void fillState(int[] mState);

    public void prepare() {
        for(int i=0;i<BUTTON_COUNT;i++) {
            mLastState[i] = mState[i];
            mState[i] = 0;
        }
        fillState(mState);
    }

    public boolean isButtonDown(Button button) {
        return mState[button.ordinal()] > 0;
    }

    public boolean isButtonUp(Button button) {
        return mState[button.ordinal()] == 0;
    }

    public boolean isButtonPressed(Button button) {
        int buttonIndex = button.ordinal();
        return mLastState[buttonIndex]==0 && mState[buttonIndex]==1;
    }

    public boolean isButtonReleased(Button button) {
        int buttonIndex = button.ordinal();
        return mLastState[buttonIndex]>0 && mState[buttonIndex]==0;
    }

    public boolean isButtonSticky(Button button) {
        return mState[button.ordinal()]>mStickFrames;
    }
}
