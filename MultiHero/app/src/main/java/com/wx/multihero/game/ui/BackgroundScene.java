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

package com.wx.multihero.game.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.wx.multihero.game.base.AssetsLoader;
import com.wx.multihero.game.base.SceneType;
import com.wx.multihero.os.TouchState;

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
        mBackgoundBitmap = AssetsLoader.getInstance().loadBitmap(String.format("gfx/ui/backg1.png"));
        mTileBitmap = AssetsLoader.getInstance().loadBitmap(String.format("gfx/ui/bg.png"));
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

    public boolean processTouchState(TouchState touchState) {
        return false;
    }

    public void shiftIn() {

    }

    public void shiftOut() {

    }

    public void step() {

    }
}
