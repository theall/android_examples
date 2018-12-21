package com.wx.multihero;

import java.util.ArrayList;

public class PlatformManager {
	private ArrayList<Platform> mPlatforms = new ArrayList<Platform>();
	
	public void loadAssets() {
		for(int i=1;i<=20;i++) {
			Platform platform = new Platform();
			platform.loadAssets(i);
			mPlatforms.add(platform);
		}
	}
}
