package com.wx.multihero;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.wx.multihero.ui.widget.Renderable;
import com.wx.multihero.base.SceneType;
import com.wx.multihero.ui.widget.Touchable;
import com.wx.multihero.ui.BaseScene;
import java.util.Stack;

public class SceneStack implements Renderable, Touchable {
    private Stack<BaseScene> mSceneStack;

    public SceneStack() {
        mSceneStack = new Stack<BaseScene>();
    }

    public int clearPush(BaseScene scene) {
        mSceneStack.clear();
        return push(scene);
    }

    public int push(BaseScene scene) {
        if(scene != null) {
            scene.shiftIn();
            mSceneStack.push(scene);
        }

        return mSceneStack.size();
    }

    public BaseScene pop() {
        BaseScene scene = mSceneStack.firstElement();
        if(scene != null) {
            mSceneStack.pop();
            scene.shiftOut();
        }
        return scene;
    }

    public BaseScene top() {
        return mSceneStack.firstElement();
    }

    public SceneType getTopSceneType() {
        int sceneSize = mSceneStack.size();
        if(sceneSize < 1)
            return SceneType.INVALID;
        return mSceneStack.firstElement().getSceneType();
    }

    public SceneType getTopLessSceneType() {
        int sceneSize = mSceneStack.size();
        if(sceneSize < 2)
            return SceneType.INVALID;
        return mSceneStack.get(sceneSize-2).getSceneType();
    }

    public void render(Canvas canvas, Paint paint) {
        canvas.drawColor(Color.BLACK);
        for(BaseScene scene : mSceneStack) {
            scene.render(canvas, paint);
        }
    }

    public int processTouchEvent(MotionEvent event) {
        mSceneStack.firstElement().processTouchEvent(event);
        return 0;
    }
}
