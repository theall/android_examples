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
import com.wx.multihero.game.variability.chunk.Chunk;

public class AnimationSprite extends Sprite implements Cloneable {
    private SerializedFrames mSerializedFrames;
    private Frame mCurrentFrame;

    public AnimationSprite() {
        super();
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

    public Frame add(Bitmap bitmap) {
        Frame frame = new Frame(bitmap);
        add(frame);
        return frame;
    }

    public Frame add(int n, Bitmap bitmap) {
        Frame frame = new Frame(n, bitmap);
        add(frame);
        return frame;
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

                // attach chunk
                Chunk.Item chunk = mCurrentFrame.getChunk();
                if(chunk!=null && !chunk.isNull()) {
                    chunk.owner = this;
                    ChunkManager.getInstance().makeChunk(chunk);
                }

                // Sound
                SoundItem soundItem = mCurrentFrame.getSoundItem();
                if(soundItem.isValid()) {
                    SoundRender.getInstance().add(soundItem);
                }
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

    public void setIgnoreGravity() {
        for(Frame frame : mSerializedFrames.getFramesList()) {
            frame.ignoreGravity = true;
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        AnimationSprite animationSprite = (AnimationSprite)super.clone();
        SerializedFrames serializedFrames = animationSprite.getSerializedFrames();
        if(serializedFrames != null) {
            animationSprite.setSerializedFrames((SerializedFrames)serializedFrames.clone());
        }
        return animationSprite;
    }

    public void setTimes(int times) {
        mSerializedFrames.setTimes(times);
    }
}
