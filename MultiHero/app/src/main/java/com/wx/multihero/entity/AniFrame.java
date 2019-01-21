package com.wx.multihero.entity;

import java.io.DataInputStream;
import java.io.IOException;

public class AniFrame {
    public int bg;
    public int n;
    public int time;
    public AniFrame(DataInputStream inputStream) throws IOException {
        bg = inputStream.readInt();
        n = inputStream.readInt();
        time = inputStream.readInt();
    }
}
