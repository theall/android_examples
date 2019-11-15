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

import com.wx.multihero.game.base.AssetsLoader;
import com.wx.multihero.game.base.SceneType;
import com.wx.multihero.game.base.Utils;
import com.wx.multihero.game.entity.Map;
import com.wx.multihero.game.entity.MapSet;
import com.wx.multihero.game.entity.Mod;
import com.wx.multihero.game.entity.ModManager;
import com.wx.multihero.game.ui.component.BackwardButton;
import com.wx.multihero.game.ui.component.ForwardButton;
import com.wx.multihero.game.ui.component.ModSwitchButton;
import com.wx.multihero.game.ui.widget.Button;
import com.wx.multihero.game.ui.widget.PictureItem;
import com.wx.multihero.game.ui.widget.BitmapText;
import com.wx.multihero.game.ui.widget.SelectedBorder;
import com.wx.multihero.game.ui.widget.TouchableWidget;
import com.wx.multihero.os.TouchState;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MapChooseScene extends BaseScene implements TouchableWidget.Callback { //地图选择场景
	private ArrayList<PictureItem> mMapThumbList = new ArrayList<PictureItem>();
    private BitmapText mModName;
    private SelectedBorder mSelectBorder;
    private BackwardButton mBtnBack;
    private ForwardButton mBtnNext;
    private BackgroundScene mBackgroundScene;
    private ModSwitchButton mBtnModSwitch;
    private Button mBtnStageSelect;
    private MapSet mCurrentMapSet;
    private static float SPACE_COLUMN = 20;
    private static float SPACE_ROW = 10;
    private final int ID_BACK = 1;
    private final int ID_NEXT = 2;
    private final int ID_MOD_SHIT = 3;
    private final int ID_MAP = 4;
	public MapChooseScene(SceneType sceneType, Notify notify) {
		super(sceneType, notify);

        mModName = new BitmapText(null);
        mBtnBack = new BackwardButton(this, null);
        mBtnBack.setTag(ID_BACK);
        mBtnNext = new ForwardButton(this, null);
        mBtnNext.setTag(ID_NEXT);
        mBackgroundScene = new BackgroundScene(SceneType.INVALID, null);
        mBtnModSwitch = new ModSwitchButton(this,null);
        mBtnModSwitch.setTag(ID_MOD_SHIT);
        mBtnModSwitch.setBindValue(ModManager.getInstance());
        mSelectBorder = new SelectedBorder(null);
        mBtnStageSelect = new Button(null);
        mCurrentMapSet = null;

        SPACE_COLUMN = Utils.getRealWidth(20);
        SPACE_ROW = Utils.getRealHeight(10);
	}

	private void setMapSet(MapSet mapSet) {
	    if(mapSet == null)
	        return;

        mBtnModSwitch.setText(mapSet.getName());

        mCurrentMapSet = mapSet;
        mMapThumbList.clear();
        if(mapSet.getMapCount() < 1)
            return;

        float screenWidth = mScreenRect.width();
        float screenHeight = mScreenRect.height();
        float validWidth = screenWidth * Utils.GOLD_LINE;
        float validHeight = screenHeight * Utils.GOLD_LINE;
        float spaceColumn = SPACE_COLUMN;
        float spaceRow = SPACE_ROW;
        float bmpWidth = 0;
        for(Map map : mapSet.getMapList()) {
            Bitmap thumb = map.getThumbBitmap();
            if (thumb != null) {
                bmpWidth = thumb.getWidth();
                break;
            }
        }
        if(bmpWidth == 0) {
            Log.e("MultiHero","Can not find any thumb bitmaps in map set!");
            return;
        }
        // precompute the real width  预计实际宽度
        int colsPerRow = Math.round((validWidth+spaceColumn) / (bmpWidth+spaceColumn));
        float widthPerRow = colsPerRow * (bmpWidth+spaceColumn) - spaceColumn;
        float leftBound = (screenWidth - widthPerRow) / 2;
        float rightBound = leftBound + widthPerRow;
        int col = 0;
        int row = 0;
        RectF r = new RectF();
        r.left = leftBound;
        r.top = (screenHeight - validHeight) / 2;
        for(Map map : mapSet.getMapList()) {
            Bitmap thumb = map.getThumbBitmap();
            if(thumb == null)
                continue;
            r.right = r.left + thumb.getWidth();
            r.bottom = r.top + thumb.getHeight();
            PictureItem pi = new PictureItem(null);
            pi.setTag(ID_MAP);
            pi.setBoundingRect(r);
            pi.setBitmap(thumb);
            r.left = r.right + spaceColumn;
            if(r.left > rightBound) {
                r.left = leftBound;
                r.top += thumb.getHeight() + spaceRow;
                col = 0;
                row++;
            } else {
                col++;
            }
            mMapThumbList.add(pi);
        }

        int currentMap = mapSet.getCurrentMapIndex();
        PictureItem pictureItem = mMapThumbList.get(currentMap);
        if(pictureItem != null) {
            mSelectBorder.setHost(pictureItem);
        } else {
            mSelectBorder.setHost(null);
        }
    }

	public void render(Canvas canvas, Paint paint) {
        mBackgroundScene.render(canvas, paint);
		for(PictureItem pi : mMapThumbList) {
		    pi.render(canvas, paint);
        }
        mSelectBorder.render(canvas, paint);
        mBtnModSwitch.render(canvas, paint);
        mBtnBack.render(canvas, paint);
        mBtnNext.render(canvas, paint);
	}

	public boolean processTouchState(TouchState touchState) {
	    float x = touchState.getX();
	    float y = touchState.getY();
	    int index = 0;
	    for(PictureItem pi : mMapThumbList) {
	        if(pi.touchTest(x, y)) {
                mCurrentMapSet.setCurrentMapIndex(index);
	            mSelectBorder.setHost(pi);
	            return true;
            }
            index++;
        }
	    mBtnModSwitch.processTouchState(touchState);
        mBtnBack.processTouchState(touchState);
        mBtnNext.processTouchState(touchState);
		return false;
	}

	public void shiftIn() {

	}

	public void shiftOut() {

	}

    public void loadAssets() {
	    mBackgroundScene.loadAssets();
        mBtnModSwitch.loadAssets();
        mBtnModSwitch.offset((mScreenRect.width()-mBtnModSwitch.getBoundingRect().width())/2, Utils.getRealHeight(40));

        RectF rect = mBtnModSwitch.getBoundingRect();
        mBtnStageSelect.setBitmap(AssetsLoader.getInstance().loadBitmap("gfx/ui/but_start.png"));
        mBtnStageSelect.moveTo(rect.left-mBtnStageSelect.getBoundingRect().width()-Utils.getRealWidth(20), rect.top);
        mBtnStageSelect.setText("SELECT STAGE");

        ModManager modMan = ModManager.getInstance();
        modMan.load();
        refresh();

        mBtnBack.loadAssets();
        mBtnNext.loadAssets();
    }

    public void refresh() {
        ModManager modMan = ModManager.getInstance();
        Mod mod = modMan.getCurrentMod();
        mModName.setText(mod.getName());
        if(mod != null) {
            setMapSet(mod.getVsMaps());
        }
    }

    public void selected(int id, Bundle parameters) {
        if(mNotify != null) {
            if(id == ID_BACK) {
                mNotify.back(mSceneType);
            } else if(id == ID_NEXT) {
                mNotify.next(mSceneType, 0);
            } else if(id == ID_MOD_SHIT) {
                refresh();
            }
        }
    }

    public Map getSelectedMap() {
	    if(mCurrentMapSet == null)
	        return null;
	    return mCurrentMapSet.getCurrentMap();
    }

    public void step() {

    }
}
