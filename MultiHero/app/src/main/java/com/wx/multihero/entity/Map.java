package com.wx.multihero.entity;

import android.graphics.Bitmap;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Map {
    private Bitmap mThumbBitmap;
    private String mMapName;
    private ArrayList<Area> mAreaList = new ArrayList<Area>();
    private ArrayList<DArea> mDAreaList = new ArrayList<DArea>();
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
    private int[] mNexMap = new int[5];
    private int mXScrStart;
    private int mYScrStart;
    private int mFightMode;
    private int mMapN;
    private int mScrLock;
    private int mLoadvsMode;
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
    public enum Type {
        ADV,
        VS
    }

    public Map(String assetFileName) {
        mMapName = Utils.extractFileName(assetFileName);
        load(assetFileName);
    }

    public void load(String assetFileName) {
        DataInputStream inputStream = AssetsLoader.getInstance().loadFile(assetFileName);
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

    private void parseMapData(DataInputStream inputStream) {
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
            mBackgroundColor = 0xff<<24 + r<<16 + g<<8 + b;

            // 5 layers
            mLayerList.clear();
            for(int i=0;i<5;i++) {
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
                mNexMap[i] = inputStream.readInt();
            }

            mXScrStart = inputStream.readInt();
            mYScrStart = inputStream.readInt();
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
            mStri1 = readString(inputStream);
            mStri2 = readString(inputStream);
            mStri3 = readString(inputStream);
            mLScrLimit = inputStream.readInt();
            mRScrLimit = inputStream.readInt();
            mMusicN1 = inputStream.readInt();
            mMusicN2 = inputStream.readInt();

            int animationAmount = inputStream.readInt();
            mAnimationList.clear();
            for(int i=0;i<animationAmount;i++) {
                Animation animation = new Animation(inputStream);
                mAnimationList.add(animation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readString(DataInputStream inputStream) throws IOException {
        String result = "";
        char c = '\0';
        do {
            c = inputStream.readChar();
            result += c;
        } while (c!=0);
        return result;
    }
}
