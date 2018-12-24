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
        mBackgroundStreamId = mSoundPool.play(id, 1.0f, 1.0f, 100, -1, 1f);
    }
    
    public static void playSound(int id) {
        mSoundPool.play(id, 1.0f, 1.0f, 100, 0, 1f);
    }    
}
