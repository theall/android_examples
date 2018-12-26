package com.wx.multihero.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.SceneType;

public class BackgroundScene extends BaseScene {
    private Bitmap mBackgoundBitmap;
    private Bitmap mTileBitmap;
    private float mTileOffset = 0.0f;
    private boolean mStaticMode = false;

    public BackgroundScene(SceneType sceneType, Notify notify) {
        super(sceneType, notify);
    }

    public void setMode(boolean _static) {
        mStaticMode = _static;
    }

    public void loadAssets() {
        mBackgoundBitmap = AssetsLoader.loadBitmap(String.format("gfx/stuff/backg1.png"));
        mTileBitmap = AssetsLoader.loadBitmap(String.format("gfx/stuff/bg.png"));
    }

    public void render(Canvas canvas, Paint paint) {
    	if(mStaticMode) {
    		canvas.drawBitmap(mBackgoundBitmap, null, mScreenRect, paint);
    	} else {
    		 // tiles
            float screenWidth = mScreenRect.width();
            float screenHeight = mScreenRect.height();
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
    	}
    }

    public int processTouchEvent(MotionEvent event) {
        return 0;
    }

    public void shiftIn() {

    }

    public void shiftOut() {

    }
}
