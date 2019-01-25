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
