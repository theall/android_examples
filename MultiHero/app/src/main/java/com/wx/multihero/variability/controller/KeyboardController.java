package com.wx.multihero.variability.controller;

import android.view.KeyEvent;

import com.wx.multihero.base.Button;
import com.wx.multihero.base.KeyboardState;

import java.util.HashMap;
import java.util.Map;

public class KeyboardController extends Controller {
    private Map<Button, Integer> mButtonKeyMap;

    public KeyboardController() {
        mButtonKeyMap = new HashMap<Button, Integer>();
        mButtonKeyMap.put(Button.UP, KeyEvent.KEYCODE_W);
        mButtonKeyMap.put(Button.DOWN, KeyEvent.KEYCODE_S);
        mButtonKeyMap.put(Button.LEFT, KeyEvent.KEYCODE_A);
        mButtonKeyMap.put(Button.RIGHT, KeyEvent.KEYCODE_D);
    }

    public void fillState(int[] mState) {
        boolean[] state = KeyboardState.getInstance().getState();

        for(Button button : mButtonKeyMap.keySet()) {
            int keyCode = mButtonKeyMap.get(button);
            if(keyCode<0 || keyCode>=state.length) {
                continue;
            }
            boolean isDown = state[keyCode];
            mState[button.ordinal()] = isDown?mState[button.ordinal()]+1:0;
        }
    }

    public void set(Button button, int keyCode) {
        mButtonKeyMap.put(button, keyCode);
    }

    public int get(Button button) {
        return mButtonKeyMap.get(button);
    }
}
