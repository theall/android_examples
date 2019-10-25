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

import com.wx.multihero.game.variability.SoundRender;

public class AnimationSprite extends Sprite {
    private SerializedFrames mSerializedFrames;
    private Frame mCurrentFrame;

    public AnimationSprite() {
        mSerializedFrames = new SerializedFrames();
        mCurrentFrame = null;
    }

    public SerializedFrames getSerializedFrames() {
        return mSerializedFrames;
    }

    public void setSerializedFrames(SerializedFrames serializedFrames) {
        mSerializedFrames = serializedFrames;
    }

    public void reset() {
        mSerializedFrames.reset();
    }

    public void add(Bitmap bitmap) {
        add(new Frame(bitmap));
    }

    public void add(int n, Bitmap bitmap) {
        add(new Frame(n, bitmap));
    }

    public void add(Frame frame) {
        mSerializedFrames.add(frame);
    }

    @Override
    public void step() {
        mSerializedFrames.step();
        Frame currentFrame = mSerializedFrames.getCurrentFrame();
        if(currentFrame == mCurrentFrame) {
            super.step();
            return;
        }
        mCurrentFrame = currentFrame;
        if(mCurrentFrame != null) {
            if(!mSerializedFrames.isFalseFrame()) {
                setVector(mCurrentFrame.getVector());
                ignoreGravity = mCurrentFrame.ignoreGravity;
                virtualized = mCurrentFrame.virtualized;
                SoundItem soundItem = mCurrentFrame.getSoundItem();
                if(soundItem.isValid())
                    SoundRender.getInstance().add(soundItem);
            }
            setBitmap(mCurrentFrame.getBitmap());
        }
        super.step();
    }

    public Frame getCurrentFrame() {
        return mSerializedFrames.getCurrentFrame();
    }

    public boolean isRecycled() {
        return mSerializedFrames.isRecycled();
    }
}
