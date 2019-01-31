package com.wx.multihero.entity;

import com.wx.multihero.base.LittleEndianDataInputStream;

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
        tile = map.mLayerList.get(bg).getTile(n-1);
        if(tile == null) {
            tile = null;
        }
    }
}
