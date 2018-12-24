package com.wx.multihero.entity;

import android.graphics.Bitmap;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;

public class Trigger {
	private Bitmap bitmap1;
	private Bitmap bitmap2;
	
	public void loadAssets(int id) {
		bitmap1 = AssetsLoader.loadBitmap(String.format("gfx/stuff/trig%d_a1.png", id));
		bitmap2 = AssetsLoader.loadBitmap(String.format("gfx/stuff/trig%d_a2.png", id));
	}
}
