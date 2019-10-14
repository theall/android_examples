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
import com.wx.multihero.ui.component.JoyStick;

public class ControllerScene extends BaseScene {
    public static final int KEY_SHOT    = 0;
    public static final int KEY_JUMP    = 1;
    public static final int KEY_SPECIAL = 2;
    public static final int KEY_LEFT    = 3;
    public static final int KEY_HIT     = 4;
    public static final int KEY_SUPER   = 5;
    public static final int KEY_BLOCK   = 6;
    public static final int KEY_DOWN    = 7;
    public static final int KEY_UP      = 8;
    public static final int KEY_GRAB    = 9;
    public static final int KEY_RIGHT   = 10;
    public static final int KEY_COUNT   = 11;

    public boolean[] mState = new boolean[KEY_COUNT];
    private JoyStick mJoyStick;
    private static final int ID_JOYSTICK = 1;

    public ControllerScene() {
        super(SceneType.INVALID, null);

        mJoyStick = new JoyStick(null);
        mJoyStick.setTag(ID_JOYSTICK);
    }

    public void shiftIn() {

    }

    public void shiftOut() {

    }

    public void loadAssets() {
        mJoyStick.loadAssets();
    }

    public void render(Canvas canvas, Paint paint) {
        mJoyStick.render(canvas, paint);
    }

    public boolean processTouchEvent(MotionEvent event) {
        mJoyStick.processTouchEvent(event);
        return false;
    }

    public boolean isKeyDown(int key) {
        return mState[key];
    }
}
