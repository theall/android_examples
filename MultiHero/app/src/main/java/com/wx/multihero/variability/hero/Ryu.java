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

import android.graphics.Bitmap;
import com.wx.multihero.entity.Character;
import java.util.ArrayList;

public class Ryu extends Hero {
    private int mSndHue = -1;
    private int mSndUpperCut = -1;
    private int mSndBall = -1;
    private int mSndSpin = -1;

    public Ryu(Character character) {
        super(character);

        setActionAttackDistance(Action.ID.PUNCH, 44);
        setActionAttackDistance(Action.ID.FLYING_KICK, 48);
        setActionAttackDistance(Action.ID.LOW_KICK, 44);
        setActionAttackDistance(Action.ID.UPPERCUT, 48);
        setActionAttackDistance(Action.ID.SPECIAL, 300);
        setActionAttackDistance(Action.ID.DOWN_SPECIAL, 70);
        setActionAttackDistance(Action.ID.HIGH_KICK, 44);
        setActionAttackDistance(Action.ID.CLUB, 150);
        setActionAttackDistance(Action.ID.SUPER_SPECIAL, 600);
    }

    public void load(Character character) {
        // Ready
        Action readyAction = getAction(Action.ID.READY);
        readyAction.clear();
        for(Bitmap bitmap : character.getReadyList()) {
            readyAction.add(bitmap);
        }

        // walk
        Action walkAction = getAction(Action.ID.WALK);
        walkAction.clear();
        for(Bitmap bitmap : character.getWalkList()) {
            walkAction.add(10, bitmap);
        }
        walkAction.setBreakable(true);

        // Blocking
        Action blockAction = getAction(Action.ID.BLOCKING);
        blockAction.clear();
        for(Bitmap bitmap : character.getBlockList()) {
            blockAction.add(60, bitmap);
        }

        // Duck
        Action duckAction = getAction(Action.ID.DUCK);
        duckAction.clear();
        ArrayList<Bitmap> duckList = character.getDuckList();
        duckAction.add(20, duckList.get(0));
        duckAction.setBreakable(true);

        // Punch
        Action action = getAction(Action.ID.PUNCH);
        action.clear();
        ArrayList<Bitmap> bmpSet = character.getBitmapList(Character.SetID.BLOW);
        action.add(8, bmpSet.get(0));
        action.add(7, bmpSet.get(1));
        action.add(10, bmpSet.get(2));
        action.add(3, bmpSet.get(1));
        action.add(5, bmpSet.get(0));

        // Low kick
        action = getAction(Action.ID.LOW_KICK);
        action.clear();
        bmpSet = character.getBitmapList(Character.SetID.LOWKICK);
        action.add(8, bmpSet.get(0));
        action.add(15, bmpSet.get(1));
        action.add(12, bmpSet.get(0));
    }

    public void go() {

    }
}