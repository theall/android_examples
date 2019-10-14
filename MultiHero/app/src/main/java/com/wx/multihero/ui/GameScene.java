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
package com.wx.multihero.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.wx.multihero.base.SceneType;
import com.wx.multihero.variability.Game;

public class GameScene extends BaseScene {
    private ControllerScene mControllerScene;
    private boolean mShowController;
    public GameScene(SceneType sceneType, Notify notify) {
        super(sceneType, notify);

        mShowController = false;
        mControllerScene = new ControllerScene();
    }

    public void render(Canvas canvas, Paint paint) {
        Game.getInstance().render(canvas, paint);

        if(mShowController)
            mControllerScene.render(canvas, paint);
    }

    public boolean processTouchEvent(MotionEvent event) {
        mControllerScene.processTouchEvent(event);
        return false;
    }

    public void shiftIn() {
        Game.getInstance().setState(Game.State.RUNNING);
    }

    public void shiftOut() {
        Game.getInstance().setState(Game.State.OVER);
    }

    public void loadAssets() {
        mControllerScene.loadAssets();
    }

    public boolean ismShowController() {
        return mShowController;
    }

    public void setmShowController(boolean showController) {
        mShowController = showController;
    }
}
