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

package com.wx.multihero.game.ui.scene;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.wx.multihero.game.base.AssetsLoader;
import com.wx.multihero.game.base.SceneType;
import com.wx.multihero.os.TouchState;

public class BackgroundScene extends BaseScene {        //背景场景
    private Bitmap mBackgoundBitmap;    //后台位置图
    private Bitmap mTileBitmap;         //平铺位置图
    private float mTileOffset = 0.0f;   //平铺偏移
    private boolean mStaticMode = false;//这里是静态方法

    public BackgroundScene(SceneType sceneType, Notify notify) {
        super(sceneType, notify);
    }

    public void setMode(boolean _static) {//下划线看不懂
        mStaticMode = _static;
    }

    public void loadAssets() {  //函数调用背景图片路径
        mBackgoundBitmap = AssetsLoader.getInstance().loadBitmap(String.format("gfx/ui/backg1.png"));
        mTileBitmap = AssetsLoader.getInstance().loadBitmap(String.format("gfx/ui/bg.png"));
    }

    public void render(Canvas canvas, Paint paint) {
    	if(mStaticMode) {
    		canvas.drawBitmap(mBackgoundBitmap, null, mScreenRect, paint);
    	} else {
    		 // tiles   瓦
            float screenWidth = mScreenRect.width();//这里写的长宽
            float screenHeight = mScreenRect.height();
            int tileBitmapWidth = mTileBitmap.getWidth();
            int tileBitmapheight = mTileBitmap.getHeight();
            int tileColumns = (int)screenWidth / tileBitmapWidth + 2;
            int tileRows = (int)screenHeight / tileBitmapheight + 2;
            for(int i=0;i<tileRows;i++) {
                for(int j=0;j<tileColumns;j++) {        //left左，，，top上
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
