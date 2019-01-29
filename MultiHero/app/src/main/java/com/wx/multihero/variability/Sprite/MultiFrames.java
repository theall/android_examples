package com.wx.multihero.variability.Sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class MultiFrames extends Sprite {
    private ArrayList<Frame> mFrameList = new ArrayList<Frame>();
    private Frame mCurrentFrame;
    private int mCurrentIndex;
    private int mTotalFrameDuration;
    private int mRecycleTimes;

    public MultiFrames() {
        mCurrentIndex = -1;
        mTotalFrameDuration = 0;
        mCurrentFrame = null;
        mRecycleTimes = 0;
    }

    public void add(int n, Bitmap bitmap) {
        add(new Frame(n, bitmap));
    }

    public void add(Frame frame) {
        mFrameList.add(frame);
        mTotalFrameDuration += frame.getDuration();
    }
    
    public void render(Canvas canvas, Paint paint) {
        if(mCurrentFrame != null)
            mCurrentFrame.render(canvas, paint);
    }

    public void step() {
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
    }

    public Frame getCurrentFrame() {
        return mCurrentFrame;
    }

    public boolean isRecycled() {
        return mRecycleTimes>0;
    }
}
