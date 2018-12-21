package com.wx.multihero;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class Box {
	private ArrayList<Bitmap> mBitmaps = new ArrayList<Bitmap>();
	
	public void loadAssets(int id) {
		for(int i=1;i<=5;i++) {
			Bitmap bitmap = Utils.loadBitmap(String.format("gfx/box%d_a%d.png", id, i));
			mBitmaps.add(bitmap);
		}
	}
}
