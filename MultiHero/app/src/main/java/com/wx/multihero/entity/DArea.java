package com.wx.multihero.entity;

import com.wx.multihero.base.LittleEndianDataInputStream;

import java.io.IOException;

public class DArea {
    public int x;
    public int y;
    public int w;
    public int h;
    public int fleeDir;
    public int type;
    public int targetH;
    public int moveBy;

    public DArea(LittleEndianDataInputStream inputStream) throws IOException {
        x = inputStream.readInt();
        y = inputStream.readInt();
        w = inputStream.readInt();
        h = inputStream.readInt();
        fleeDir = inputStream.readInt();
        type = inputStream.readInt();
        targetH = inputStream.readInt();
        moveBy = inputStream.readInt();
    }
}
