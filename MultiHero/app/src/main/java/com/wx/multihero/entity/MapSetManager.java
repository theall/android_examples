package com.wx.multihero.entity;

import com.wx.multihero.base.AssetsLoader;
import java.util.ArrayList;

public class MapSetManager {
    private static ArrayList<MapSet> mMapSetList = new ArrayList<MapSet>();
    private static final String MAP_ROOT = "maps";

    public MapSetManager() {

    }

    public static void load() {
        mMapSetList.clear();
        ArrayList<String> mapFileNameList = AssetsLoader.getFileNameList(MAP_ROOT);
        for(String mapSetName : mapFileNameList) {
            MapSet mapSet = new MapSet(MAP_ROOT + "/" + mapSetName);
            mMapSetList.add(mapSet);
        }
    }
    public static ArrayList<MapSet> getMapSetList() {
        return mMapSetList;
    }

    public static MapSet getMapSet(int index) {
        if(mMapSetList.size() < 1)
            return null;

        return mMapSetList.get(index);
    }

    public static ArrayList<String> getMapSetNames() {
        ArrayList<String> mapsetNames = new ArrayList<String>();
        for(MapSet mapSet : mMapSetList) {
            mapsetNames.add(mapSet.getName());
        }
        return  mapsetNames;
    }

    public static MapSet getMapSetByName(String name) {
        for(MapSet mapSet : mMapSetList) {
            if(mapSet.getName() == name) {
                return mapSet;
            }
        }
        return null;
    }
}
