package com.wx.multihero.ui;

import android.graphics.RectF;

import com.wx.multihero.base.SoundPlayer;
import com.wx.multihero.ui.widget.Touchable;
import com.wx.multihero.base.Renderable;
import com.wx.multihero.base.SceneType;
import com.wx.multihero.variability.Game;

public abstract class BaseScene implements Renderable, Touchable {
    protected SceneType mSceneType;
    public static RectF mScreenRect;
    protected Notify mNotify;
    protected int mBackgroundSound = -1;

    public interface Notify {
        void back(SceneType sceneType);
        void next(SceneType sceneType, int parameter);
    }

    public abstract void shiftIn();
    public abstract void shiftOut();

    public abstract void loadAssets();

    public static void setResolution(int screenWidth, int screenHeight) {
        mScreenRect = new RectF(0, 0, screenWidth, screenHeight);
    }

    public BaseScene(SceneType sceneType, Notify notify) {
        mSceneType = sceneType;
        mNotify = notify;
    }

    public SceneType getSceneType() {
        return mSceneType;
    }

    public void playBackgoundSound(boolean interrupt) {
        if(interrupt) {
            SoundPlayer.stopAudio();
        }

        SoundPlayer.playAudio(mBackgroundSound);
    }
}
