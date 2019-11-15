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

public class LoadingScene extends BaseScene implements AssetsLoader.LoadNotify {      //加载场景
    private ColorProgressBar mProgressBar;//设置步进栏颜色
    private PrimitiveText mLoadingText; //准备界面的主题

    public LoadingScene(SceneType sceneType, Notify notify) {  //场景类型，通告上级
        super(sceneType, notify);
        mLoadingText = new PrimitiveText(null);//P是ML的类，这里是将Pr类NEW出来ML调用构造函数
        mLoadingText.setText(Utils.getStringFromResourceId(R.string.loading));

        float pbWidth = mScreenRect.width() * Utils.GOLD_LINE;
        float pbHeight = pbWidth / 20;
        RectF rect = new RectF(0, 0, pbWidth, pbHeight);
        rect.offset((mScreenRect.width() - pbWidth) / 2, (mScreenRect.height() - pbHeight) / 2);
        mProgressBar = new ColorProgressBar(null); //new出颜色渐进栏对象方便调用
        mProgressBar.setBoundingRect(rect); //设置边界Rect
        mProgressBar.setColor(Color.GREEN, Color.RED);//设置步进栏颜色
        mLoadingText.setColor(Color.WHITE); //设置准备进入的背景颜色
        mLoadingText.moveTo((mScreenRect.width()-mLoadingText.getBoundingRect().width())/2,
                rect.top-mLoadingText.getBoundingRect().height()-Utils.getRealHeight(10)); //移动
    }

    public void render(Canvas canvas, Paint paint) {        //render使什么 paint绘图 Canvas 描绘
        canvas.drawColor(Color.BLACK);              //这个render构造方法写的是调用P，C方法，进行描绘界面颜色
        mLoadingText.render(canvas, paint);
        mProgressBar.render(canvas, paint);
    }

    public boolean processTouchState(TouchState touchState) {
        return false;
    }

    public void onProgress(int loadedSize, int totalSize) { //触摸状态
        mProgressBar.setProgress((float)loadedSize / totalSize);
        if(loadedSize==totalSize && mNotify!=null) {
            mNotify.next(mSceneType, 0);
        }
    }

    public void shiftIn() { //移位

    }

    public void shiftOut() { //转换

    }

    public void loadAssets() {  //加载资源

    }

    public void step() {  //递进

    }
}
