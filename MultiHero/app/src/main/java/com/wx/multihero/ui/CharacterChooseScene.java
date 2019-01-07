package com.wx.multihero.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MotionEvent;

import com.wx.multihero.R;
import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.SceneType;
import com.wx.multihero.base.Utils;
import com.wx.multihero.ui.component.ActorBoard;
import com.wx.multihero.ui.component.CharacterPlatform;
import com.wx.multihero.ui.widget.Button;
import com.wx.multihero.ui.widget.SwitchMenu;
import com.wx.multihero.ui.widget.TouchableWidget;

import java.util.ArrayList;

public class CharacterChooseScene extends BaseScene implements TouchableWidget.Callback {
    private Button mBtnTeamAttack;
	private Button mBtnItems;
	private Button mBtnBack;
	private Button mBtnNext;
	private Button mBtnMode;
	private SwitchMenu mSMLives;
	private BackgroundScene mBackgroundScene;
    private ArrayList<ActorBoard> mBoards = new ArrayList<ActorBoard>();
    private ArrayList<CharacterPlatform> mPlatforms = new ArrayList<CharacterPlatform>();

    private final int ID_ITEMS = 1;
    private final int ID_NEXT = 2;
    private final int ID_BACK = 3;
    private final int ID_LIVES = 4;
    private final int ID_TEAM_ATTACK = 5;
    private final int ID_GAME_MODE = 6;
    private final int ID_BOARD = 7;
    private final int ID_PLATFORM = 8;
    private final int BORAD_COUNT = 10;
    private final int PLATFORM_COUNT = 4;

	public CharacterChooseScene(SceneType sceneType, Notify notify) {
		super(sceneType, notify);
        mBtnTeamAttack = new Button(ID_TEAM_ATTACK, null, this);
        mBtnItems = new Button(ID_ITEMS, null, this);
        mBtnBack = new Button(ID_BACK, null, this);
        mBtnNext = new Button(ID_NEXT, null, this);
        mSMLives = new SwitchMenu(ID_LIVES, null, this);
        mBtnMode = new Button(ID_GAME_MODE, null, this);
        mBackgroundScene = new BackgroundScene(SceneType.INVALID, null);
        for(int i=0;i<BORAD_COUNT;i++) {
            ActorBoard b = new ActorBoard(i<<16+ID_BOARD, null, this);
            mBoards.add(b);
        }
        for(int i=0;i<PLATFORM_COUNT;i++) {
            CharacterPlatform p = new CharacterPlatform(i<<16+ID_PLATFORM, null, this);
            mPlatforms.add(p);
        }
	}

	public void render(Canvas canvas, Paint paint) {
        mBackgroundScene.render(canvas, paint);

        mBtnTeamAttack.render(canvas, paint);
        mBtnItems.render(canvas, paint);
        mBtnBack.render(canvas, paint);
        mBtnNext.render(canvas, paint);
        mBtnMode.render(canvas, paint);
        mSMLives.render(canvas, paint);

        for(ActorBoard ab : mBoards) {
            ab.render(canvas, paint);
        }
        for(CharacterPlatform cp : mPlatforms) {
            cp.render(canvas, paint);
        }
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

        RectF r = new RectF();
        r.left = Utils.getRealWidth(10);
        r.top = Utils.getRealHeight(20);

        Bitmap buttonBackground = AssetsLoader.loadBitmap("gfx/ui/but_ta.png");
        r.bottom = r.top + buttonBackground.getHeight();
        mBtnTeamAttack.setBitmaps(buttonBackground);
        mBtnTeamAttack.setText(Utils.getStringFromResourceId(R.string.team_attack));
        mBtnTeamAttack.moveTo(r.left, r.top);

        r.left += buttonBackground.getWidth() + Utils.getRealWidth(20);
        mBtnMode.setBitmaps(buttonBackground);
        mBtnMode.setText(Utils.getStringFromResourceId(R.string.game_mode));
        mBtnMode.moveTo(r.left, r.top);

        Bitmap arrow1 = AssetsLoader.loadBitmap("gfx/ui/arrow1.png");
        Bitmap arrow2 = AssetsLoader.loadBitmap("gfx/ui/arrow2.png");
        mSMLives.setBitmaps(buttonBackground, arrow1, arrow2);
        mSMLives.setText(Utils.getStringFromResourceId(R.string.lives));
        r.left += buttonBackground.getWidth() + Utils.getRealWidth(20);
        mSMLives.moveTo(r.left, r.top);

        mBtnItems.setBitmaps(buttonBackground);
        mBtnItems.setText(Utils.getStringFromResourceId(R.string.items));
        r.left += buttonBackground.getWidth() + Utils.getRealWidth(20);
        mBtnItems.moveTo(r.left, r.top);

        Bitmap boardBackground = AssetsLoader.loadBitmap("gfx/ui/board3.png");
        float boardWidth = boardBackground.getWidth();
        float margin = Utils.getRealWidth(20);
        r.left = margin;
        r.top = r.bottom + Utils.getRealHeight(10);
        for(int i=0;i<BORAD_COUNT;i++) {
            ActorBoard b = mBoards.get(i);
            Bitmap foreground = AssetsLoader.loadBitmap(String.format("gfx/character/%d/zwalk0.png", i+1));
            b.setBitmaps(boardBackground, foreground);
            b.moveTo(r.left, r.top);
            r.left += boardBackground.getWidth();
            if(r.left+margin >mScreenRect.width()) {
                r.left = margin;
                r.top += boardBackground.getHeight() + Utils.getRealHeight(5);
            }
        }

        // platforms
        r.top += Utils.getRealHeight(10);
        for(CharacterPlatform cp : mPlatforms) {
            cp.loadAssets();
            cp.moveTo(r.left, r.top);
        }


        Bitmap backBitmap = AssetsLoader.loadBitmap("gfx/ui/backward.png");
        r.left = Utils.getRealWidth(10);
        r.top = mScreenRect.bottom - backBitmap.getHeight() - Utils.getRealHeight(40);
        r.right = r.left + backBitmap.getWidth();
        r.bottom = r.top + backBitmap.getHeight();
        mBtnBack.setBoundingRect(r);
        mBtnBack.setBitmaps(backBitmap, backBitmap);

        Bitmap nextBitmap = AssetsLoader.loadBitmap("gfx/ui/forward.png");
        r.offsetTo(mScreenRect.right - Utils.getRealWidth(10) - nextBitmap.getWidth(), r.top);
        mBtnNext.setBoundingRect(r);
        mBtnNext.setBitmaps(nextBitmap, nextBitmap);
	}

    public void selected(int id, Bundle parameters) {
        if(id == ID_BACK) {
            mNotify.back(SceneType.CHARACTER);
        } else if(id == ID_NEXT) {
            mNotify.next(SceneType.CHARACTER, 0);
        } else if(id== ID_TEAM_ATTACK) {

        } else if(id== ID_GAME_MODE) {

        } else if(id== ID_LIVES) {

        } else if(id== ID_ITEMS) {

        }
    }
}
