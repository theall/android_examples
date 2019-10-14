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

import android.graphics.RectF;

import com.wx.multihero.base.SoundPlayer;
import com.wx.multihero.ui.widget.Touchable;
import com.wx.multihero.base.Renderable;
import com.wx.multihero.base.SceneType;

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
