package com.wx.multihero.entity;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Layer {
    private ArrayList<Tile> mTileList = new ArrayList<Tile>();
    public Layer(DataInputStream inputStream) throws IOException {
        int tileAmount = inputStream.readInt();
        for(int i=0;i<tileAmount;i++) {
            Tile tile = new Tile(inputStream);
            mTileList.add(tile);
        }
    }
}
