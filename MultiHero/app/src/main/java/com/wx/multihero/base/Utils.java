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

package com.wx.multihero.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.util.Random;

public class Utils {
	public static final float BASE_SCREEN_WIDTH = 800.0f;
	public static final float BASE_SCREEN_HEIGHT = 480f;
	public static final float GOLD_LINE = 0.628f;
	private static float mScreenWidth = BASE_SCREEN_WIDTH;
	private static float mScreenHeight = BASE_SCREEN_HEIGHT;
	private static Context mContext = null;
	private static Random mRandom = null;

	public static boolean DEBUG = true;
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

	public static Bitmap getBitmapFromResourceId(int id) {
		if(mContext != null) {
			return BitmapFactory.decodeResource(mContext.getResources(), id);
		}
		return null;
	}

	public static void setContext(Context context) {
		mContext = context;
	}

	public static int getRandValue(int below, int up) {
		if(mRandom == null)
			mRandom = new Random();
		return mRandom.nextInt(up-below)+below;
	}

	public static float getRandWidth(int below, int up) {
		return getRealWidth(getRandValue(below, up));
	}

	public static float getRandHeight(int below, int up) {
		return getRealHeight(getRandValue(below, up));
	}

	public static float getGoldenWidth() {
		return mScreenWidth * mScreenWidth * GOLD_LINE / BASE_SCREEN_WIDTH;
	}
}
