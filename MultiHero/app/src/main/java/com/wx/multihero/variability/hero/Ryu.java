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

        mActionDist[Action.PUNCH] = 44;
        mActionDist[Action.FLYING_KICK] = 48;
        mActionDist[Action.LOW_KICK] = 44;
        mActionDist[Action.UPPERCUT] = 48;
        mActionDist[Action.SPECIAL] = 300;
        mActionDist[Action.DOWN_SPECIAL] = 70;
        mActionDist[Action.HIGH_KICK] = 44;
        mActionDist[Action.CLUB] = 150;
        mActionDist[Action.SUPER_SPECIAL] = 600;
    }

    public void load(Character character) {
        mActionMap.clear();

        // Ready
        Action readyAction = new Action(Action.READY);
        for(Bitmap bitmap : character.getNoActionList()) {
            readyAction.add(bitmap);
        }
        add(readyAction);

        // walk
        Action walkAction = new Action(Action.WALK);
        for(Bitmap bitmap : character.getWalkList()) {
            walkAction.add(10, bitmap);
        }
        add(walkAction);

        // Blocking
        Action blockAction = new Action(Action.BLOCKING);
        for(Bitmap bitmap : character.getBlockList()) {
            blockAction.add(20, bitmap);
        }
        add(blockAction);

        // punch
        Action punchAction = new Action(Action.PUNCH);
        ArrayList<Bitmap> blowList = character.getActionList();
        punchAction.add(8, blowList.get(0));
        punchAction.add(7, blowList.get(1));
        punchAction.add(10, blowList.get(2));
        add(punchAction);
    }

    public void go() {

    }
}
