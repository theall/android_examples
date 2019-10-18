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

package com.wx.multihero.variability.sprite;

import android.graphics.Bitmap;

import com.wx.multihero.base.Stepable;

import java.util.ArrayList;

public class SerializedFrames implements Stepable {
    private ArrayList<Frame> mFrameList = new ArrayList<Frame>();
    private Frame mCurrentFrame;
    private int mCurrentIndex;
    private int mTotalFrameDuration;
    private int mRecycleTimes;

    public SerializedFrames() {
        mTotalFrameDuration = 0;
        reset();
    }

    public void reset() {
        mCurrentIndex = -1;
        mCurrentFrame = null;
        mRecycleTimes = 0;
    }

    public void add(Bitmap bitmap) {
        add(new Frame(bitmap));
    }

    public void add(int duration, Bitmap bitmap) {
        add(new Frame(duration, bitmap));
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
            mRecycleTimes++;
            mCurrentIndex = -1;
            mCurrentFrame = mFrameList.get(0);
        } else {
            int frameCounter = mCurrentIndex;
            for (Frame frame : mFrameList) {
                int duration = frame.getDuration();
                if (frameCounter < duration) {
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
}
