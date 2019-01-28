package com.wx.multihero.entity;

import com.wx.multihero.base.LittleEndianDataInputStream;

import java.io.IOException;

public class Point {
    public int x;
    public int y;

    public Point(LittleEndianDataInputStream inputStream) throws IOException {
        x = inputStream.readInt();
        y = inputStream.readInt();
    }
}
