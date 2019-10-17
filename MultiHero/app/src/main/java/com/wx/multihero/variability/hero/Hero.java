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

package com.wx.multihero.variability.hero;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.wx.multihero.base.Utils;
import com.wx.multihero.entity.Character;
import com.wx.multihero.variability.chunk.ChunkManager;
import com.wx.multihero.variability.sprite.AnimationSprite;
import com.wx.multihero.variability.sprite.FaceDir;
import com.wx.multihero.variability.sprite.SerializedFrames;

import java.util.HashMap;

public abstract class Hero extends AnimationSprite {
    public int hp;
    public int sp;
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
    private boolean mIsActioning;
    private boolean mIsGrabbed;
    private Action mCurrentAction;
    private Hero mTarget;
    public int[] mActionDist = new int[Action.COUNT];
    public HashMap<Integer, Action> mActionMap = new HashMap<Integer, Action>();

    public Hero(Character character) {
        mDir = FaceDir.NONE;
        mFrameCounter = 0;
        mCurrentAction = new Action(Action.NONE);
        //setAnchor(Anchor.CENTER_BOTTOM);
        for(int i = 0; i< Action.COUNT; i++) {
            mActionDist[i] = 0;
        }
        load(character);

        setCurrentAction(Action.READY);
    }

    public abstract void load(Character character);

    public abstract void go();

    public void setCurrentAction(int blow) {
        Action action = mActionMap.get(blow);
        setCurrentAction(action);
    }

    public void setCurrentAction(Action action) {
        if(mCurrentAction == action)
            return;

        if(mCurrentAction != null) {
            mCurrentAction.reset();;
        }
        mCurrentAction = action;
        if(action != null) {
            setSerializedFrames(action);
        }
    }

    public boolean isCurrentActionEnd() {
        return mCurrentAction.isEnd();
    }

    public void reset() {
        hp = 100;
        sp = 0;
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

        for(SerializedFrames serializedFrames : mActionMap.values()) {
            serializedFrames.reset();
        }
    }

    public void step() {
        super.step();

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

    @Override
    public void render(Canvas canvas, Paint paint) {
        super.render(canvas, paint);
    }

    public void add(Action action) {
        mActionMap.put(action.getId(), action);
    }

    public Action getCurrentAction() {
        return mCurrentAction;
    }
}
