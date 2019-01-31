package com.wx.multihero.entity;

import com.wx.multihero.base.LittleEndianDataInputStream;

import java.io.IOException;
import java.util.ArrayList;

public class Animation {
    private int seg;
    private int frames;
    private int bgsel;
    private int nsel;
    private int curFrame;
    public Tile tile;

    public ArrayList<KeyFrame> mFrames = new ArrayList<KeyFrame>();
    public Animation(LittleEndianDataInputStream inputStream, Map map) throws IOException {
        seg = inputStream.readInt();
        frames = inputStream.readInt();
        bgsel = inputStream.readInt();
        nsel = inputStream.readInt();
        curFrame = inputStream.readInt()-1;
        tile = map.mLayerList.get(bgsel).getTile(nsel-1);
        for(int i=0;i<frames;i++) {
            KeyFrame frame = new KeyFrame(inputStream, map);
            mFrames.add(frame);
        }
        ArrayList<KeyFrame> framesList = new ArrayList<KeyFrame>();
        for(int i=curFrame;i<frames;i++) {
            framesList.add(mFrames.get(i));
        }
        for(int i=0;i<curFrame;i++) {
            framesList.add(mFrames.get(i));
        }
        mFrames.clear();
        mFrames = framesList;
    }
}
