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
import com.wx.multihero.variability.sprite.SerializedFrames;

public class Ryu extends Hero {
    private int mSndHue = -1;
    private int mSndUpperCut = -1;
    private int mSndBall = -1;
    private int mSndSpin = -1;

    public Ryu(Character character) {
        super(character);

        mBlowDist[Blow.PUNCH] = 44;
        mBlowDist[Blow.FLYING_KICK] = 48;
        mBlowDist[Blow.LOW_KICK] = 44;
        mBlowDist[Blow.UPPERCUT] = 48;
        mBlowDist[Blow.SPECIAL] = 300;
        mBlowDist[Blow.DOWN_SPECIAL] = 70;
        mBlowDist[Blow.HIGH_KICK] = 44;
        mBlowDist[Blow.CLUB] = 150;
        mBlowDist[Blow.SUPER_SPECIAL] = 600;
    }

    public void load(Character character) {
        mBlowFramesMap.clear();

        // walk
        SerializedFrames walkFrames = new SerializedFrames();
        for(Bitmap bitmap : character.mBlockList) {
            walkFrames.add(10, bitmap);
        }
        mBlowFramesMap.put(Blow.WALK, walkFrames);

        SerializedFrames blockFrames = new SerializedFrames();
        for(Bitmap bitmap : character.mBlockList) {
            blockFrames.add(bitmap);
        }
        mBlowFramesMap.put(Blow.BLOCKING, blockFrames);

        // punch
        SerializedFrames punchFrames = new SerializedFrames();
        punchFrames.add(8, character.mBlowList.get(0));
        punchFrames.add(7, character.mBlowList.get(1));
        punchFrames.add(10, character.mBlowList.get(2));
        punchFrames.add(5, character.mBlowList.get(5));
        mBlowFramesMap.put(Blow.PUNCH, punchFrames);
    }

    public void go() {

    }
}
