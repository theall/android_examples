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
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;

import com.wx.multihero.game.base.AssetsLoader;
import com.wx.multihero.game.base.BigFont;
import com.wx.multihero.game.base.SceneType;
import com.wx.multihero.game.base.Utils;
import com.wx.multihero.game.ui.widget.MenuItem;
import com.wx.multihero.game.ui.widget.PictureItem;
import com.wx.multihero.os.TouchState;

import java.util.ArrayList;

//implements 容器用于存放底层标题
public class TitleScene extends BaseScene implements MenuItem.Callback {    //标题类
	private PictureItem mTitlePicture;
	private static final String mMenuList[] = {"ADVENTURE MODE","VS MODE","OPTIONS","CREDITS","EXIT"};

	public void step() {

	}

	public enum MenuID {    //枚举用法
	    ADV,
        VS,
        OPTION,
        CREDIT,
        EXIT
    }
    
	private static final float MENU_DEFAULT_SPACE = 20.0f; //标题居中
	private ArrayList<MenuItem> mMenuItems = new ArrayList<MenuItem>();

	public TitleScene(SceneType sceneType, Notify notify) {
		super(sceneType, notify);
		mBackgroundSound = AssetsLoader.getInstance().loadSound("title");
	}

	public void loadAssets() {
		float screenWidth = mScreenRect.width();
		float screenHeight = mScreenRect.height();
		float titleStubHeight = screenHeight*(1-Utils.GOLD_LINE); //黄金分割线

		if(mTitlePicture == null)
        {
            mTitlePicture = new PictureItem(null);
            mTitlePicture.setBoundingRect(new RectF(0,0, screenWidth, titleStubHeight));//高宽
            mTitlePicture.setBitmap(AssetsLoader.getInstance().loadBitmap("gfx/ui/title.png"));
            mTitlePicture.center();
        } //上面写的是字母图片的大小位子
		float remainHeight = screenHeight - titleStubHeight;
		float menuTotalHeight = remainHeight / mMenuList.length;
		float menuActualSpace = MENU_DEFAULT_SPACE * screenHeight / Utils.BASE_SCREEN_HEIGHT;
		float memuHeight = menuTotalHeight - menuActualSpace;

		float maxStringWidth = 0f;
		for(int i=0;i<mMenuList.length;i++) {
			float stringDrawWidth = BigFont.getStringWidth(mMenuList[i]);
			if(stringDrawWidth > maxStringWidth)
				maxStringWidth = stringDrawWidth;
		}
		maxStringWidth /= Utils.GOLD_LINE;
		mMenuItems.clear();
		RectF rect = new RectF();
		rect.left = (screenWidth-maxStringWidth)/2;
		rect.right = rect.left + maxStringWidth;
		rect.top = titleStubHeight;
		rect.bottom = rect.top + memuHeight;
		int soundId = AssetsLoader.getInstance().loadSound("blocked");
		for(int i=0;i<5;i++) {
			MenuItem mi = new MenuItem(mMenuList[i], this, null);
			mi.setTag(i);
			mi.setBoundingRect(rect);
			mi.setTouchedSoundEffect(soundId);
			rect.offset(0, menuTotalHeight);
			mMenuItems.add(mi);
		}
	}

	public void selected(int id, Bundle parameters) {
		if(mNotify != null) {
		    mNotify.next(mSceneType, id);
        }
	}

	public boolean processTouchState(TouchState touchState) { //对字符串进行触摸判断
		for(MenuItem mi : mMenuItems) {
			mi.processTouchState(touchState);
		}
		return false;
	}

	public void render(Canvas canvas, Paint paint) {
		// title
		mTitlePicture.render(canvas, paint);
		
		// menu
		for(MenuItem mi : mMenuItems) {
			mi.render(canvas, paint);
		}
	}

	public void shiftIn() {
        playBackgoundSound(false);
	}

	public void shiftOut() {

	}
}
