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

package com.wx.multihero.game.variability.chunk;

import com.wx.multihero.game.base.AssetsLoader;
import com.wx.multihero.os.SoundPlayer;
import com.wx.multihero.game.base.Utils;

public class RoundIntroduction extends Chunk {
    private int mSoundReady;
    private int mSoundFight;
    private int mFrameSeg;
    private float mStepY;
    public RoundIntroduction() {
        super(Type.ROUND_INTRODUCTION);

        add(100, 2, 1);
        add(140, 2, 2);
        AssetsLoader assetsLoader = AssetsLoader.getInstance();
        mSoundReady = assetsLoader.loadSound("ready");
        mSoundFight = assetsLoader.loadSound("fight");
        mFrameSeg = 0;
        mStepY = Utils.getRealHeight(10);
    }

    @Override
    public void step() {
        super.step();
        mFrameSeg++;

        if(mFrameSeg<30) {

        } else if(mFrameSeg==30) {
            SoundPlayer.getInstance().playAudio(mSoundReady);
        } else if(mFrameSeg==180) {
            SoundPlayer.getInstance().playAudio(mSoundFight);
        }
    }
}
