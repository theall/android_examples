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

public class AnimationSprite extends Sprite implements Stepable {
    private SerializedFrames mSerializedFrames;

    public AnimationSprite() {
        mSerializedFrames = new SerializedFrames();
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

    public void step() {
        mSerializedFrames.step();
        Frame currentFrame = mSerializedFrames.getCurrentFrame();
        if(currentFrame != null) {
            setBitmap(currentFrame.getBitmap());
        }

    }

    public Frame getCurrentFrame() {
        return mSerializedFrames.getCurrentFrame();
    }

    public boolean isRecycled() {
        return mSerializedFrames.isRecycled();
    }
}
