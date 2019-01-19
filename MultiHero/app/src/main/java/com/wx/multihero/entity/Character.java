package com.wx.multihero.entity;

import android.graphics.Bitmap;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;

public class Character {
	private Bitmap mIcon;
	private Bitmap mPreview;

	public Character() {
	}

	public void loadAssets(String path) {
		path = Utils.adjustDir(path);
		mIcon = AssetsLoader.getInstance().loadBitmap(path+"Icon.png");
		mIcon = AssetsLoader.getInstance().loadBitmap(path+"zWalk0.png");
	}

	public Bitmap getIcon() {
		return mIcon;
	}

	public Bitmap getPreview() {
		return mPreview;
	}

}
