package com.wx.multihero;

import android.graphics.Bitmap;

public class Platform {
	private Bitmap bitmap;
	
	public void loadAssets(int id) {
		bitmap = Utils.loadBitmap(String.format("gfx/stuff/plat%d.png", id));
	}
}
