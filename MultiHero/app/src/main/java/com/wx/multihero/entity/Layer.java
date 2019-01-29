package com.wx.multihero.entity;

import com.wx.multihero.base.LittleEndianDataInputStream;

import java.io.IOException;
import java.util.ArrayList;

public class Layer {
    private ArrayList<Tile> mTileList = new ArrayList<Tile>();
    public Layer(LittleEndianDataInputStream inputStream) throws IOException {
        int tileAmount = inputStream.readInt();
        for(int i=0;i<tileAmount;i++) {
            Tile tile = new Tile(inputStream);
            mTileList.add(tile);
        }
    }

    public ArrayList<Tile> getTileList() {
        return mTileList;
    }

    public int getTilesAmount() {
        return mTileList.size();
    }
}
