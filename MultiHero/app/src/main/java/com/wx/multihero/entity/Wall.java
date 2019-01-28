package com.wx.multihero.entity;

import com.wx.multihero.base.LittleEndianDataInputStream;

import java.io.IOException;

public class Wall {
    public int x;
    public int y;
    public int w;
    public int h;
    public Wall(LittleEndianDataInputStream inputStream) throws IOException {
        x = inputStream.readInt();
        y = inputStream.readInt();
        w = inputStream.readInt();
        h = inputStream.readInt();
    }
}
