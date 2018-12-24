package com.wx.multihero.entity;

import java.util.ArrayList;

public class TriggersManager {
	private ArrayList<Trigger> mTriggers = new ArrayList<Trigger>();
	
	public void loadAssets() {
		for(int i=1;i<=20;i++) {
			Trigger trigger = new Trigger();
			trigger.loadAssets(i);
			mTriggers.add(trigger);
		}
	}
}
