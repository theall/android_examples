package com.wx.multihero.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.wx.multihero.base.SceneType;

public class OptionScene extends BaseScene {
    public OptionScene(SceneType sceneType, Notify notify) {
        super(sceneType, notify);
    }

    public void render(Canvas canvas, Paint paint) {

    }

    public boolean processTouchEvent(MotionEvent event) {
        return false;
    }

    public void shiftIn() {

    }

    public void shiftOut() {

    }

    public void loadAssets() {

    }
}
