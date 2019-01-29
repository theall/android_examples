package com.wx.multihero.variability.Chunk;

import java.util.ArrayList;

public class ChunkManager {
    private static ChunkManager mInstance = null;
    private ArrayList<Chunk> mChunkList = new ArrayList<Chunk>();

    public ChunkManager() {
    }

    public static ChunkManager getInstance() {
        if(mInstance == null)
            mInstance = new ChunkManager();
        return mInstance;
    }

    public void loadAssets() {
        mChunkList.clear();


    }

    public void makeChunk(float x, float y) {

    }
}
