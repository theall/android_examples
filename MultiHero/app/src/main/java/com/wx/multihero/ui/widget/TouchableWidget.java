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

package com.wx.multihero.ui.widget;

import android.graphics.RectF;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;

import com.wx.multihero.base.SoundPlayer;

public abstract class TouchableWidget extends Widget implements Touchable {
    private boolean mFingerDown = false;
    private boolean mFireContinous = false;
    private int mTouchedSoundEffect = -1;
    private long mLastFireTick = 0;

    public interface Callback {
        void selected(int id, Bundle parameters);
    }
    private Callback mCallback = null;

    public TouchableWidget(Widget parent) {
        super(parent);
    }

    public TouchableWidget(Callback callback, Widget parent) {
        super(parent);
        mCallback = callback;
    }

    public boolean isTouchingDown() {
        return mFingerDown;
    }

    public boolean isFireContinous() {
        return mFireContinous;
    }

    public void setFireContinous(boolean fireContinous) {
        mFireContinous = fireContinous;
    }

    public void setTouchedSoundEffect(int soundEffect) {
        mTouchedSoundEffect = soundEffect;
    }

    public void touched() {
        if(mCallback != null) {
            mCallback.selected(getTag(), getParameters());
        }
    }

    public Bundle getParameters() {
        return new Bundle();
    }

    public boolean processTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if(touchTest(x, y)) {
            int action = event.getAction();
            if(action == MotionEvent.ACTION_DOWN) {
                mFingerDown = true;
                mLastFireTick = SystemClock.elapsedRealtime();
                if(mTouchedSoundEffect != -1) {
                    SoundPlayer.playSound(mTouchedSoundEffect);
                }
            } else if(action == MotionEvent.ACTION_UP) {
                mFingerDown = false;
                touched();
            }
//            if(mFingerDown && mFireContinous) {
//                long currentTick = SystemClock.elapsedRealtime();
//                if(currentTick-mLastFireTick > 100) {
//                    mLastFireTick = SystemClock.elapsedRealtime();
//                    touched();
//                }
//            }
        } else {
            mFingerDown = false;
        }
        return false;
    }
}
