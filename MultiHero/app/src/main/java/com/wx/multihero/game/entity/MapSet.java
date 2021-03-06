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

import com.wx.multihero.game.base.AssetsLoader;
import com.wx.multihero.game.base.Utils;

import java.util.ArrayList;

public class MapSet {
    private String mName;
    private int mCurrentMapIndex;
    private ArrayList<Map> mMapList = new ArrayList<Map>();

    public MapSet(String path, String name) {
        mName = name;
        ArrayList<String> mapFileNameList = AssetsLoader.getInstance().getFileNameList(path, ".dat");
        for(String mapName : mapFileNameList) {
            Map map = new Map(Utils.merge(path, mapName));
            mMapList.add(map);
        }
        mCurrentMapIndex = 0;
    }

    public String getName() {
        return mName;
    }

    public ArrayList<Map> getMapList() {
        return mMapList;
    }

    public int getMapCount() {
        return mMapList.size();
    }

    public Map getMap(int index) {
        Map map = null;
        if(index>=0 && index<mMapList.size())
            map = mMapList.get(index);
        return map;
    }

    public int getCurrentMapIndex() {
        return mCurrentMapIndex;
    }

    public Map getCurrentMap() {
        if(mCurrentMapIndex>=0 && mCurrentMapIndex<mMapList.size())
            return mMapList.get(mCurrentMapIndex);
        return null;
    }
    public void setCurrentMapIndex(int currentMapIndex) {
        mCurrentMapIndex = currentMapIndex;
    }
}
