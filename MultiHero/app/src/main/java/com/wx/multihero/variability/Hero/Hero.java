package com.wx.multihero.variability.Hero;

import com.wx.multihero.base.Utils;
import com.wx.multihero.entity.Character;
import com.wx.multihero.variability.Chunk.ChunkManager;
import com.wx.multihero.variability.Sprite.AnimationSprite;
import com.wx.multihero.variability.Sprite.FaceDir;
import com.wx.multihero.variability.Sprite.SerializedFrames;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Hero extends AnimationSprite {
    public Hero target;
    public int mHP;
    public int mSP;
    public int mLifes;
    public FaceDir mDir;
    public float mSpeed;
    public float mAcceleration;
    public float mBlockSpeed;
    public int mShieldTime;
    public int mHeight;
    public int mUpHeight;
    public int mDuckHeight;
    public int mBlockLife;
    public int mBlockMaxLife;
    private int mTempShieldFrames;
    private int mAntiPlatFrames;
    private int mTrailEffectFrames;
    private int mFrameCounter;
    private boolean mIsShield;
    private boolean mCanFly;
    private boolean mIsBlocking;
    private boolean mIsBlowing;
    private boolean mIsGrabbed;
    private int mCurrentBlow;
    public int[] mBlowDist = new int[Blow.COUNT];
    public SerializedFrames mCurrentAnimation = null;
    public HashMap<Integer,SerializedFrames> mBlowFramesMap = new HashMap<Integer, SerializedFrames>();

    public Hero() {
        mDir = FaceDir.NONE;
        mFrameCounter = 0;
        for(int i=0;i<Blow.COUNT;i++) {
            mBlowDist[i] = 0;
        }
    }

    public abstract void load(Character character);

    public abstract void go();

    private void reset() {
        mHP = 100;
        mSP = 0;
        mLifes = 3;
        mHeight = 45;
        mUpHeight = mHeight;
        mDuckHeight = 25;
        mBlockLife = 100;
        mBlockMaxLife = 100;
        mAcceleration = 0.2f;
        mBlockSpeed = 0.8f;
        mTempShieldFrames = 0;
        mAntiPlatFrames = 0;
        mTrailEffectFrames = 0;
        mIsShield = false;
        mCanFly = false;
    }

    public void step() {
        int renderIndex = mFrameCounter%3;
        if(mTempShieldFrames > 0) {
            mTempShieldFrames--;
            int seed = Utils.getRandValue(1, 3);
            if(seed == 2) {
                float x = Utils.getRandWidth(-10, 10);
                float y = Utils.getRandHeight(-40, 0);
                ChunkManager.getInstance().makeChunk(x, y);
            }
        }
        if(mAntiPlatFrames > 0) {
            mAntiPlatFrames--;
        }
        if(mTrailEffectFrames > 0) {
            mTrailEffectFrames--;
            if(renderIndex==1) {
                float x = Utils.getRandWidth(-8,8);
                float y = Utils.getRandHeight(-30,-10);
            }
        }

        go();

        mFrameCounter++;
    }

    public void setShield() {
        mTempShieldFrames = mShieldTime;
    }

    public void setAntiPlatform() {
        mAntiPlatFrames = 5;
    }

    public void setTrailEffect(int type) {

    }
}
