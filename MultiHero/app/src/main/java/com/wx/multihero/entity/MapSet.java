package com.wx.multihero.entity;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;

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
