package com.wx.multihero.variability.Sprite;

import android.graphics.Bitmap;

import com.wx.multihero.base.Stepable;

import java.util.ArrayList;

public class AnimationSprite extends Sprite implements Stepable {
    private SerializedFrames mSerializedFrames;

    public AnimationSprite() {
        mSerializedFrames = new SerializedFrames();
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
