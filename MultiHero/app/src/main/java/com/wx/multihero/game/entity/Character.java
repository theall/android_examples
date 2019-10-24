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

import com.wx.multihero.game.base.AssetsLoader;
import com.wx.multihero.game.base.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class Character {
	public enum SetID {
		ICON,
		PREVIEW,
		DUCK,
		AIR,
		WALK,
		FALLING,
		FLIP,
		BLOW,
		UPSPECIAL,
		FLYKICK,
		LOWKICK,
		SPECIAL,
		DSPECIAL,
		BLOCK,
		UPBLOW,
		GRAB,
		SUPER,
		READY
	}
	private Bitmap mIcon;
	private Bitmap mPreview;
	private HashMap<String, Bitmap> mStringBitmapMap = new HashMap<String, Bitmap>();
	private HashMap<SetID, ArrayList<Bitmap>> mPictureCollection = new HashMap<SetID, ArrayList<Bitmap>>();
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
		for(SetID sid : SetID.values()) {
			mPictureCollection.put(sid, new ArrayList<Bitmap>());
		}
		mIcon = mStringBitmapMap.get("zicon.png");

		putBitmap("zduck.png", SetID.DUCK);
		putBitmap("zair.png", SetID.AIR);
		ArrayList<Bitmap> previewSet = putBitmap("zwalk0.png", SetID.PREVIEW);
		if(!previewSet.isEmpty()) {
			mPreview = previewSet.get(0);
		}

		loadBitmapList("walk", SetID.WALK);
		ArrayList<Bitmap> fallingSet = loadBitmapList("falling", SetID.FALLING);
		fallingSet.add(0, mStringBitmapMap.get("zfallen.png"));

		loadBitmapList("flip", SetID.FLIP);
		loadBitmapList("blow", SetID.BLOW);
		loadBitmapList("uspecial", SetID.UPSPECIAL);
		loadBitmapList("flykick", SetID.FLYKICK);
		loadBitmapList("lowkick", SetID.LOWKICK);
		loadBitmapList("special", SetID.SPECIAL);
		loadBitmapList("dspecial", SetID.DSPECIAL);
		loadBitmapList("block", SetID.BLOCK);
		loadBitmapList("upblow", SetID.UPBLOW);
		loadBitmapList("grab", SetID.GRAB);
		loadBitmapList("super", SetID.SUPER);
		ArrayList<Bitmap> readySet = loadBitmapList("noaction", SetID.READY);
		if(readySet.isEmpty()) {
			readySet.add(mPreview);
		}
	}

	public Bitmap getIcon() {
		return mIcon;
	}

	public Bitmap getPreview() {
		return mPreview;
	}

	public Bitmap getDuckBitmap() {
		ArrayList<Bitmap> bitmaps = mPictureCollection.get(SetID.DUCK);
		if(!bitmaps.isEmpty())
			return bitmaps.get(0);
		return null;
	}

	public Bitmap getAirBitmap() {
		ArrayList<Bitmap> bitmaps = mPictureCollection.get(SetID.AIR);
		if(!bitmaps.isEmpty())
			return bitmaps.get(0);
		return null;
	}

	public ArrayList<Bitmap> getWalkList() {
		return mPictureCollection.get(SetID.WALK);
	}

	public ArrayList<Bitmap> getFallingList() {
		return mPictureCollection.get(SetID.FALLING);
	}

	public ArrayList<Bitmap> getFlipList() {
		return mPictureCollection.get(SetID.FLIP);
	}

	public ArrayList<Bitmap> getBlowList() {
		return mPictureCollection.get(SetID.BLOW);
	}

	public ArrayList<Bitmap> getUpSpecialList() {
		return mPictureCollection.get(SetID.UPSPECIAL);
	}

	public ArrayList<Bitmap> getFlyKickList() {
		return mPictureCollection.get(SetID.FLYKICK);
	}

	public ArrayList<Bitmap> getLowKickList() {
		return mPictureCollection.get(SetID.LOWKICK);
	}

	public ArrayList<Bitmap> getSpecialList() {
		return mPictureCollection.get(SetID.SPECIAL);
	}

	public ArrayList<Bitmap> getDSpecialList() {
		return mPictureCollection.get(SetID.DSPECIAL);
	}

	public ArrayList<Bitmap> getBlockList() {
		return mPictureCollection.get(SetID.BLOCK);
	}

	public ArrayList<Bitmap> getUpBlowList() {
		return mPictureCollection.get(SetID.UPBLOW);
	}

	public ArrayList<Bitmap> getGrabList() {
		return mPictureCollection.get(SetID.GRAB);
	}

	public ArrayList<Bitmap> getSuperList() {
		return mPictureCollection.get(SetID.SUPER);
	}

	public ArrayList<Bitmap> getReadyList() {
		return mPictureCollection.get(SetID.READY);
	}
	public ArrayList<Bitmap> getDuckList() {
		return mPictureCollection.get(SetID.DUCK);
	}

	private ArrayList<Bitmap> loadBitmapList(String prefix, SetID sid) {
		ArrayList<Bitmap> container = mPictureCollection.get(sid);
		if(container == null) {
			container = new ArrayList<Bitmap>();
			mPictureCollection.put(sid, container);
		} else {
			container.clear();
		}
		for(int i=1;i<100;i++) {
			String fileName = String.format("z%s%d.png", prefix, i);
			Bitmap bitmap = mStringBitmapMap.get(fileName);
			if(bitmap == null)
				break;
			container.add(bitmap);
		}
		return container;
	}
	
	private ArrayList<Bitmap> putBitmap(String name, SetID sid) {
		ArrayList<Bitmap> bmpList = mPictureCollection.get(sid);
		if(bmpList == null) {
			bmpList = new ArrayList<Bitmap>();
			mPictureCollection.put(sid, bmpList);
		}
		Bitmap bitmap = mStringBitmapMap.get(name);
		if(bitmap != null)
			bmpList.add(bitmap);
		return bmpList;
	}
	
	public ArrayList<Bitmap> getBitmapList(SetID sid) {
		return mPictureCollection.get(sid);
	}
}
