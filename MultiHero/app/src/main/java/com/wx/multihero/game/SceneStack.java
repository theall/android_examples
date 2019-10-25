/**
 * Copyright (C) Bilge Theall, wazcd_1608@qq.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package com.wx.multihero.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;

import com.wx.multihero.game.base.Renderable;
import com.wx.multihero.game.base.SceneType;
import com.wx.multihero.game.base.Stepable;
import com.wx.multihero.game.ui.widget.Touchable;
import com.wx.multihero.game.ui.scene.BaseScene;
import com.wx.multihero.os.TouchState;

import java.util.Stack;

public class SceneStack implements Renderable, Touchable, Stepable {
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

    public boolean processTouchState(TouchState touchState) {
        if(!mSceneStack.isEmpty())
            mSceneStack.firstElement().processTouchState(touchState);
        return false;
    }

    public boolean processKeyEvent(int keyCode, KeyEvent event) {
        return false;
    }

    public void step() {
        if(!mSceneStack.isEmpty())
            mSceneStack.firstElement().step();
    }
}
