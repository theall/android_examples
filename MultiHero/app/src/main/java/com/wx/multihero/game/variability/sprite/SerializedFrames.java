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

package com.wx.multihero.game.variability.sprite;

import android.graphics.Bitmap;

import com.wx.multihero.game.base.Stepable;

import java.util.ArrayList;

public class SerializedFrames implements Stepable {
    private ArrayList<Frame> mFrameList = new ArrayList<Frame>();
    private Frame mCurrentFrame;
    private int mCurrentIndex;
    private int mTotalFrameDuration;
    private int mRecycleTimes;

    private int mTimes;
    private int mCurrentTimes;
    private boolean mIsKey;

    public static final int DEFAULT_TIMES = 1;
    public SerializedFrames() {
        mTotalFrameDuration = 0;
        mTimes = DEFAULT_TIMES;
        reset();
    }

    public void reset() {
        mCurrentIndex = -1;
        mCurrentFrame = null;
        mRecycleTimes = 0;
        mCurrentTimes = 0;
    }

    public Frame add(Bitmap bitmap) {
        Frame frame = new Frame(bitmap);
        add(frame);
        return frame;
    }

    public Frame add(int duration, Bitmap bitmap) {
        Frame frame = new Frame(duration, bitmap);
        add(frame);
        return frame;
    }

    public void add(Frame frame) {
        mFrameList.add(frame);
        mTotalFrameDuration += frame.getDuration();
    }

    public void step() {
        if(mFrameList.isEmpty()) {
            mCurrentFrame = null;
            return;
        }
        mCurrentIndex++;
        if(mCurrentIndex >= mTotalFrameDuration) {
            mCurrentTimes++;
            if(mCurrentTimes >= mTimes) {
                mRecycleTimes++;
                mCurrentIndex = -1;
                mCurrentFrame = null;
            } else {
                mCurrentIndex = 0;
                mCurrentFrame = mFrameList.get(0);
            }

        } else {
            int frameCounter = mCurrentIndex;
            for (Frame frame : mFrameList) {
                int duration = frame.getDuration();
                if (frameCounter < duration) {
                    if(frameCounter == 0)
                        mIsKey = true;
                    mCurrentFrame = frame;
                    break;
                }
                frameCounter -= duration;
            }
        }
    }

    public void clear() {
        mFrameList.clear();
        mCurrentFrame = null;
        mCurrentIndex = -1;
    }

    public Frame getCurrentFrame() {
        return mCurrentFrame;
    }

    public boolean isRecycled() {
        return mRecycleTimes>0;
    }

    public boolean isEnd() {
        return mFrameList.isEmpty() || (mRecycleTimes>0 && mCurrentIndex<0);
    }

    public int getTimes() {
        return mTimes;
    }

    public void setTimes(int times) {
        mTimes = times;
    }

    public int getCurrentTimes() {
        return mCurrentTimes;
    }

    public void setCurrentTimes(int currentTimes) {
        mCurrentTimes = currentTimes;
    }

    public void setIgnoreGravity(boolean ignoreGravity) {
        for(Frame frame : mFrameList) {
            frame.ignoreGravity = ignoreGravity;
        }
    }

    public boolean isKeyFrame() {
        return mIsKey;
    }

    public boolean isCloneFrame() {
        return !mIsKey;
    }

    public boolean isFalseFrame() {
        return mCurrentTimes>0;
    }

    public void setVirtualized(boolean virtualized) {
        for(Frame frame : mFrameList) {
            frame.virtualized = virtualized;
        }
    }

    public ArrayList<Frame> getFramesList() {
        return mFrameList;
    }
}
