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

package com.wx.multihero.game.ui.scene;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.wx.multihero.game.base.SceneType;
import com.wx.multihero.os.TouchState;

public class ExitScene extends BaseScene {  //退出场景
    private boolean mIsOk = false;

    public ExitScene(SceneType sceneType, Notify notify) {
        super(sceneType, notify);
    }

    public void processTouchState(int x, int y) {
        
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

    public boolean processTouchState(TouchState touchState) {
        return false;
    }

    public void shiftIn() {

    }

    public void shiftOut() {

    }

    public void loadAssets() {

    }

    public void step() {

    }
}
