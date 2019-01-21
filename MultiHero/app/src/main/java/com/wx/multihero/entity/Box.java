package com.wx.multihero.entity;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.graphics.Bitmap;

import com.wx.multihero.base.AssetsLoader;

public class Box {
	public float x;
	public float y;
	public int xspeed;
	public int yspeed;
	public int target;
	public int draw;
	public int width;
	public int height;
	public int curPoint;
	public int pointsAmount;
	public int type;
	public int chunkType;
	public int hitMode;
	public int hitTime;
	public int hitSpeed;
	public int hitYSpeed;
	public int damage;
	public int hitSound;
	public int useTrigger;
	public int eventN;
	public int finalDest;
	public int break_;
	public int sound;
	public int breakable;
	public int eventN2;
	public ArrayList<Point> mPointList = new ArrayList<Point>();
	private ArrayList<Bitmap> mBitmaps = new ArrayList<Bitmap>();

	public Box(DataInputStream inputStream) throws IOException {
		x = inputStream.readFloat();
		y = inputStream.readFloat();
		xspeed = inputStream.readInt();
		yspeed = inputStream.readInt();
		target = inputStream.readInt();
		draw = inputStream.readInt();
		width = inputStream.readInt();
		height = inputStream.readInt();
		curPoint = inputStream.readInt();
		pointsAmount = inputStream.readInt();
		type = inputStream.readInt();
		chunkType = inputStream.readInt();
		hitMode = inputStream.readInt();
		hitTime = inputStream.readInt();
		hitSpeed = inputStream.readInt();
		hitYSpeed = inputStream.readInt();
		damage = inputStream.readInt();
		hitSound = inputStream.readInt();
		useTrigger = inputStream.readInt();
		eventN = inputStream.readInt();
		finalDest = inputStream.readInt();
		break_ = inputStream.readInt();
		sound = inputStream.readInt();
		breakable = inputStream.readInt();
		eventN2 = inputStream.readInt();
		for(int i=0;i<pointsAmount;i++){
			Point p = new Point(inputStream);
			mPointList.add(p);
		}
	}

	public void loadAssets(int id) {
		for(int i=1;i<=5;i++) {
			Bitmap bitmap = AssetsLoader.getInstance().loadBitmap(String.format("gfx/box%d_a%d.png", id, i));
			mBitmaps.add(bitmap);
		}
	}
}
