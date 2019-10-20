package com.wx.multihero.game.variability.controller;

import android.view.KeyEvent;

import com.wx.multihero.game.base.Button;
import com.wx.multihero.os.KeyboardState;

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
        mButtonKeyMap.put(Button.ATTACK, KeyEvent.KEYCODE_J);
        mButtonKeyMap.put(Button.JUMP, KeyEvent.KEYCODE_K);
        mButtonKeyMap.put(Button.BLOCKING, KeyEvent.KEYCODE_SHIFT_LEFT);
        mButtonKeyMap.put(Button.THROW, KeyEvent.KEYCODE_U);
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
