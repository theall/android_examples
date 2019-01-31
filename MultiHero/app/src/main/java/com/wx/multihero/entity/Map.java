package com.wx.multihero.entity;

import android.graphics.Bitmap;
import android.util.Log;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.LittleEndianDataInputStream;
import com.wx.multihero.base.Utils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class Map {
    public Bitmap mThumbBitmap;
    public String mMapName;
    public ArrayList<Area> mAreaList = new ArrayList<Area>();
    public ArrayList<DArea> mDAreaList = new ArrayList<DArea>();
    public ArrayList<Platform> mPlatformList = new ArrayList<Platform>();
    public ArrayList<Box> mBoxList = new ArrayList<Box>();
    public ArrayList<Wall> mWallList = new ArrayList<Wall>();
    public ArrayList<Layer> mLayerList = new ArrayList<Layer>();
    public ArrayList<PawnPoint> mPawnPointList = new ArrayList<PawnPoint>();
    public ArrayList<Integer> mEventList = new ArrayList<Integer>();
    public ArrayList<Trigger> mTriggerList = new ArrayList<Trigger>();
    public ArrayList<Animation> mAnimationList = new ArrayList<Animation>();
    public Point mFlagPoint1;
    public Point mFlagPoint2;
    public boolean mScrollMap;
    public int mBackgroundColor;
    public int[] mNextMap = new int[5];
    public float mXScrStart;
    public float mYScrStart;
    public int mFightMode;
    public int mMapN;
    public int mScrLock;
    public int mLoadvsMode;
    public int mYScrCameraBottomLimit;
    public int mUScrLimit;
    public int mNoAirStrike;
    public int mVar4;
    public int mVar5;
    public int mVar6;
    public int mVar7;
    public int mVar8;
    public int mVar9;
    public int mVar10;
    public String mStri1;
    public String mStri2;
    public String mStri3;
    public int mLScrLimit;
    public int mRScrLimit;
    public int mMusicN1;
    public int mMusicN2;
    public enum Type {
        ADV,
        VS
    }

    public Map(String assetFileName) {
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
            mLoadvsMode = inputStream.readInt();
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
