package com.wx.multihero.entity;

import com.wx.multihero.base.LittleEndianDataInputStream;

import java.io.IOException;

public class AniFrame {
    public int bg;
    public int n;
    public int time;
    public AniFrame(LittleEndianDataInputStream inputStream) throws IOException {
        bg = inputStream.readInt();
        n = inputStream.readInt();
        time = inputStream.readInt();
    }
}
