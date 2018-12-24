package com.wx.multihero.ui;

import android.graphics.RectF;

import com.wx.multihero.ui.widget.Touchable;
import com.wx.multihero.ui.widget.Renderable;
import com.wx.multihero.base.SceneType;

public abstract class BaseScene implements Renderable, Touchable {
    protected SceneType mSceneType;
    public static RectF mScreenRect;
    protected Notify mNotify;

    public interface Notify {
        void requestExit(SceneType sceneType);
    }

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
}
