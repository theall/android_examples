package com.wx.multihero.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.BigFont;
import com.wx.multihero.base.SceneType;
import com.wx.multihero.base.SoundPlayer;
import com.wx.multihero.base.Utils;
import com.wx.multihero.ui.widget.MenuItem;
import com.wx.multihero.ui.widget.PictureItem;

import java.util.ArrayList;

public class TitleScene extends BaseScene implements MenuItem.Callback {
	private PictureItem mTitlePicture;
	private static final String mMenuList[] = {"ADVENTURE MODE","VS MODE","OPTIONS","CREDITS","EXIT"};

    public enum MenuID {
	    ADV,
        VS,
        OPTION,
        CREDIT,
        EXIT
    }
    
	private static final float MENU_DEFAULT_SPACE = 20.0f;
	private ArrayList<MenuItem> mMenuItems = new ArrayList<MenuItem>();

	public TitleScene(SceneType sceneType, Notify notify) {
		super(sceneType, notify);
		mBackgroundSound = AssetsLoader.getInstance().loadSound("sound/title.mp3");
	}

	public void loadAssets() {
		float screenWidth = mScreenRect.width();
		float screenHeight = mScreenRect.height();
		float titleStubHeight = screenHeight*(1-Utils.GOLD_LINE);

		if(mTitlePicture == null)
        {
            mTitlePicture = new PictureItem(0,
                    new RectF(0,0,screenWidth,titleStubHeight),
                    AssetsLoader.getInstance().loadBitmap("gfx/ui/title.png"));
            mTitlePicture.center();
        }
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
		int soundId = AssetsLoader.getInstance().loadSound("sound/blocked.mp3");
		for(int i=0;i<5;i++) {
			MenuItem mi = new MenuItem(i, rect, mMenuList[i], this);
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

	public boolean processTouchEvent(MotionEvent event) {
		for(MenuItem mi : mMenuItems) {
			mi.processTouchEvent(event);
		}
		Log.i("event", event.toString());
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
