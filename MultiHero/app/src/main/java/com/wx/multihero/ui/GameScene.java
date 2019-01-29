package com.wx.multihero.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.wx.multihero.base.SceneType;
import com.wx.multihero.variability.Game;

public class GameScene extends BaseScene {
    public GameScene(SceneType sceneType, Notify notify) {
        super(sceneType, notify);
    }

    public void render(Canvas canvas, Paint paint) {
        Game.getInstance().render(canvas, paint);
    }

    public int processTouchEvent(MotionEvent event) {
        return 0;
    }

    public void shiftIn() {
        Game.getInstance().setState(Game.State.RUNNING);
    }

    public void shiftOut() {
        Game.getInstance().setState(Game.State.OVER);
    }

    public void loadAssets() {

    }
}
