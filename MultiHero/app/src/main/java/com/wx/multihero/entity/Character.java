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

package com.wx.multihero.entity;

import android.graphics.Bitmap;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class Character {
	private Bitmap mIcon;
	private Bitmap mPreview;
	private Bitmap mDuckBitmap;
	private Bitmap mAirBitmap;
	private ArrayList<Bitmap> mWalkList = new ArrayList<Bitmap>();
	private ArrayList<Bitmap> mFallingList = new ArrayList<Bitmap>();
	private ArrayList<Bitmap> mFlipList = new ArrayList<Bitmap>();
	private ArrayList<Bitmap> mActionList = new ArrayList<Bitmap>();
	private ArrayList<Bitmap> mUpSpecialList = new ArrayList<Bitmap>();
	private ArrayList<Bitmap> mFlyKickList = new ArrayList<Bitmap>();
	private ArrayList<Bitmap> mLowKickList = new ArrayList<Bitmap>();
	private ArrayList<Bitmap> mSpecialList = new ArrayList<Bitmap>();
	private ArrayList<Bitmap> mDSpecialList = new ArrayList<Bitmap>();
	private ArrayList<Bitmap> mBlockList = new ArrayList<Bitmap>();
	private ArrayList<Bitmap> mUpActionList = new ArrayList<Bitmap>();
	private ArrayList<Bitmap> mGrabList = new ArrayList<Bitmap>();
	private ArrayList<Bitmap> mSuperList = new ArrayList<Bitmap>();
	private ArrayList<Bitmap> mNoActionList = new ArrayList<Bitmap>();
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
		loadBitmapList("blow", mActionList);
		loadBitmapList("upspecial", mUpSpecialList);
		loadBitmapList("flykick", mFlyKickList);
		loadBitmapList("lowkick", mLowKickList);
		loadBitmapList("special", mSpecialList);
		loadBitmapList("dspecial", mDSpecialList);
		loadBitmapList("block", mBlockList);
		loadBitmapList("upblow", mUpActionList);
		loadBitmapList("grab", mGrabList);
		loadBitmapList("super", mSuperList);
		loadBitmapList("noaction", mNoActionList);
		if(mNoActionList.isEmpty()) {
			mNoActionList.add(mPreview);
		}
	}

	public Bitmap getIcon() {
		return mIcon;
	}

	public Bitmap getPreview() {
		return mPreview;
	}

	public Bitmap getDuckBitmap() {
		return mDuckBitmap;
	}

	public Bitmap getAirBitmap() {
		return mAirBitmap;
	}

	public ArrayList<Bitmap> getWalkList() {
		return mWalkList;
	}

	public ArrayList<Bitmap> getFallingList() {
		return mFallingList;
	}

	public ArrayList<Bitmap> getFlipList() {
		return mFlipList;
	}

	public ArrayList<Bitmap> getActionList() {
		return mActionList;
	}

	public ArrayList<Bitmap> getUpSpecialList() {
		return mUpSpecialList;
	}

	public ArrayList<Bitmap> getFlyKickList() {
		return mFlyKickList;
	}

	public ArrayList<Bitmap> getLowKickList() {
		return mLowKickList;
	}

	public ArrayList<Bitmap> getSpecialList() {
		return mSpecialList;
	}

	public ArrayList<Bitmap> getDSpecialList() {
		return mDSpecialList;
	}

	public ArrayList<Bitmap> getBlockList() {
		return mBlockList;
	}

	public ArrayList<Bitmap> getUpActionList() {
		return mUpActionList;
	}

	public ArrayList<Bitmap> getGrabList() {
		return mGrabList;
	}

	public ArrayList<Bitmap> getSuperList() {
		return mSuperList;
	}

	public ArrayList<Bitmap> getNoActionList() {
		return mNoActionList;
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
