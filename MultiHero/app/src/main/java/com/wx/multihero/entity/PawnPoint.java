package com.wx.multihero.entity;

import com.wx.multihero.base.LittleEndianDataInputStream;

import java.io.IOException;

public class PawnPoint {
    public Point start;
    public Point respawn;
    public PawnPoint(LittleEndianDataInputStream inputStream) throws IOException {
        start = new Point(inputStream);
        respawn = new Point(inputStream);
    }
}
