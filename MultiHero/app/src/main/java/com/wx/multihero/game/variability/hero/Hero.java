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

package com.wx.multihero.game.variability.hero;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wx.multihero.game.base.Utils;
import com.wx.multihero.game.entity.Character;
import com.wx.multihero.game.variability.chunk.ChunkManager;
import com.wx.multihero.game.variability.object.Plat;
import com.wx.multihero.game.variability.sprite.AnimationSprite;
import com.wx.multihero.game.variability.sprite.FaceDir;
import com.wx.multihero.game.variability.sprite.SerializedFrames;

import java.util.HashMap;

public abstract class Hero extends AnimationSprite {
    public int hp;
    public int sp;
    public int mLifes;
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
    public boolean air;
    public boolean ground;
    private boolean mIsShield;
    private boolean mCanFly;
    private boolean mIsBlocking;
    private boolean mIsActioning;
    private boolean mIsGrabbed;
    private Plat mPlat;
    private Action mCurrentAction;
    private Hero mTarget;
    private RectF mFootRect = new RectF();

    public interface Instruction {
        Action.ID next();
    }

    public HashMap<Action.ID, Action> mActionMap = new HashMap<Action.ID, Action>();

    public Hero(Character character) {
        mFrameCounter = 0;
        mCurrentAction = null;
        setAnchor(Anchor.CENTER_BOTTOM);

        for(Action.ID actionId : Action.ID.values()) {
            mActionMap.put(actionId, new Action(actionId));
        }

        load(character);
        setCurrentAction(Action.ID.READY);
    }

    public abstract void load(Character character);

    public abstract void go();

    public void setCurrentAction(Action.ID blow) {
        Action action = mActionMap.get(blow);
        setCurrentAction(action);
    }

    public Action getAction(Action.ID actionID) {
        return mActionMap.get(actionID);
    }

    public void clearActions() {
        for(Action action : mActionMap.values()) {
            action.clear();
        }
    }

    public void setCurrentAction(Action action) {
        if(mCurrentAction == action)
            return;

        if(mCurrentAction != null) {
            mCurrentAction.reset();
        }
        mCurrentAction = action;
        if(action != null) {
            setSerializedFrames(action);
        }
    }

    public boolean isCurrentActionEnd() {
        return mCurrentAction.isEnd();
    }

    @Override
    public void reset() {
        //super.reset();

        hp = 100;
        sp = 0;
        mLifes = 3;
        mHeight = 45;
        mUpHeight = mHeight;
        mDuckHeight = 25;
        mBlockLife = 100;
        mBlockMaxLife = 100;
        accx = accy = 0.2f;
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

    public void setActionAttackDistance(Action.ID actionID, int distance) {
        getAction(actionID).setDistance(distance);
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

    @Override
    public boolean setFaceDir(FaceDir faceDir) {
        boolean dirChanged = super.setFaceDir(faceDir);
        if(dirChanged) {
            mCurrentAction.reset();
            //sx = 0;
        }
        return dirChanged;
    }

    public RectF getFootRect() {
        mFootRect.left = x - 8;
        mFootRect.right = x + 8;
        mFootRect.top = y - 0.5f;
        mFootRect.bottom = y + 0.5f;
        return  mFootRect;
    }

    public RectF getLastFootRect() {
        mFootRect.left = _x - 8;
        mFootRect.right = _x + 8;
        mFootRect.top = _y - 0.5f;
        mFootRect.bottom = _y + 0.5f;
        return  mFootRect;
    }

    public boolean isOnGround() {
        return mPlat!=null;
    }

    public boolean isInAir() {
        return mPlat==null;
    }

    public void setPlat(Plat plat) {
        mPlat = plat;
    }
}
