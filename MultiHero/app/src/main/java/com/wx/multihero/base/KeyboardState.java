package com.wx.multihero.base;

import android.view.KeyEvent;

public class KeyboardState {
    private static KeyboardState mInstance;
    private boolean[] mState;
    private static final int KEY_COUNT = KeyEvent.KEYCODE_CALCULATOR + 1;
    private KeyboardState() {
        mState = new boolean[KEY_COUNT];
        for(int i=0;i<KEY_COUNT;i++) {
            mState[i] = false;
        }
    }

    public static KeyboardState getInstance() {
        if(mInstance == null) {
            mInstance = new KeyboardState();
        }
        return mInstance;
    }

    public boolean[] getState() {
        return mState;
    }

    public boolean getKeyState(int key) {
        if(key<0 || key>=KEY_COUNT)
            return false;
        return mState[key];
    }

    public void setKeyState(int key, boolean state) {
        if(key<0 || key>=KEY_COUNT)
            return;
        mState[key] = state;
    }
}
