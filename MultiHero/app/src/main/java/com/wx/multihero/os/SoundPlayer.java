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

package com.wx.multihero.os;

import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {
    private static SoundPool mSoundPool = null;
    private static int mBackgroundStreamId = -1;
    private static SoundPlayer mSoundPlayer;

    private SoundPlayer() {
        mSoundPool = new SoundPool(400, AudioManager.STREAM_MUSIC, 100);
    }

    public static SoundPlayer getInstance() {
        if(mSoundPlayer == null)
            mSoundPlayer = new SoundPlayer();
        return mSoundPlayer;
    }
    
    public SoundPool getSoundPool() {
        return mSoundPool;
    }

    public void pauseAudio() {
        if(mBackgroundStreamId != -1) {
            mSoundPool.pause(mBackgroundStreamId);
        }
    }

    public void resumeAudio() {
        if(mBackgroundStreamId != -1) {
            mSoundPool.resume(mBackgroundStreamId);
        }
    }

    public void stopAudio() {
        if(mBackgroundStreamId != -1) {
            mSoundPool.stop(mBackgroundStreamId);
        }
    }

    public void playAudio(int id) {
        if (id != -1) {
            if(mBackgroundStreamId  != -1) {
                mSoundPool.pause(mBackgroundStreamId);
            }
            mBackgroundStreamId = mSoundPool.play(id, 1.0f, 1.0f, 100, -1, 1f);
        }
    }
    
    public void playSound(int id) {
        mSoundPool.play(id, 1.0f, 1.0f, 100, 0, 1f);
    }    
}
