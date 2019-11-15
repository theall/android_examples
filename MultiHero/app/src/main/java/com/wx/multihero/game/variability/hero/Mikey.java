/**
 * Copyright (C) 850781307, 850781307@qq.com
 * <p>
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package com.wx.multihero.game.variability.hero;

import android.graphics.Bitmap;

import com.wx.multihero.game.base.AssetsLoader;
import com.wx.multihero.game.base.VectorF;
import com.wx.multihero.game.entity.Character;
import com.wx.multihero.game.variability.chunk.Chunk;
import com.wx.multihero.game.variability.sprite.Frame;

import java.util.ArrayList;

public class Mikey extends Hero {
    private int mSndHue = -1;
    private int mSndUpperCut = -1;
    private int mSndBall = -1;
    private int mSndSpin = -1;

    public Mikey(Character character) {
        super(character);
    }
    public void load(Character character) {
        clearActions();

        Action action = null;
        Frame frame = null;
        AssetsLoader assetsLoader = AssetsLoader.getInstance();

        // Ready 准备开始  C
        action = getAction(Action.ID.READY);
        for(Bitmap bitmap : character.getReadyList()) {
            action.add(bitmap);
        }
        action.setBreakable(true);

        // walk行走 C
        action = getAction(Action.ID.WALK);
        for(Bitmap bitmap : character.getWalkList()) {
            action.add(10, bitmap).setVector(1.5f, 0);
        }
        action.setBreakable(true);

        // Blocking格挡 C
        action = getAction(Action.ID.BLOCKING);
        for(Bitmap bitmap : character.getBlockList()) {
            action.add(60, bitmap);
        }

        // Duck蹲下 C
        action = getAction(Action.ID.DUCK);
        ArrayList<Bitmap> duckList = character.getDuckList();
        action.add(20, duckList.get(0));
        action.setBreakable(true);

        // Punch攻击
        action = getAction(Action.ID.PUNCH);
        ArrayList<Bitmap> bmpSet = character.getBitmapList(Character.SetID.BLOW);
        action.add(8, bmpSet.get(0));
        action.add(7, bmpSet.get(1));
        action.add(10, bmpSet.get(2));
        action.add(3, bmpSet.get(1));
        action.add(5, bmpSet.get(0));

        // Low kick蹲下攻击
        action = getAction(Action.ID.LOW_KICK);
        bmpSet = character.getBitmapList(Character.SetID.LOWKICK);
        action.add(8, bmpSet.get(0));
        action.add(10, bmpSet.get(1));
        action.add(15, bmpSet.get(2));

        // Jump跳
        action = getAction(Action.ID.JUMP);
        bmpSet = character.getBitmapList(Character.SetID.FLIP);
        action.add(7, bmpSet.get(0)).setVector(0, VectorF.Type.RELATIVE, -5.0f, VectorF.Type.ABSOLUTE);
        action.setBreakable(true);

        //二段跳
        action = getAction(Action.ID.JUMP2);//跳跃起来帧数越高，跳跃越高，Y轴负数越高，跳的越高
        action.add(11, bmpSet.get(1)).setVector(0, VectorF.Type.RELATIVE, -5.0f, VectorF.Type.ABSOLUTE);
        action.add(10, bmpSet.get(2)).setVectorType(VectorF.Type.RELATIVE);
        action.add(10, bmpSet.get(3)).setVectorType(VectorF.Type.RELATIVE);
        //bug是停止不了

        // Falling在空中的时候
        action = getAction(Action.ID.AIR);
        action.add(1, bmpSet.get(0)).setVectorType(VectorF.Type.RELATIVE);
        action.setBreakable(true);

        action = getAction(Action.ID.WALK_IN_AIR);
        bmpSet = character.getBitmapList(Character.SetID.AIR);
        action.add(5, bmpSet.get(0)).setVector(1.5f, VectorF.Type.ABSOLUTE, 0, VectorF.Type.RELATIVE);
        action.setBreakable(true);

        // High kick对着天上攻击
        action = getAction(Action.ID.HIGH_KICK);
        bmpSet = character.getBitmapList(Character.SetID.UPBLOW);
        action.add(10, bmpSet.get(0));
        action.add(10, bmpSet.get(1));
        action.add(10, bmpSet.get(2));
        action.add(5, bmpSet.get(3));

        // Special大招
        action = getAction(Action.ID.SPECIAL);
        bmpSet = character.getBitmapList(Character.SetID.SPECIAL);
        action.add(10, bmpSet.get(0)).setSound(assetsLoader.loadSound("mikeBreath"));//添加音乐
        action.add(10, bmpSet.get(1)).setChunk(Chunk.Type.RYU_BLUE_BALL_IMPACT, 30, -32);//因为没有图片，所以攻击波写的是RYU

        action.add(10, bmpSet.get(1));
        action.add(10, bmpSet.get(0));

        //  keen跳起来的大招
        action = getAction(Action.ID.UPPERCUT);
        bmpSet = character.getBitmapList(Character.SetID.UPSPECIAL);
        frame = action.add(5, bmpSet.get(0));
        frame.setSound(assetsLoader.loadSound("mikeUpperCut"));
        action.add(4, bmpSet.get(1));
        action.add(30, bmpSet.get(2)).setVector(3.0f, -5.5f);
        action.add(4, bmpSet.get(3));

        // down special蹲下大招
        action = getAction(Action.ID.DOWN_SPECIAL);
        bmpSet = character.getBitmapList(Character.SetID.DSPECIAL);
        action.add(15, bmpSet.get(0)).setVector(1.2f, -1f);
        action.add(5, bmpSet.get(1)).setVectorType(VectorF.Type.RELATIVE);
        //frame.setVector(4f, -1f);
        bmpSet = character.getBitmapList(Character.SetID.FLIP);
        action.add(5, bmpSet.get(0)).setVector(3.2f, -3.5f);
        action.add(5, bmpSet.get(1)).setVectorType(VectorF.Type.RELATIVE);
        action.add(5, bmpSet.get(2)).setVectorType(VectorF.Type.RELATIVE);
        action.add(10, bmpSet.get(3));
        action.setBreakable(false);
        //action.setIgnoreGravity(true);
        //bug是第1段跳无法落地


        // Falling也是在空中的时候
        action = getAction(Action.ID.FALLING);
        bmpSet = character.getBitmapList(Character.SetID.AIR);
        action.add(5, bmpSet.get(0)).setVectorType(VectorF.Type.RELATIVE);
        action.setBreakable(true);

        // Jump down蹲跳
        action = getAction(Action.ID.JUMP_DOWN);
        bmpSet = character.getBitmapList(Character.SetID.FLYKICK);
        action.add(5, bmpSet.get(0)).setVector(0.0f, 1.0f);
        action.setVirtualized(true);

        // Fly kick跳攻击
        action = getAction(Action.ID.FLYING_KICK);
        bmpSet = character.getBitmapList(Character.SetID.FLYKICK);
        action.add(10, bmpSet.get(0)).setVectorType(VectorF.Type.RELATIVE);
        action.add(30, bmpSet.get(1)).setVectorType(VectorF.Type.RELATIVE);
        action.add(10, bmpSet.get(0)).setVectorType(VectorF.Type.RELATIVE);


        // land C
        action = getAction(Action.ID.LAND);
        bmpSet = character.getBitmapList(Character.SetID.READY);
        action.add(bmpSet.get(0));

        // Grab enymy C
        action = getAction(Action.ID.GRABING);
        bmpSet = character.getBitmapList(Character.SetID.GRAB);
        action.add(20, bmpSet.get(0));

        // Grabed C
        action = getAction(Action.ID.GRABED);
        bmpSet = character.getBitmapList(Character.SetID.FALLING);
        action.add(1, bmpSet.get(1));

        // Throw 投出去什么东西
        action = getAction(Action.ID.THROW);
        bmpSet = character.getBitmapList(Character.SetID.GRAB);
        action.add(3, bmpSet.get(0));
        action.add(5, bmpSet.get(1));
        action.add(5, bmpSet.get(2));
        action.add(6, bmpSet.get(3));

        // Pick up item

        // Throw item

        // Dodge

    }
    public void go() {

    }
}
