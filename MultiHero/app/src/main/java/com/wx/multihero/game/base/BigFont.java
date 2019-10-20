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

package com.wx.multihero.game.base;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;


@SuppressLint("DefaultLocale") 
public class BigFont {
	private static Map<String, Bitmap> mStringBitmapMap = new HashMap<String, Bitmap>();
	private static final String charList = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final int SPACE_CHAR_DEFAULT_WIDTH = 16;
	
	public BigFont() {
		
	}
	
	public void loadAssets() {
		for(int i=0;i<36;i++) {
			Bitmap bitmap = AssetsLoader.getInstance().loadBitmap(String.format("gfx/fonts/l1_%d.png", i));
			String character = charList.substring(i, i+1);
			mStringBitmapMap.put(character, bitmap);
            character = character.toLowerCase();
            mStringBitmapMap.put(character, bitmap);
		}
	}
	
	public static void drawString(Canvas canvas, String string, float left, float top, Paint paint) {
		for(int i=0;i<string.length();i++) {
			if(string.charAt(i) == ' ')
			{
				left += SPACE_CHAR_DEFAULT_WIDTH;
			} else {
				Bitmap bitmap = mStringBitmapMap.get(string.substring(i, i+1));
				canvas.drawBitmap(bitmap, left, top, paint);
				left += bitmap.getWidth();
			}
			
		}
	}

	public static void drawString(Canvas canvas, String string, RectF rect, Paint paint) {
		drawString(canvas, string, rect.left, rect.top, paint);
	}
	
	public static float getStringHeight() {
		return mStringBitmapMap.get("0").getHeight();
	}
	
	public static float getStringWidth(String string) {
		float stringWidth = 0f;
		for(int i=0;i<string.length();i++) {
			if(string.charAt(i) == ' ') {
				stringWidth += SPACE_CHAR_DEFAULT_WIDTH;
			} else {
				Bitmap bitmap = mStringBitmapMap.get(string.substring(i, i+1));
				if(bitmap != null) {
					stringWidth += bitmap.getWidth();
				} else {
					Log.e("MultiHero", "Invalid bitmap!");
				}

			}
		}
		return stringWidth;
	}
}
