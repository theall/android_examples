/**
 * Copyright (C) Bilge Theall, wazcd_1608@qq.com
 *mzmoe 844024041@qq.com
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

import android.graphics.Bitmap;

import com.wx.multihero.game.base.AssetsLoader;
import com.wx.multihero.game.base.VectorF;
import com.wx.multihero.game.entity.Character;
import com.wx.multihero.game.variability.chunk.Chunk;
import com.wx.multihero.game.variability.sprite.Frame;

import java.util.ArrayList;
public class Mario extends Hero {
    public Mario(Character character) {
        super(character);
    }
    public void load(Character character) {
        clearActions();

        Action action = null;
        Frame frame = null;
        AssetsLoader assetsLoader = AssetsLoader.getInstance();

        // Ready
        action = getAction(Action.ID.READY);
        for (Bitmap bitmap : character.getReadyList()) {
            action.add(bitmap);
        }
        action.setBreakable(true);

        // walk
        action = getAction(Action.ID.WALK);
        for(Bitmap bitmap : character.getWalkList()) {
            action.add(10, bitmap).setVector(1.5f, 0);
        }
        action.setBreakable(true);

        // Blocking
        action = getAction(Action.ID.BLOCKING);
        for(Bitmap bitmap : character.getBlockList()) {
            action.add(60, bitmap);
        }

        // Duck
        action = getAction(Action.ID.DUCK);
        ArrayList<Bitmap> duckList = character.getDuckList();
        action.add(20, duckList.get(0));
        action.setBreakable(true);

        // Punch
        action = getAction(Action.ID.PUNCH);
        ArrayList<Bitmap> bmpSet = character.getBitmapList(Character.SetID.BLOW);
        action.add(4, bmpSet.get(0));
        action.add(5, bmpSet.get(1));
        action.add(5, bmpSet.get(2));
        action.add(5, bmpSet.get(3));
        action.add(5, bmpSet.get(4));
        action.add(9, bmpSet.get(5));
        action.add(5, bmpSet.get(4));
        action.add(5, bmpSet.get(3));
        action.add(5, bmpSet.get(2));
        action.add(4, bmpSet.get(1));

        // Low kick
        action = getAction(Action.ID.LOW_KICK);
        bmpSet = character.getBitmapList(Character.SetID.LOWKICK);
        action.add(6, bmpSet.get(0));
        action.add(6, bmpSet.get(1));
        action.add(6, bmpSet.get(2));
        action.add(7, bmpSet.get(3));
        action.add(5, bmpSet.get(4));
        action.add(5, bmpSet.get(5));
        action.add(5, bmpSet.get(6));

        // Jump
        action = getAction(Action.ID.JUMP);
        bmpSet = character.getBitmapList(Character.SetID.FLIP);
        action.add(10, bmpSet.get(0)).setVector(0, VectorF.Type.RELATIVE, -6.0f, VectorF.Type.ABSOLUTE);
        action.setBreakable(true);
        action = getAction(Action.ID.JUMP2);
        action.add(10, bmpSet.get(1)).setVector(0, VectorF.Type.RELATIVE, -4.0f, VectorF.Type.ABSOLUTE);
        action.add(10, bmpSet.get(2)).setVectorType(VectorF.Type.RELATIVE);
        action.add(10, bmpSet.get(3)).setVectorType(VectorF.Type.RELATIVE);

        // Falling
        action = getAction(Action.ID.AIR);
        action.add(1, bmpSet.get(0)).setVectorType(VectorF.Type.RELATIVE);
        action.setBreakable(true);
        action = getAction(Action.ID.WALK_IN_AIR);
        bmpSet = character.getBitmapList(Character.SetID.AIR);
        action.add(5, bmpSet.get(0)).setVector(1.5f, VectorF.Type.ABSOLUTE, 0, VectorF.Type.RELATIVE);
        action.setBreakable(true);

        // High kick
        action = getAction(Action.ID.HIGH_KICK);
        bmpSet = character.getBitmapList(Character.SetID.UPBLOW);
        action.add(4, bmpSet.get(0));
        action.add(7, bmpSet.get(1));
        action.add(6, bmpSet.get(2));
        action.add(7, bmpSet.get(3));
        action.add(6, bmpSet.get(4));
        action.add(5, bmpSet.get(5));

        // Special
        action = getAction(Action.ID.SPECIAL);
        bmpSet = character.getBitmapList(Character.SetID.SPECIAL);
        action.add(3, bmpSet.get(0));
        action.add(4, bmpSet.get(1));
        action.add(5, bmpSet.get(2));
        action.add(30, bmpSet.get(3)).setChunk(Chunk.Type.RYU_BLUE_BALL_IMPACT, 30, -32);//替代

        // ryu keen
        action = getAction(Action.ID.UPPERCUT);
        bmpSet = character.getBitmapList(Character.SetID.UPSPECIAL);
        action.add(8, bmpSet.get(0));
        action.add(15, bmpSet.get(1)).setVector(5.0f, -7.5f);
        action.add(8, bmpSet.get(2));
        action.add(8, bmpSet.get(3));
        action.add(8, bmpSet.get(4));
        action.add(8, bmpSet.get(5));

        // down special
        action = getAction(Action.ID.DOWN_SPECIAL);
        bmpSet = character.getBitmapList(Character.SetID.DSPECIAL);
        action.add(3, bmpSet.get(0));
        action.add(3, bmpSet.get(1));
        action.add(3, bmpSet.get(2));
        action.add(3, bmpSet.get(3));
        action.add(138, bmpSet.get(4));

        // Falling
        action = getAction(Action.ID.FALLING);
        bmpSet = character.getBitmapList(Character.SetID.AIR);
        action.add(5, bmpSet.get(0)).setVectorType(VectorF.Type.RELATIVE);
        action.setBreakable(true);

        // Jump down
        action = getAction(Action.ID.JUMP_DOWN);
        bmpSet = character.getBitmapList(Character.SetID.FLYKICK);
        action.add(5, bmpSet.get(0)).setVector(0.0f, 1.0f);
        action.setVirtualized(true);

        // Fly kick
        action = getAction(Action.ID.FLYING_KICK);
        bmpSet = character.getBitmapList(Character.SetID.FLYKICK);
        action.add(9, bmpSet.get(0)).setVectorType(VectorF.Type.RELATIVE);
        action.add(18, bmpSet.get(1)).setVectorType(VectorF.Type.RELATIVE);
        action.add(9, bmpSet.get(0)).setVectorType(VectorF.Type.RELATIVE);

        // Grab enymy
        action = getAction(Action.ID.GRABING);
        bmpSet = character.getBitmapList(Character.SetID.GRAB);
        action.add(20, bmpSet.get(1));

        // land
        action = getAction(Action.ID.LAND);
        bmpSet = character.getBitmapList(Character.SetID.READY);
        action.add(bmpSet.get(0));

        // Grabed
        action = getAction(Action.ID.GRABED);
        bmpSet = character.getBitmapList(Character.SetID.FALLING);
        action.add(1, bmpSet.get(1));

        // Throw enymy
        action = getAction(Action.ID.THROW);
        bmpSet = character.getBitmapList(Character.SetID.GRAB);
        action.add(8, bmpSet.get(0));
        action.add(8, bmpSet.get(1));
        action.add(8, bmpSet.get(2));

        // Pick up item

        // Throw item

        // Dodge
        action = getAction(Action.ID.CLUB);
        bmpSet = character.getBitmapList(Character.SetID.BLOW);
        action.add(8, bmpSet.get(0));
        action.add(7, bmpSet.get(1));
        action.add(10, bmpSet.get(3));
        action.add(3, bmpSet.get(4));
        action.add(5, bmpSet.get(1));
        action.add(5, bmpSet.get(0));
    }


    public void go() {

    }
}
