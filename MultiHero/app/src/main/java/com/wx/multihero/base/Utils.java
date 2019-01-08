package com.wx.multihero.base;

import android.content.Context;

public class Utils {
	public static final float BASE_SCREEN_WIDTH = 800.0f;
	public static final float BASE_SCREEN_HEIGHT = 480f;
	public static final float GOLD_LINE = 0.628f;
	private static float mScreenWidth = BASE_SCREEN_WIDTH;
	private static float mScreenHeight = BASE_SCREEN_HEIGHT;
	private static Context mContext = null;

	public static float getScreenWidth() {
		return mScreenWidth;
	}
	public static void setScreenWidth(float width) {
		mScreenWidth = width;
	}
	public static float getScreenHeight() {
		return mScreenHeight;
	}
	public static void setScreenHeight(float height) {
		mScreenHeight = height;
	}
	public static void setResolution(float width, float height) {
		mScreenWidth = width;
		mScreenHeight = height;
	}
	public static float getRealWidth(float width) {
		return  width * mScreenWidth / BASE_SCREEN_WIDTH;
	}

	public static float getRealHeight(float height) {
		return height *mScreenHeight / BASE_SCREEN_HEIGHT;
	}
	public static String adjustDir(String path) {
	    if(!path.endsWith("/"))
        {
            path += "/";
        }
        return path;
    }
    public static String merge(String path, String name) {
	    return adjustDir(path) + name;
    }
    public static String extractFileName(String fileFullName) {
        fileFullName = fileFullName.trim();
	    int index = fileFullName.lastIndexOf("/");
	    if(index < 0) {
	        index = fileFullName.lastIndexOf("\\");
        }
        index++;
        return fileFullName.substring(index);
    }
    public static String getStringFromResourceId(int id) {
		if(mContext != null) {
			return mContext.getResources().getString(id);
		}
		return "";
	}

	public static void setContext(Context context) {
		mContext = context;
	}
}
