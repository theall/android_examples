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

import com.wx.multihero.game.base.VectorF;

public class Frame {
    public int duration;
    public Bitmap bitmap;
    private VectorF mVector = new VectorF();
    public boolean ignoreGravity;
    public boolean virtualized = false;

    private SoundItem mSoundItem;
    public enum Type {
        KEY,
        CLONE
    }
    public Frame() {
        duration = -1;
    }

    public Frame(Bitmap bitmap) {
        this.duration = 1;
        this.bitmap = bitmap;
        ignoreGravity = false;
        mSoundItem = new SoundItem();
    }

    public Frame(int duration, Bitmap bitmap) {
        this.duration = duration;
        this.bitmap = bitmap;
        ignoreGravity =false;
        mSoundItem = new SoundItem();
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public VectorF getVector() {
        return mVector;
    }

    public void setVectorType(VectorF.Type type) {
        mVector.setType(type);
    }

    public void setVectorType(VectorF.Type tx, VectorF.Type ty) {
        mVector.setType(tx, ty);
    }

    public void setVectorValue(float x, float y) {
        mVector.setValue(x, y);
    }

    public void setVector(float dx, float dy) {
        mVector.x.value = dx;
        mVector.x.type = VectorF.Type.ABSOLUTE;
        mVector.y.value = dy;
        mVector.y.type = VectorF.Type.ABSOLUTE;
    }

    public void setVector(float dx, float dy, VectorF.Type type) {
        mVector.x.value = dx;
        mVector.y.value = dy;
        mVector.x.type = type;
        mVector.y.type = type;
    }

    public void setVector(float dx, VectorF.Type tx, float dy, VectorF.Type ty) {
        mVector.x.value = dx;
        mVector.y.value = dy;
        mVector.x.type = tx;
        mVector.y.type = ty;
    }

    public void setVector(VectorF.Item x, VectorF.Item y) {
        mVector.x.copyFrom(x);
        mVector.y.copyFrom(y);
    }

    public void setVector(VectorF vector) {
        mVector.copyFrom(vector);
    }

    public SoundItem getSoundItem() {
        return mSoundItem;
    }

    public void setSound(SoundItem soundItem) {
        mSoundItem.copy(soundItem);
    }

    public void setSound(int id) {
        mSoundItem.id = id;
        mSoundItem.delay = 0;
    }

    public void setSound(int id, int delay) {
        mSoundItem.id = id;
        mSoundItem.delay = delay;
    }
}
