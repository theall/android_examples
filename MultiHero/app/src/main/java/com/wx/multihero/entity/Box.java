package com.wx.multihero.entity;

import java.util.ArrayList;

import android.graphics.Bitmap;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;

public class Box {
	private ArrayList<Bitmap> mBitmaps = new ArrayList<Bitmap>();
	
	public void loadAssets(int id) {
		for(int i=1;i<=5;i++) {
			Bitmap bitmap = AssetsLoader.getInstance().loadBitmap(String.format("gfx/box%d_a%d.png", id, i));
			mBitmaps.add(bitmap);
		}
	}
}
