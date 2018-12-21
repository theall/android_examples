package com.wx.multihero;

import android.graphics.Bitmap;

public class Character {
	private Bitmap mIconBitmap;
	
	public void loadAssets(int id) {
		mIconBitmap = Utils.loadBitmap(String.format("gfx/%d/zIcon.png", id));
	}
}
