package com.wx.multihero.entity;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;

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
