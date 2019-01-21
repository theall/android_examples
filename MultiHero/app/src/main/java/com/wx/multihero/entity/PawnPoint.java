package com.wx.multihero.entity;

import java.io.DataInputStream;
import java.io.IOException;

public class PawnPoint {
    public Point start;
    public Point respawn;
    public PawnPoint(DataInputStream inputStream) throws IOException {
        start = new Point(inputStream);
        respawn = new Point(inputStream);
    }
}
