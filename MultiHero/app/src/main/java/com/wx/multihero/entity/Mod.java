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
package com.wx.multihero.entity;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;

import java.util.ArrayList;

public class Mod {
    private String mName;
    private MapSet mAdvMaps;
    private MapSet mVsMaps;
    private MapSet mCtfMaps;
    private static final String MAP_TYPE_ADV = "adv";
    private static final String MAP_TYPE_VS = "vs";
    private static final String MAP_TYPE_CTF = "ctf";
    
    public Mod(String path) {
        mName = Utils.extractFileName(path);
        mAdvMaps = new MapSet(Utils.merge(path, MAP_TYPE_ADV), mName);
        mVsMaps = new MapSet(Utils.merge(path, MAP_TYPE_VS), mName);
        mCtfMaps = new MapSet(Utils.merge(path, MAP_TYPE_CTF), mName);
    }
    
    public String getName() {
        return mName;
    }

    public MapSet getAdvMaps() {
        return mAdvMaps;
    }

    public MapSet getVsMaps() {
        return mVsMaps;
    }

    public MapSet getCtfMaps() {
        return mCtfMaps;
    }
}
