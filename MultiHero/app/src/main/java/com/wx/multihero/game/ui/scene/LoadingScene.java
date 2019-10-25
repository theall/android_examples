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

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wx.multihero.R;
import com.wx.multihero.game.base.AssetsLoader;
import com.wx.multihero.game.base.SceneType;
import com.wx.multihero.game.base.Utils;
import com.wx.multihero.game.ui.widget.ColorProgressBar;
import com.wx.multihero.game.ui.widget.PrimitiveText;
import com.wx.multihero.os.TouchState;

public class LoadingScene extends BaseScene implements AssetsLoader.LoadNotify {
    private ColorProgressBar mProgressBar;
    private PrimitiveText mLoadingText;

    public LoadingScene(SceneType sceneType, Notify notify) {
        super(sceneType, notify);
        mLoadingText = new PrimitiveText(null);
        mLoadingText.setText(Utils.getStringFromResourceId(R.string.loading));

        float pbWidth = mScreenRect.width() * Utils.GOLD_LINE;
        float pbHeight = pbWidth / 20;
        RectF rect = new RectF(0, 0, pbWidth, pbHeight);
        rect.offset((mScreenRect.width() - pbWidth) / 2, (mScreenRect.height() - pbHeight) / 2);
        mProgressBar = new ColorProgressBar(null);
        mProgressBar.setBoundingRect(rect);
        mProgressBar.setColor(Color.GREEN, Color.RED);
        mLoadingText.setColor(Color.WHITE);
        mLoadingText.moveTo((mScreenRect.width()-mLoadingText.getBoundingRect().width())/2,
                rect.top-mLoadingText.getBoundingRect().height()-Utils.getRealHeight(10));
    }

    public void render(Canvas canvas, Paint paint) {
        canvas.drawColor(Color.BLACK);
        mLoadingText.render(canvas, paint);
        mProgressBar.render(canvas, paint);
    }

    public boolean processTouchState(TouchState touchState) {
        return false;
    }

    public void onProgress(int loadedSize, int totalSize) {
        mProgressBar.setProgress((float)loadedSize / totalSize);
        if(loadedSize==totalSize && mNotify!=null) {
            mNotify.next(mSceneType, 0);
        }
    }

    public void shiftIn() {

    }

    public void shiftOut() {

    }

    public void loadAssets() {

    }

    public void step() {

    }
}
