package com.wx.multihero.entity;

import android.graphics.Bitmap;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;

public class Platform {
	private Bitmap bitmap;
	
	public void loadAssets(int id) {
		bitmap = AssetsLoader.loadBitmap(String.format("gfx/stuff/plat%d.png", id));
	}
}
