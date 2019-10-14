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
package com.wx.multihero.base;

import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {
    private static SoundPool mSoundPool = null;
    private static int mBackgroundStreamId = -1;

    public SoundPlayer() {

    }

    public static SoundPool initialize() {
        if(mSoundPool == null) {
            mSoundPool = new SoundPool(400, AudioManager.STREAM_MUSIC, 100);
        }
        return mSoundPool;
    }

    public static void pauseAudio() {
        if(mBackgroundStreamId != -1) {
            mSoundPool.pause(mBackgroundStreamId);
        }
    }

    public static void resumeAudio() {
        if(mBackgroundStreamId != -1) {
            mSoundPool.resume(mBackgroundStreamId);
        }
    }

    public static void stopAudio() {
        if(mBackgroundStreamId != -1) {
            mSoundPool.stop(mBackgroundStreamId);
        }
    }

    public static void playAudio(int id) {
        if (id != -1) {
            if(mBackgroundStreamId  != -1) {
                mSoundPool.pause(mBackgroundStreamId);
            }
            mBackgroundStreamId = mSoundPool.play(id, 1.0f, 1.0f, 100, -1, 1f);
        }
    }
    
    public static void playSound(int id) {
        mSoundPool.play(id, 1.0f, 1.0f, 100, 0, 1f);
    }    
}
