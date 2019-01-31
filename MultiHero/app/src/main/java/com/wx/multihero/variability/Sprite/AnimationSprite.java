package com.wx.multihero.variability.Sprite;

import android.graphics.Bitmap;

import com.wx.multihero.base.Stepable;

import java.util.ArrayList;

public class AnimationSprite extends Sprite implements Stepable {
    private ArrayList<Frame> mFrameList = new ArrayList<Frame>();
    private Frame mCurrentFrame;
    private int mCurrentIndex;
    private int mTotalFrameDuration;
    private int mRecycleTimes;

    public AnimationSprite() {
        mCurrentIndex = -1;
        mTotalFrameDuration = 0;
        mCurrentFrame = null;
        mRecycleTimes = 0;
    }

    public void add(Bitmap bitmap) {
        add(new Frame(bitmap));
    }

    public void add(int n, Bitmap bitmap) {
        add(new Frame(n, bitmap));
    }

    public void add(Frame frame) {
        mFrameList.add(frame);
        mTotalFrameDuration += frame.getDuration();
    }

    public void step() {
        int frameCount = mFrameList.size();
        if(frameCount > 1) {
            mCurrentIndex++;
            if(mCurrentIndex > mTotalFrameDuration) {
                mRecycleTimes++;
                mCurrentIndex = 0;
            }

            int frameCounter = mCurrentIndex;
            for(Frame frame : mFrameList) {
                int duration = frame.getDuration();
                if(frameCounter < duration) {
                    mCurrentFrame = frame;
                    break;
                }
                frameCounter -= duration;
            }

        } else if(frameCount==1) {
            mCurrentFrame = mFrameList.get(0);
        } else {
            mCurrentFrame = null;
        }
        if(mCurrentFrame != null) {
            setBitmap(mCurrentFrame.getBitmap());
        }
    }

    public Frame getCurrentFrame() {
        return mCurrentFrame;
    }

    public boolean isRecycled() {
        return mRecycleTimes>0;
    }
}
