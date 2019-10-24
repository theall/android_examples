/**
 * Copyright (C) Bilge Theall, wazcd_1608@qq.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package com.wx.multihero.game.variability.sprite;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.wx.multihero.game.base.Renderable;
import com.wx.multihero.game.base.Stepable;
import com.wx.multihero.game.base.Utils;
import com.wx.multihero.game.entity.Layer;
import com.wx.multihero.game.entity.Map;
import com.wx.multihero.game.variability.ui.Player;

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

    public void setMap(Map map, ArrayList<Player> playerList) {
        mBackgroundColor = map.getBackgroundColor();
        mOffsetX = map.getXScrStart();
        mOffsetY = map.getYScrStart();
        mBackgroundLayerList.clear();
        for(int i=0;i<3;i++) {
            Layer layer = map.getLayer(i);
            TilesLayer tilesLayer = new TilesLayer();
            tilesLayer.loadFromLayer(layer);
            mBackgroundLayerList.add(tilesLayer);
        }
        mMainLayer.loadMap(map, playerList);

        mForegroundLayerList.clear();
        for(int i=3;i<6;i++) {
            Layer layer = map.getLayer(i);
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
        for (TilesLayer tilesLayer : mBackgroundLayerList) {
            tilesLayer.render(canvas, paint);
        }
        mMainLayer.render(canvas, paint);
        for (TilesLayer tilesLayer : mForegroundLayerList) {
            tilesLayer.render(canvas, paint);
        }
        canvas.translate(mOffsetX, offsetY);
    }

    public void step() {
        if(mQuakeFrames > 0) {
            mQuakeFrames--;
        }
        for(TilesLayer tilesLayer : mBackgroundLayerList) {
            tilesLayer.step();
        }
        mMainLayer.step();
        for(TilesLayer tilesLayer : mForegroundLayerList) {
            tilesLayer.step();
        }
    }

    private final float getRandValue() {
        int sign = mRandom.nextBoolean()?1:-1;
        int value = mRandom.nextInt(5);
        return Utils.getRealHeight(value)*sign;
    }

    public void earthQuake() {
        mQuakeFrames = 60;
    }
}
