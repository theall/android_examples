package com.wx.multihero.entity;

import android.graphics.Bitmap;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;

public class Character {
	private Bitmap mIconBitmap;
	
	public void loadAssets(int id) {
		mIconBitmap = AssetsLoader.loadBitmap(String.format("gfx/%d/zIcon.png", id));
	}
}
