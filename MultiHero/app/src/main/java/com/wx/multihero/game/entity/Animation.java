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

package com.wx.multihero.game.entity;

import com.wx.multihero.game.base.LittleEndianDataInputStream;

import java.io.IOException;
import java.util.ArrayList;

public class Animation {
    private int seg;
    private int frames;
    private int bgsel;
    private int nsel;
    private int curFrame;
    public Tile tile;

    public ArrayList<KeyFrame> mFrames = new ArrayList<KeyFrame>();
    public Animation(LittleEndianDataInputStream inputStream, Map map) throws IOException {
        seg = inputStream.readInt();
        frames = inputStream.readInt();
        bgsel = inputStream.readInt();
        nsel = inputStream.readInt();
        curFrame = inputStream.readInt()-1;
        tile = map.getTile(bgsel, nsel-1);
        for(int i=0;i<frames;i++) {
            KeyFrame frame = new KeyFrame(inputStream, map);
            mFrames.add(frame);
        }
        ArrayList<KeyFrame> framesList = new ArrayList<KeyFrame>();
        for(int i=curFrame;i<frames;i++) {
            framesList.add(mFrames.get(i));
        }
        for(int i=0;i<curFrame;i++) {
            framesList.add(mFrames.get(i));
        }
        mFrames.clear();
        mFrames = framesList;
    }
}
