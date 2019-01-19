package com.wx.multihero.entity;

import android.graphics.Bitmap;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;

import java.io.File;
import java.io.InputStream;

public class Map {
    private Bitmap mThumbBitmap;
    private String mMapName;

    public enum Type {
        ADV,
        VS
    }

    public Map(String assetFileName) {
        mMapName = Utils.extractFileName(assetFileName);
        load(assetFileName);
    }

    public void load(String assetFileName) {
        InputStream inputStream = AssetsLoader.getInstance().loadFile(assetFileName);
        if(inputStream != null)
        {
            assetFileName = assetFileName.replace(".dat", ".jpg");
            mThumbBitmap = AssetsLoader.getInstance().loadBitmap(assetFileName);
            parseMapData(inputStream);
        }
    }
    public Bitmap getThumbBitmap() {
        return mThumbBitmap;
    }

    private void parseMapData(InputStream inputStream) {

    }
}
