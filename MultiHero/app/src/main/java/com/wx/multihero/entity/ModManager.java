package com.wx.multihero.entity;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;

import java.util.ArrayList;

public class ModManager {
    private static ArrayList<Mod> mModList = new ArrayList<Mod>();
    private static final String MOD_ROOT = "maps";

    public ModManager() {

    }

    public static void load() {
        mModList.clear();
        ArrayList<String> modFileNameList = AssetsLoader.getFileNameList(MOD_ROOT);
        for(String modName : modFileNameList) {
            Mod mod = new Mod(Utils.merge(MOD_ROOT, modName));
            mModList.add(mod);
        }
    }

    public static ArrayList<Mod> getModList() {
        return mModList;
    }

    public static Mod getMod(int index) {
        if(mModList.size() < 1)
            return null;

        return mModList.get(index);
    }

    public static ArrayList<String> getModNames() {
        ArrayList<String> modNames = new ArrayList<String>();
        for(Mod mod : mModList) {
            modNames.add(mod.getName());
        }
        return  modNames;
    }

    public static Mod getModByName(String name) {
        for(Mod mod : mModList) {
            if(mod.getName() == name) {
                return mod;
            }
        }
        return null;
    }
}
