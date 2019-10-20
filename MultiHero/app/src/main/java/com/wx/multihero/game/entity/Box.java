/**
 * Copyright (C) Bilge Theall, wazcd_1608@qq.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package com.wx.multihero.game.entity;

import java.io.IOException;
import java.util.ArrayList;

import android.graphics.Bitmap;

import com.wx.multihero.game.base.AssetsLoader;
import com.wx.multihero.game.base.LittleEndianDataInputStream;

public class Box {
	public float x;
	public float y;
	public float xspeed;
	public float yspeed;
	public int target;
	public int draw;
	public int width;
	public int height;
	public int curPoint;
	public int pointsAmount;
	public int type;
	public int chunkType;
	public int hitMode;
	public float hitTime;
	public float hitSpeed;
	public float hitYSpeed;
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

	public Box(LittleEndianDataInputStream inputStream) throws IOException {
		x = inputStream.readFloat();
		y = inputStream.readFloat();
		xspeed = inputStream.readFloat();
		yspeed = inputStream.readFloat();
		target = inputStream.readInt();
		draw = inputStream.readInt();
		width = inputStream.readInt();
		height = inputStream.readInt();
		curPoint = inputStream.readInt();
		pointsAmount = inputStream.readInt();
		type = inputStream.readInt();
		chunkType = inputStream.readInt();
		hitMode = inputStream.readInt();
		hitTime = inputStream.readFloat();
		hitSpeed = inputStream.readFloat();
		hitYSpeed = inputStream.readFloat();
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
