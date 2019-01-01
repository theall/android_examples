package com.wx.multihero.ui.widget;

import android.graphics.RectF;
import android.os.Bundle;
import android.view.MotionEvent;

import com.wx.multihero.base.SoundPlayer;

public abstract class TouchableWidget extends Widget implements Touchable {
    private boolean mFingerDown = false;
    private int mTouchedSoundEffect = -1;

    public interface Callback {
        void selected(int id, Bundle parameters);
    }
    private Callback mCallback = null;

    public TouchableWidget(int id, RectF boundingRect, Callback callback) {
        super(id, boundingRect);
        mCallback = callback;
    }

    public boolean isTouchingDown() {
        return mFingerDown;
    }

    public void setTouchedSoundEffect(int soundEffect) {
        mTouchedSoundEffect = soundEffect;
    }

    public void touched() {
        if(mCallback != null) {
            mCallback.selected(mId, getParameters());
        }
    }

    public Bundle getParameters() {
        return new Bundle();
    }

    public int processTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if(touchTest(x, y)) {
            int action = event.getAction();
            if(action == MotionEvent.ACTION_DOWN) {
                mFingerDown = true;
                if(mTouchedSoundEffect != -1) {
                    SoundPlayer.playSound(mTouchedSoundEffect);
                }
            } else if(action == MotionEvent.ACTION_UP) {
                mFingerDown = false;
                touched();
            }
        } else {
            mFingerDown = false;
        }
        return 0;
    }
}
