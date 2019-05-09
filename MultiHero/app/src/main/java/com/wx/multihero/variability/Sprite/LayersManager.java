package com.wx.multihero.variability.Sprite;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.wx.multihero.base.Renderable;
import com.wx.multihero.base.Stepable;
import com.wx.multihero.base.Utils;
import com.wx.multihero.entity.Layer;
import com.wx.multihero.entity.Map;

import java.util.ArrayList;
import java.util.Random;

public class LayersManager implements Renderable, Stepable {
    private MainLayer mMainLayer;
    private ArrayList<TilesLayer> mForegroundLayerList;
    private ArrayList<TilesLayer> mBackgroundLayerList;
    private int mBackgroundColor;
    private float mOffsetX;
    private float mOffsetY;
    private int mQuakeFrames;
    private Random mRandom;

    public LayersManager() {
        mForegroundLayerList = new ArrayList<TilesLayer>();
        mBackgroundLayerList = new ArrayList<TilesLayer>();
        mBackgroundColor = Color.BLACK;
        mQuakeFrames = 0;
        mRandom = new Random();
        mMainLayer = new MainLayer();
    }

    public void setMap(Map map) {
        mBackgroundColor = map.mBackgroundColor;
        mOffsetX = map.mXScrStart;
        mOffsetY = map.mYScrStart;
        mBackgroundLayerList.clear();
        for(int i=0;i<3;i++) {
            Layer layer = map.mLayerList.get(i);
            TilesLayer tilesLayer = new TilesLayer();
            tilesLayer.loadFromLayer(layer);
            mBackgroundLayerList.add(tilesLayer);
        }
        mMainLayer.loadMap(map);
        mForegroundLayerList.clear();
        for(int i=3;i<6;i++) {
            Layer layer = map.mLayerList.get(i);
            TilesLayer tilesLayer = new TilesLayer();
            tilesLayer.loadFromLayer(layer);
            mForegroundLayerList.add(tilesLayer);
        }
    }

    public void render(Canvas canvas, Paint paint) {
        canvas.drawColor(mBackgroundColor);
        float offsetY = mOffsetY;
        if(mQuakeFrames > 0) {
            offsetY += getRandValue();
        }
        canvas.translate(-mOffsetX, -offsetY);
        for(TilesLayer tilesLayer : mBackgroundLayerList) {
            tilesLayer.render(canvas, paint);
        }
        mMainLayer.render(canvas, paint);
        for(TilesLayer tilesLayer : mForegroundLayerList) {
            tilesLayer.render(canvas, paint);
        }
        canvas.translate(mOffsetX, offsetY);
    }

    public void step() {
        mMainLayer.step();
        if(mQuakeFrames > 0) {
            mQuakeFrames--;
        }
        for(TilesLayer tilesLayer : mBackgroundLayerList) {
            tilesLayer.step();
        }
        for(int i=0;i<mForegroundLayerList.size()-1;i++) {
            mForegroundLayerList.get(i).step();
        }
    }

    private final float getRandValue() {
        float value = mRandom.nextBoolean()?1:-1;
        return Utils.getRealHeight(value);
    }

    public void earthQuake() {
        mQuakeFrames = 60;
    }
}
