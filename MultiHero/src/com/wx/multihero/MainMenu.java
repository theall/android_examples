package com.wx.multihero;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class MainMenu {
	private Bitmap mBackgoundBitmap;
	private Bitmap mTitleBitmap;
	private Bitmap mTileBitmap;
	private RectF mScreenRect = new RectF();
	private static final String mMenuList[] = {"ADVENTURE MODE","VS MODE","OPTIONS","CREDITS","EXIT"};
	private static final float MENU_SPACE = 40.0f;
	private float mTileOffset = 0.0f;
	
	public MainMenu(int screenWidth, int screenHeight) {
		mScreenRect.left = 0;
		mScreenRect.right = screenWidth;
		mScreenRect.top = 0;
		mScreenRect.bottom = screenHeight;
	}
	public void loadAssets() {
		mBackgoundBitmap = Utils.loadBitmap(String.format("gfx/stuff/backg1.png"));
		mTitleBitmap = Utils.loadBitmap(String.format("gfx/stuff/title.png"));
		mTileBitmap = Utils.loadBitmap(String.format("gfx/stuff/bg.png"));
	}
	
	public void render(Canvas canvas, Paint paint) {
		canvas.drawBitmap(mBackgoundBitmap, null, mScreenRect, paint);
		
		float screenWidth = mScreenRect.width();
		float screenHeight = mScreenRect.height();
		
		// tiles
		int tileBitmapWidth = mTileBitmap.getWidth();
		int tileBitmapheight = mTileBitmap.getHeight();
		int tileColumns = (int)screenWidth / tileBitmapWidth + 2;
		int tileRows = (int)screenHeight / tileBitmapheight + 2;
		for(int i=0;i<tileRows;i++) {
			for(int j=0;j<tileColumns;j++) {
				canvas.drawBitmap(mTileBitmap, j*tileBitmapWidth-mTileOffset%tileBitmapWidth, i*tileBitmapheight-mTileOffset%tileBitmapheight, paint);
			}
		}
		mTileOffset += 1;
		
		// title
		float hCenter = (screenWidth-mTitleBitmap.getWidth())/2;
		float y = 72;
		canvas.drawBitmap(mTitleBitmap, hCenter, y, paint);
		
		// menu
		y += mTitleBitmap.getHeight();
		float remainHeight = mScreenRect.bottom - y;
		float menuHeight = BigFont.getStringHeight() + MENU_SPACE;
		float menusHeight = menuHeight * mMenuList.length - MENU_SPACE;
		y += (remainHeight - menusHeight) /2;
		for(int i=0;i<mMenuList.length;i++) {
			hCenter = (mScreenRect.width() - BigFont.getStringWidth(mMenuList[i])) / 2;
			BigFont.drawString(canvas, mMenuList[i], hCenter, y, paint);
			y += menuHeight;
		}
	}
}
