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

package com.wx.multihero.game.entity;

import android.graphics.Bitmap;
import android.util.Log;

import com.wx.multihero.game.base.AssetsLoader;
import com.wx.multihero.game.base.LittleEndianDataInputStream;
import com.wx.multihero.game.base.Utils;

import java.io.IOException;
import java.util.ArrayList;

public class Map {
    public enum Type {
        ADV,
        VS
    }

    private Bitmap mThumbBitmap;
    private String mMapName;
    private ArrayList<Area> mAreaList = new ArrayList<Area>();
    private ArrayList<DArea> mDAreaList = new ArrayList<DArea>();
    private ArrayList<Platform> mPlatformList = new ArrayList<Platform>();
    private ArrayList<Box> mBoxList = new ArrayList<Box>();
    private ArrayList<Wall> mWallList = new ArrayList<Wall>();
    private ArrayList<Layer> mLayerList = new ArrayList<Layer>();
    private ArrayList<PawnPoint> mPawnPointList = new ArrayList<PawnPoint>();
    private ArrayList<Integer> mEventList = new ArrayList<Integer>();
    private ArrayList<Trigger> mTriggerList = new ArrayList<Trigger>();
    private ArrayList<Animation> mAnimationList = new ArrayList<Animation>();
    private Point mFlagPoint1;
    private Point mFlagPoint2;
    private boolean mScrollMap;
    private int mBackgroundColor;
    private int[] mNextMap = new int[5];
    private float mXScrStart;
    private float mYScrStart;
    private int mFightMode;
    private int mMapN;
    private int mScrLock;
    private int mLoadVSMode;
    private int mYScrCameraBottomLimit;
    private int mUScrLimit;
    private int mNoAirStrike;
    private int mVar4;
    private int mVar5;
    private int mVar6;
    private int mVar7;
    private int mVar8;
    private int mVar9;
    private int mVar10;
    private String mStri1;
    private String mStri2;
    private String mStri3;
    private int mLScrLimit;
    private int mRScrLimit;
    private int mMusicN1;
    private int mMusicN2;
    public float gravity;

    public Map(String assetFileName) {
        gravity = 0.2f;
        mMapName = Utils.extractFileName(assetFileName);
        load(assetFileName);
    }

    public void load(String assetFileName) {
        LittleEndianDataInputStream inputStream = AssetsLoader.getInstance().loadFile(assetFileName);
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

    public String getMapName() {
        return mMapName;
    }

    public ArrayList<Area> getAreaList() {
        return mAreaList;
    }

    public ArrayList<DArea> getDAreaList() {
        return mDAreaList;
    }

    public ArrayList<Platform> getPlatformList() {
        return mPlatformList;
    }

    public ArrayList<Box> getBoxList() {
        return mBoxList;
    }

    public ArrayList<Wall> getWallList() {
        return mWallList;
    }

    public ArrayList<Layer> getLayerList() {
        return mLayerList;
    }

    public Layer getLayer(int index) {
        Layer layer = null;
        if(index>=0 && index<mLayerList.size()) {
            layer = mLayerList.get(index);
        }
        return layer;
    }

    public Tile getTile(int layerIndex, int tileIndex) {
        Tile tile = null;
        Layer layer = getLayer(layerIndex);
        if(layer != null) {
            tile = layer.getTile(tileIndex);
        }
        return tile;
    }

    public ArrayList<PawnPoint> getPawnPointList() {
        return mPawnPointList;
    }

    public ArrayList<Integer> getEventList() {
        return mEventList;
    }

    public ArrayList<Trigger> getTriggerList() {
        return mTriggerList;
    }

    public ArrayList<Animation> getAnimationList() {
        return mAnimationList;
    }

    public Point getFlagPoint1() {
        return mFlagPoint1;
    }

    public Point getFlagPoint2() {
        return mFlagPoint2;
    }

    public boolean getScrollMap() {
        return mScrollMap;
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public int[] getNextMap() {
        return mNextMap;
    }

    public float getXScrStart() {
        return mXScrStart;
    }

    public float getYScrStart() {
        return mYScrStart;
    }

    public int getFightMode() {
        return mFightMode;
    }

    public int getMapN() {
        return mMapN;
    }

    public int getScrLock() {
        return mScrLock;
    }

    public int getLoadVSMode() {
        return mLoadVSMode;
    }

    public int getYScrCameraBottomLimit() {
        return mYScrCameraBottomLimit;
    }

    public int getUScrLimit() {
        return mUScrLimit;
    }

    public int getNoAirStrike() {
        return mNoAirStrike;
    }

    public int getVar4() {
        return mVar4;
    }

    public int getVar5() {
        return mVar5;
    }

    public int getVar6() {
        return mVar6;
    }

    public int getVar7() {
        return mVar7;
    }

    public int getVar8() {
        return mVar8;
    }

    public int getVar9() {
        return mVar9;
    }

    public int getVar10() {
        return mVar10;
    }

    public String getStri1() {
        return mStri1;
    }

    public String getStri2() {
        return mStri2;
    }

    public String getStri3() {
        return mStri3;
    }

    public int getLScrLimit() {
        return mLScrLimit;
    }

    public int getRScrLimit() {
        return mRScrLimit;
    }

    public int getMusicN1() {
        return mMusicN1;
    }

    public int getMusicN2() {
        return mMusicN2;
    }

    private void parseMapData(LittleEndianDataInputStream inputStream) {
        try {
            int areaAmount = inputStream.readInt();
            mAreaList.clear();
            for(int i=0;i<areaAmount;i++) {
                Area area = new Area(inputStream);
                mAreaList.add(area);
            }

            int dAreaAmount = inputStream.readInt();
            mDAreaList.clear();
            for(int i=0;i<dAreaAmount;i++) {
                DArea area = new DArea(inputStream);
                mDAreaList.add(area);
            }

            int platAmount = inputStream.readInt();
            mPlatformList.clear();
            for(int i=0;i<platAmount;i++) {
                Platform platform = new Platform(inputStream);
                mPlatformList.add(platform);
            }

            int boxAmount = inputStream.readInt();
            mBoxList.clear();
            for(int i=0;i<boxAmount;i++) {
                Box box = new Box(inputStream);
                mBoxList.add(box);
            }

            int wallAmount = inputStream.readInt();
            mWallList.clear();
            for(int i=0;i<wallAmount;i++) {
                Wall wall = new Wall(inputStream);
                mWallList.add(wall);
            }

            int r = inputStream.readInt();
            int g = inputStream.readInt();
            int b = inputStream.readInt();
            mBackgroundColor = (0xff<<24) + (r<<16) + (g<<8) + b;

            // 6 layers
            mLayerList.clear();
            for(int i=0;i<6;i++) {
                Layer layer = new Layer(inputStream);
                mLayerList.add(layer);
            }

            int pawnAmount = inputStream.readInt();
            mPawnPointList.clear();
            for(int i=0;i<pawnAmount;i++) {
                PawnPoint p = new PawnPoint(inputStream);
                mPawnPointList.add(p);
            }

            mFlagPoint1 = new Point(inputStream);
            mFlagPoint2 = new Point(inputStream);
            mScrollMap = inputStream.readInt()==1;

            // events
            mEventList.clear();
            for(int i=0;i<100;i++) {
                int event = inputStream.readInt();
                mEventList.add(event);
            }

            int triggerAmount = inputStream.readInt();
            mTriggerList.clear();
            for(int i=0;i<triggerAmount;i++) {
                Trigger trigger = new Trigger(inputStream);
                mTriggerList.add(trigger);
            }

            for(int i=0;i<5;i++) {
                mNextMap[i] = inputStream.readInt();
            }

            mXScrStart = inputStream.readInt();
            if(mXScrStart==0)
                mXScrStart = Utils.getRealWidth(-100);
            mYScrStart = inputStream.readInt();
            if(mYScrStart==0)
                mYScrStart = Utils.getRealHeight(-1);
            mFightMode = inputStream.readInt();
            mMapN = inputStream.readInt();
            mScrLock = inputStream.readInt();
            mLoadVSMode = inputStream.readInt();
            mYScrCameraBottomLimit = inputStream.readInt();
            mUScrLimit = inputStream.readInt();
            mNoAirStrike = inputStream.readInt();
            mVar4 = inputStream.readInt();
            mVar5 = inputStream.readInt();
            mVar6 = inputStream.readInt();
            mVar7 = inputStream.readInt();
            mVar8 = inputStream.readInt();
            mVar9 = inputStream.readInt();
            mVar10 = inputStream.readInt();
            inputStream.skipBytes(4*3);
            mLScrLimit = inputStream.readInt();
            mRScrLimit = inputStream.readInt();
            mMusicN1 = inputStream.readInt();
            mMusicN2 = inputStream.readInt();

            int animationAmount = inputStream.readInt();
            mAnimationList.clear();
            for(int i=0;i<animationAmount;i++) {
                Animation animation = new Animation(inputStream, this);
                mAnimationList.add(animation);
            }

            // merge tile and animation
            for(Animation a : mAnimationList) {
                for(int i=0;i<a.mFrames.size()-1;i++) {
                    KeyFrame currentFrame = a.mFrames.get(i);
                    KeyFrame nextFrame = a.mFrames.get(i+1);
                    if(currentFrame.tile==null || nextFrame.tile==null)
                        continue;
                    currentFrame.tile.duration = currentFrame.time;
                    currentFrame.tile.nextTile = nextFrame.tile;
                }
            }
        } catch (IOException e) {
            Log.e("Map", mMapName);
            e.printStackTrace();
        }
    }

    private Animation findAnimationByTile(Tile tile) {
        for(Animation a : mAnimationList) {
            if(a.tile==tile)
                return a;
        }
        return null;
    }
}
