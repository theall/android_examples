package com.wx.multihero.ui;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.SceneType;
import com.wx.multihero.base.Utils;
import com.wx.multihero.entity.Map;
import com.wx.multihero.entity.MapSet;
import com.wx.multihero.entity.Mod;
import com.wx.multihero.entity.ModManager;
import com.wx.multihero.ui.component.BackwardButton;
import com.wx.multihero.ui.component.ForwardButton;
import com.wx.multihero.ui.component.ModSwitchButton;
import com.wx.multihero.ui.widget.Button;
import com.wx.multihero.ui.widget.PictureItem;
import com.wx.multihero.ui.widget.BitmapText;
import com.wx.multihero.ui.widget.TouchableWidget;

import android.app.UiModeManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MotionEvent;

import java.util.ArrayList;

public class MapChooseScene extends BaseScene implements TouchableWidget.Callback {
	private ArrayList<PictureItem> mMapThumbList = new ArrayList<PictureItem>();
    private BitmapText mModName;
    private BackwardButton mBtnBack;
    private ForwardButton mBtnNext;
    private BackgroundScene mBackgroundScene;
    private ModSwitchButton mBtnModSwitch;
    private static float SPACE_COLUMN = 20;
    private static float SPACE_ROW = 10;
    private final int ID_BACK = 1;
    private final int ID_NEXT = 2;
    private final int ID_MOD_SHIT = 3;
	public MapChooseScene(SceneType sceneType, Notify notify) {
		super(sceneType, notify);

        mModName = new BitmapText(0, new RectF());
        mBtnBack = new BackwardButton(ID_BACK, null, this);
        mBtnNext = new ForwardButton(ID_NEXT, null, this);
        mBackgroundScene = new BackgroundScene(SceneType.INVALID, null);
        mBtnModSwitch = new ModSwitchButton(ID_MOD_SHIT, null, this);
	}

	private void setMapSet(MapSet mapSet) {
	    if(mapSet == null)
	        return;

        mMapThumbList.clear();
        if(mapSet.getMapCount() < 1)
            return;

        float screenWidth = mScreenRect.width();
        float screenHeight = mScreenRect.height();
        float validWidth = screenWidth * Utils.GOLD_LINE;
        float validHeight = screenHeight * Utils.GOLD_LINE;
        float spaceColumn = SPACE_COLUMN * Utils.BASE_SCREEN_WIDTH / screenWidth;
        float spaceRow = SPACE_ROW * Utils.BASE_SCREEN_HEIGHT / screenHeight;
        float leftBound = (screenWidth - validWidth) / 2;
        float rightBound = leftBound + validWidth;
        int col = 0;
        int row = 0;
        RectF r = new RectF();
        r.left = leftBound;
        r.top = (screenHeight - validHeight) / 2;
        for(Map map : mapSet.getMapList()) {
            Bitmap thumb = map.getThumbBitmap();
            if(thumb == null)
                continue;
            float bmpWidth = thumb.getWidth();
            r.right = r.left + bmpWidth;
            r.bottom = r.top + thumb.getHeight();
            PictureItem pi = new PictureItem(row<<16+col, r, thumb);
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
    }

	public void render(Canvas canvas, Paint paint) {
        mBackgroundScene.render(canvas, paint);
		for(PictureItem pi : mMapThumbList) {
		    pi.render(canvas, paint);
        }
        mBtnModSwitch.render(canvas, paint);
        mBtnBack.render(canvas, paint);
        mBtnNext.render(canvas, paint);
	}

	public int processTouchEvent(MotionEvent event) {
        mBtnBack.processTouchEvent(event);
        mBtnNext.processTouchEvent(event);
		return 0;
	}

	public void shiftIn() {

	}

	public void shiftOut() {

	}

    public void loadAssets() {
	    mBackgroundScene.loadAssets();
        mBtnModSwitch.loadAssets();
        mBtnModSwitch.offset((mScreenRect.width()-mBtnModSwitch.getBoundingRect().width())/2, Utils.getRealHeight(10));

        ModManager modMan = ModManager.getInstance();
        modMan.load();
        Mod mod = modMan.getMod(1);
        mModName.setText(mod.getName());
        if(mod != null) {
            setMapSet(mod.getVsMaps());
        }

        mBtnBack.loadAssets();
        mBtnNext.loadAssets();
    }

    public void selected(int id, Bundle parameters) {
        if(mNotify != null) {
            if(id == ID_BACK) {
                mNotify.back(mSceneType);
            } else {
                mNotify.next(mSceneType, 0);
            }
        }
    }
}
