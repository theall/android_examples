package com.wx.multihero.entity;

import java.io.DataInputStream;
import java.io.IOException;

public class Area {
    public int x;
    public int y;
    public int w;
    public int h;
    public int fleeDir;
    public int dangerArea;
    public int edges;
    public int moveBy;

    public Area(DataInputStream inputStream) throws IOException {
        x = inputStream.readInt();
        y = inputStream.readInt();
        w = inputStream.readInt();
        h = inputStream.readInt();
        fleeDir = inputStream.readInt();
        dangerArea = inputStream.readInt();
        edges = inputStream.readInt();
        moveBy = inputStream.readInt();
    }
}
