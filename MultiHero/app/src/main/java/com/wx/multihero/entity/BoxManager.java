package com.wx.multihero.entity;

import java.util.ArrayList;

public class BoxManager {
	private ArrayList<Box> mBoxs = new  ArrayList<Box>();
	
	public void loadAssets() {
		for(int i=1;i<=20;i++) {
			Box box = new Box();
			box.loadAssets(i);
			mBoxs.add(box);
		}
	}
}
