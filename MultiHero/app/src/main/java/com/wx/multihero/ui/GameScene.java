package com.wx.multihero.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.wx.multihero.base.SceneType;

public class GameScene extends BaseScene {
    public GameScene(SceneType sceneType, Notify notify) {
        super(sceneType, notify);
    }

    public void render(Canvas canvas, Paint paint) {

    }

    public int processTouchEvent(MotionEvent event) {
        return 0;
    }

    public void shiftIn() {

    }

    public void shiftOut() {

    }

    public void loadAssets() {

    }
}
