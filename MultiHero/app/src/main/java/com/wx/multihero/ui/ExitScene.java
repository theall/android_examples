package com.wx.multihero.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.wx.multihero.base.SceneType;

public class ExitScene extends BaseScene {
    private boolean mIsOk = false;

    public ExitScene(SceneType sceneType, Notify notify) {
        super(sceneType, notify);
    }

    public void processTouchEvent(int x, int y) {
        
    }

    public boolean isOK() {
        return mIsOk;
    }

    public void render(Canvas canvas, Paint paint) {
        if(mIsOk)
        {
            mIsOk = false;
        }
    }

    public boolean processTouchEvent(MotionEvent event) {
        return  0;
    }

    public void shiftIn() {

    }

    public void shiftOut() {

    }

    public void loadAssets() {

    }
}
