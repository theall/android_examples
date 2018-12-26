package com.wx.multihero.entity;

import com.wx.multihero.base.AssetsLoader;

import java.util.ArrayList;

public class MapSet {
    private String mName;

    private ArrayList<Map> mMapList = new ArrayList<Map>();

    public MapSet(String path) {
        ArrayList<String> mapFileNameList = AssetsLoader.getFileNameList(path, ".dat");
        for(String mapName : mapFileNameList) {
            Map map = new Map(path + "/" + mapName);
            mMapList.add(map);
        }
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
}
