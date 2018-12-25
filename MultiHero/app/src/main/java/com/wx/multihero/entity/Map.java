package com.wx.multihero.entity;

import android.graphics.Bitmap;

import java.io.InputStream;

public class Map {
    private Bitmap mThumbBitmap;
    private String mMapName;

    public enum Type {
        ADV,
        VS
    }

    public Map(String assetFileName) {

    }

    public Bitmap getThumbBitmap() {
        return mThumbBitmap;
    }

    private void parseMapData(InputStream inputStream) {

    }
}
