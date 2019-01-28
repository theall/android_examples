package com.wx.multihero.variability.Sprite;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.wx.multihero.base.Renderable;
import com.wx.multihero.base.Stepable;
import com.wx.multihero.entity.Layer;
import com.wx.multihero.entity.Map;
import com.wx.multihero.entity.Tile;

import java.util.ArrayList;

public class LayersManager implements Renderable, Stepable {
    private MainLayer mMainLayer;
    private ArrayList<TilesLayer> mForegroundLayerList;
    private ArrayList<TilesLayer> mBackgroundLayerList;

    public LayersManager() {
        mForegroundLayerList = new ArrayList<TilesLayer>();
        mBackgroundLayerList = new ArrayList<TilesLayer>();
    }

    public void setMap(Map map) {
        mBackgroundLayerList.clear();
        for(Layer layer : map.mLayerList) {
            TilesLayer tilesLayer = new TilesLayer();
            tilesLayer.loadFromLayer(layer);

            mBackgroundLayerList.add(tilesLayer);
        }
    }

    public void render(Canvas canvas, Paint paint) {
        for(TilesLayer tilesLayer : mBackgroundLayerList) {
            tilesLayer.render(canvas, paint);
        }
        mMainLayer.render(canvas, paint);
        for(TilesLayer tilesLayer : mForegroundLayerList) {
            tilesLayer.render(canvas, paint);
        }
    }

    public void step() {
        mMainLayer.step();
    }
}
