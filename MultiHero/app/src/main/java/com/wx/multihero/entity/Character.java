package com.wx.multihero.entity;

import android.graphics.Bitmap;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;
import com.wx.multihero.variability.Chunk.BluerayImpact;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Character {
	public Bitmap mIcon;
	public Bitmap mPreview;
	public Bitmap mDuckBitmap;
	public Bitmap mAirBitmap;
	public ArrayList<Bitmap> mWalkList = new ArrayList<Bitmap>();
	public ArrayList<Bitmap> mFallingList = new ArrayList<Bitmap>();
	public ArrayList<Bitmap> mFlipList = new ArrayList<Bitmap>();
	public ArrayList<Bitmap> mBlowList = new ArrayList<Bitmap>();
	public ArrayList<Bitmap> mUpSpecialList = new ArrayList<Bitmap>();
	public ArrayList<Bitmap> mFlyKickList = new ArrayList<Bitmap>();
	public ArrayList<Bitmap> mLowKickList = new ArrayList<Bitmap>();
	public ArrayList<Bitmap> mSpecialList = new ArrayList<Bitmap>();
	public ArrayList<Bitmap> mDSpecialList = new ArrayList<Bitmap>();
	public ArrayList<Bitmap> mBlockList = new ArrayList<Bitmap>();
	public ArrayList<Bitmap> mUpBlowList = new ArrayList<Bitmap>();
	public ArrayList<Bitmap> mGrabList = new ArrayList<Bitmap>();
	public ArrayList<Bitmap> mSuperList = new ArrayList<Bitmap>();
	public ArrayList<Bitmap> mNoActionList = new ArrayList<Bitmap>();
	private HashMap<String,Bitmap> mStringBitmapMap = new HashMap<String, Bitmap>();
	private int mId;

	public Character(int id) {
		mId = id;
	}

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		mId = id;
	}

	public void loadAssets(String path) {
		AssetsLoader assetsLoader = AssetsLoader.getInstance();
		ArrayList<String> bitmapFileNameList = assetsLoader.getFileNameList(path, ".png");
		path = Utils.adjustDir(path);
		mStringBitmapMap.clear();
		for(String fileName : bitmapFileNameList) {
			Bitmap bitmap = assetsLoader.loadBitmap(Utils.merge(path, fileName));
			String fileNameNoCase = fileName.toLowerCase();
			if(bitmap != null) {
				mStringBitmapMap.put(fileNameNoCase, bitmap);
			}
		}
		mIcon = mStringBitmapMap.get("zicon.png");
		mDuckBitmap = mStringBitmapMap.get("zduck.png");
		mAirBitmap = mStringBitmapMap.get("zair.png");

		loadBitmapList("walk", mWalkList);
		mPreview = mStringBitmapMap.get("zwalk0.png");

		loadBitmapList("falling", mFallingList);
		mFallingList.add(0, mStringBitmapMap.get("zfallen.png"));

		loadBitmapList("flip", mFlipList);
		loadBitmapList("blow", mBlowList);
		loadBitmapList("upspecial", mUpSpecialList);
		loadBitmapList("flykick", mFlyKickList);
		loadBitmapList("lowkick", mLowKickList);
		loadBitmapList("special", mSpecialList);
		loadBitmapList("dspecial", mDSpecialList);
		loadBitmapList("block", mBlockList);
		loadBitmapList("upblow", mUpBlowList);
		loadBitmapList("grab", mGrabList);
		loadBitmapList("super", mSuperList);
		loadBitmapList("noaction", mNoActionList);
	}

	public Bitmap getIcon() {
		return mIcon;
	}

	public Bitmap getPreview() {
		return mPreview;
	}

	private void loadBitmapList(String prefix, ArrayList<Bitmap> container) {
		container.clear();
		for(int i=1;i<100;i++) {
			String fileName = String.format("z%s%d.png", prefix, i);
			Bitmap bitmap = mStringBitmapMap.get(fileName);
			if(bitmap == null)
				break;
			container.add(bitmap);
		}
	}
}
