package com.wx.multihero.entity;

import android.graphics.Bitmap;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.LittleEndianDataInputStream;
import com.wx.multihero.base.Utils;

import java.io.IOException;
import java.util.ArrayList;

public class Platform {
	private Bitmap bitmap;
	public float x;
	public float y;
	public int xspeed;
	public int yspeed;
	public int danger;
	public int draw;
	public int width;
	public int height;
	public int curPoint;
	public int pointsAmount;
	public int pic;
	public int useTrigger;
	public int eventN;
	public int finalDest;
	public int break_;
	public int chunk;
	public int sound;
	public int breakable;
	public int eventN2;
	public ArrayList<Point> mPointList = new ArrayList<Point>();
	public Platform(LittleEndianDataInputStream inputStream) throws IOException {
		x = inputStream.readFloat();
		y = inputStream.readFloat();
		xspeed = inputStream.readInt();
		yspeed = inputStream.readInt();
		danger = inputStream.readInt();
		draw = inputStream.readInt();
		width = inputStream.readInt();
		height = inputStream.readInt();
		curPoint = inputStream.readInt();
		pointsAmount = inputStream.readInt();
		pic = inputStream.readInt();
		useTrigger = inputStream.readInt();
		eventN = inputStream.readInt();
		finalDest = inputStream.readInt();
		break_ = inputStream.readInt();
		chunk = inputStream.readInt();
		sound = inputStream.readInt();
		breakable = inputStream.readInt();
		eventN2 = inputStream.readInt();
		for(int i=0;i<pointsAmount;i++){
			Point p = new Point(inputStream);
			mPointList.add(p);
		}
	}

	public void loadAssets(int id) {
		bitmap = AssetsLoader.getInstance().loadBitmap(String.format("gfx/stuff/plat%d.png", id));
	}
}
