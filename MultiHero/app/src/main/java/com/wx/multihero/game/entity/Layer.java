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

public class Layer {
    private ArrayList<Tile> mTileList = new ArrayList<Tile>();
    public Layer(LittleEndianDataInputStream inputStream) throws IOException {
        int tileAmount = inputStream.readInt();
        for(int i=0;i<tileAmount;i++) {
            Tile tile = new Tile(inputStream);
            mTileList.add(tile);
        }
    }

    public ArrayList<Tile> getTileList() {
        return mTileList;
    }

    public int getTilesAmount() {
        return mTileList.size();
    }

    public Tile getTile(int index) {
        if(index>=0 && index<mTileList.size())
            return mTileList.get(index);
        return null;
    }
}
