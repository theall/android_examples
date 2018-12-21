package com.wx.multihero;

import android.graphics.Bitmap;

public class Trigger {
	private Bitmap bitmap1;
	private Bitmap bitmap2;
	
	public void loadAssets(int id) {
		bitmap1 = Utils.loadBitmap(String.format("gfx/stuff/trig%d_a1.png", id));
		bitmap2 = Utils.loadBitmap(String.format("gfx/stuff/trig%d_a2.png", id));
	}
}
