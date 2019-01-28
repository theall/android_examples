package com.wx.multihero.entity;

import android.graphics.Bitmap;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.LittleEndianDataInputStream;

import java.io.IOException;

public class Trigger {
	private Bitmap bitmap1;
	private Bitmap bitmap2;
	public int x;
	public int y;
	public int w;
	public int h;
	public int way;
	public int on;
	public int zaction;
	public int passBy;
	public int objHit;
	public int event;
	public int draw;
	public int imageN;
	public int imgX;
	public int imgY;
	public int affect;
	public int sound;
	public int follow;
	public int platx;
	public int platy;
	public int onStatus;
	public int offStatus;

	public Trigger(LittleEndianDataInputStream inputStream) throws IOException {
		x = inputStream.readInt();
		y = inputStream.readInt();
		w = inputStream.readInt();
		h = inputStream.readInt();
		way = inputStream.readInt();
		on = inputStream.readInt();
		zaction = inputStream.readInt();
		passBy = inputStream.readInt();
		objHit = inputStream.readInt();
		event = inputStream.readInt();
		draw = inputStream.readInt();
		imageN = inputStream.readInt();
		imgX = inputStream.readInt();
		imgY = inputStream.readInt();
		affect = inputStream.readInt();
		sound = inputStream.readInt();
		follow = inputStream.readInt();
		platx = inputStream.readInt();
		platy = inputStream.readInt();
		onStatus = inputStream.readInt();
		offStatus = inputStream.readInt();
	}

	public void loadAssets(int id) {
		bitmap1 = AssetsLoader.getInstance().loadBitmap(String.format("gfx/stuff/trig%d_a1.png", id));
		bitmap2 = AssetsLoader.getInstance().loadBitmap(String.format("gfx/stuff/trig%d_a2.png", id));
	}
}
