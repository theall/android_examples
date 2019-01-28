package com.wx.multihero.entity;

import com.wx.multihero.base.LittleEndianDataInputStream;

import java.io.IOException;
import java.util.ArrayList;

public class Animation {
    public int seg;
    public int frames;
    public int bgsel;
    public int nsel;
    public int curFrame;
    private ArrayList<AniFrame> mFrames = new ArrayList<AniFrame>();
    public Animation(LittleEndianDataInputStream inputStream) throws IOException {
        seg = inputStream.readInt();
        frames = inputStream.readInt();
        bgsel = inputStream.readInt();
        nsel = inputStream.readInt();
        curFrame = inputStream.readInt();
        for(int i=0;i<frames;i++) {
            AniFrame frame = new AniFrame(inputStream);
            mFrames.add(frame);
        }
    }
}
