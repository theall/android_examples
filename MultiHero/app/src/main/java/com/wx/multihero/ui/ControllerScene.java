package com.wx.multihero.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.wx.multihero.base.SceneType;
import com.wx.multihero.ui.component.JoyStick;

public class ControllerScene extends BaseScene {
    public static final int KEY_SHOT    = 0;
    public static final int KEY_JUMP    = 1;
    public static final int KEY_SPECIAL = 2;
    public static final int KEY_LEFT    = 3;
    public static final int KEY_HIT     = 4;
    public static final int KEY_SUPER   = 5;
    public static final int KEY_BLOCK   = 6;
    public static final int KEY_DOWN    = 7;
    public static final int KEY_UP      = 8;
    public static final int KEY_GRAB    = 9;
    public static final int KEY_RIGHT   = 10;
    public static final int KEY_COUNT   = 11;

    public boolean[] mState = new boolean[KEY_COUNT];
    private JoyStick mJoyStick;
    private static final int ID_JOYSTICK = 1;

    public ControllerScene() {
        super(SceneType.INVALID, null);

        mJoyStick = new JoyStick(ID_JOYSTICK, null, null);
    }

    public void shiftIn() {

    }

    public void shiftOut() {

    }

    public void loadAssets() {
        mJoyStick.loadAssets();
    }

    public void render(Canvas canvas, Paint paint) {
        mJoyStick.render(canvas, paint);
    }

    public boolean processTouchEvent(MotionEvent event) {
        mJoyStick.processTouchEvent(event);
        return false;
    }

    public boolean isKeyDown(int key) {
        return mState[key];
    }
}
