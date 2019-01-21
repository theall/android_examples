package com.wx.multihero.entity;

import java.io.DataInputStream;
import java.io.IOException;

public class Point {
    public int x;
    public int y;

    public Point(DataInputStream inputStream) throws IOException {
        x = inputStream.readInt();
        y = inputStream.readInt();
    }
}
