package com.wx.multihero.base;

public class Utils {
	public static final float BASE_SCREEN_WIDTH = 800.0f;
	public static final float BASE_SCREEN_HEIGHT = 480f;
	public static final float GOLD_LINE = 0.628f;

	public static float getRealWidth(float width) {
		return  width / BASE_SCREEN_WIDTH;
	}

	public static float getRealHeight(float height) {
		return height / BASE_SCREEN_HEIGHT;
	}
}
