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

public class ModManager {
    private static ModManager mInstance = null;
    private ArrayList<Mod> mModList = new ArrayList<Mod>();
    private static final String MOD_ROOT = "maps";
    private int mCurrentModIndex;
    private boolean mEnableRecycle;

    public static ModManager getInstance() {
        if(mInstance==null)
            mInstance = new ModManager();
        return mInstance;
    }

    public ModManager() {
        mCurrentModIndex = 0;
        mEnableRecycle = true;
    }

    public void load() {
        mModList.clear();
        ArrayList<String> modFileNameList = AssetsLoader.getInstance().getFileNameList(MOD_ROOT);
        for(String modName : modFileNameList) {
            Mod mod = new Mod(Utils.merge(MOD_ROOT, modName));
            mModList.add(mod);
        }
    }

    public ArrayList<Mod> getModList() {
        return mModList;
    }

    public Mod getMod(int index) {
        if(mModList.size() < 1)
            return null;

        return mModList.get(index);
    }

    public ArrayList<String> getModNames() {
        ArrayList<String> modNames = new ArrayList<String>();
        for(Mod mod : mModList) {
            modNames.add(mod.getName());
        }
        return  modNames;
    }

    public Mod getModByName(String name) {
        for(Mod mod : mModList) {
            if(mod.getName() == name) {
                return mod;
            }
        }
        return null;
    }

    public Mod getCurrentMod() {
        if(mCurrentModIndex<0 || mCurrentModIndex>=mModList.size())
            return null;
        return mModList.get(mCurrentModIndex);
    }

    public Map getMap(int index) {
        Map map = null;
        Mod mod = getCurrentMod();
        if(mod!=null) {
            MapSet mapSet = mod.getVsMaps();
            if(mapSet != null)
                map = mapSet.getMap(index);
        }
        return map;
    }

    private void recycleIndex() {
        int lastIndex = mModList.size() - 1;
        if(mCurrentModIndex < 0)
            mCurrentModIndex = lastIndex;
        else if(mCurrentModIndex > lastIndex)
            mCurrentModIndex = 0;
    }

    public void shiftNext() {
        if(mEnableRecycle) {
            mCurrentModIndex++;
            recycleIndex();
        }
    }

    public void shiftBack() {
        if(mEnableRecycle) {
            mCurrentModIndex--;
            recycleIndex();
        }
    }

    public void enableRecycleShift(boolean enabled) {
        mEnableRecycle = enabled;
    }
}
