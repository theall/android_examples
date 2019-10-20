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

public class KeyFrame {
    public int bg;
    public int n;
    public int time;
    public Tile tile;
    public KeyFrame(LittleEndianDataInputStream inputStream, Map map) throws IOException {
        bg = inputStream.readInt();
        n = inputStream.readInt();
        time = inputStream.readInt()+1;
        tile = map.getTile(bg, n-1);
        if(tile == null) {
            tile = null;
        }
    }
}
