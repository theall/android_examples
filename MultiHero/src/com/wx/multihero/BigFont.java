package com.wx.multihero;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

@SuppressLint("DefaultLocale") 
public class BigFont {
	private static Map<String, Bitmap> mStringBitmapMap = new HashMap<String, Bitmap>();
	private static final String charList = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final int SPACE_CHAR_WIDTH = 16;
	
	public BigFont() {
		
	}
	
	public void loadAssets() {
		for(int i=0;i<36;i++) {
			Bitmap bitmap = Utils.loadBitmap(String.format("gfx/fonts/l1_%d.png", i));
			mStringBitmapMap.put(charList.substring(i, i+1), bitmap);
		}
	}
	
	public static void drawString(Canvas canvas, String string, float left, float top, Paint paint) {
		for(int i=0;i<string.length();i++) {
			if(string.charAt(i) == ' ')
			{
				left += SPACE_CHAR_WIDTH;
			} else {
				Bitmap bitmap = mStringBitmapMap.get(string.substring(i, i+1));
				canvas.drawBitmap(bitmap, left, top, paint);
				left += bitmap.getWidth();
			}
			
		}
	}
	
	public static float getStringHeight() {
		return mStringBitmapMap.get("0").getHeight();
	}
	
	public static float getStringWidth(String string) {
		float stringWidth = 0f;
		for(int i=0;i<string.length();i++) {
			if(string.charAt(i) == ' ') {
				stringWidth += SPACE_CHAR_WIDTH;
			} else {
				Bitmap bitmap = mStringBitmapMap.get(string.substring(i, i+1));
				stringWidth += bitmap.getWidth();
			}
		}
		return stringWidth;
	}
}
