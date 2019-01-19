package com.wx.multihero.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MotionEvent;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.SceneType;
import com.wx.multihero.base.Utils;
import com.wx.multihero.entity.Platform;
import com.wx.multihero.ui.component.ActorBoard;
import com.wx.multihero.ui.component.BackwardButton;
import com.wx.multihero.ui.component.CharacterPlatform;
import com.wx.multihero.ui.component.ForwardButton;
import com.wx.multihero.ui.component.GameModeBtton;
import com.wx.multihero.ui.component.LifeSwitchButton;
import com.wx.multihero.ui.component.TeamAttackButton;
import com.wx.multihero.ui.component.UseItemButton;
import com.wx.multihero.ui.widget.SelectedBorder;
import com.wx.multihero.ui.widget.TouchableWidget;
import com.wx.multihero.variability.Game;

import java.util.ArrayList;

public class CharacterChooseScene extends BaseScene implements TouchableWidget.Callback {
    private TeamAttackButton mBtnTeamAttack;
	private UseItemButton mBtnUseItems;
	private BackwardButton mBtnBack;
	private ForwardButton mBtnNext;
	private GameModeBtton mBtnMode;
	private LifeSwitchButton mBtnLifes;
	private BackgroundScene mBackgroundScene;
	private SelectedBorder mChatacterSelectBorder;
    private ArrayList<ActorBoard> mBoards = new ArrayList<ActorBoard>();
    private ArrayList<CharacterPlatform> mPlatforms = new ArrayList<CharacterPlatform>();
    private int mCurrentPlatformIndex;

    private static final int ID_ITEMS = 1;
    private static final int ID_NEXT = 2;
    private static final int ID_BACK = 3;
    private static final int ID_LIVES = 4;
    private static final int ID_TEAM_ATTACK = 5;
    private static final int ID_GAME_MODE = 6;
    private static final int ID_BOARD = 7;
    private static final int ID_PLATFORM = 8;
    private static final int BORAD_COUNT = 10;
    private static final int PLATFORM_COUNT = 10;
    private static final float PLATFORM_SPACE_HORIZONTAL = 10;
    private static final float PLATFORM_SPACE_VERTICAL = 10;

	public CharacterChooseScene(SceneType sceneType, Notify notify) {
		super(sceneType, notify);

		Game game = Game.getInstance();
        mBtnTeamAttack = new TeamAttackButton(ID_TEAM_ATTACK, null, this);
        mBtnTeamAttack.setBindValue(game.getAttackMate());
        mBtnUseItems = new UseItemButton(ID_ITEMS, null, this);
        mBtnUseItems.setBindValue(game.getUseItems());

        mBtnBack = new BackwardButton(ID_BACK, null, this);
        mBtnNext = new ForwardButton(ID_NEXT, null, this);

        mBtnLifes = new LifeSwitchButton(ID_LIVES, null, this);
        mBtnLifes.setBindValue(game.getLifes());

        mBtnMode = new GameModeBtton(ID_GAME_MODE, null, this);
        mBtnMode.setBindValue(game.getGameMode());

        mBackgroundScene = new BackgroundScene(SceneType.INVALID, null);
        for(int i=0;i<BORAD_COUNT;i++) {
            ActorBoard b = new ActorBoard(i<<16+ID_BOARD, null, this);
            mBoards.add(b);
        }
        for(int i=0;i<PLATFORM_COUNT;i++) {
            CharacterPlatform p = new CharacterPlatform(i<<16+ID_PLATFORM, null, this);
            mPlatforms.add(p);
        }

        mChatacterSelectBorder = new SelectedBorder(0, null);
        setCurrentPlatformIndex(0);
	}

	public void render(Canvas canvas, Paint paint) {
        mBackgroundScene.render(canvas, paint);

        mBtnTeamAttack.render(canvas, paint);
        mBtnUseItems.render(canvas, paint);
        mBtnMode.render(canvas, paint);
        mBtnLifes.render(canvas, paint);

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
	    mBtnTeamAttack.processTouchEvent(event);
	    mBtnMode.processTouchEvent(event);
	    mBtnUseItems.processTouchEvent(event);
	    mBtnLifes.processTouchEvent(event);
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
        mBtnTeamAttack.loadAssets();
        mBtnMode.loadAssets();
        mBtnLifes.loadAssets();
        mBtnUseItems.loadAssets();

        RectF r = new RectF();
        float buttonSpace = Utils.getRealWidth(20);
        float btnTotalWidth = mBtnTeamAttack.getBoundingRect().width() + buttonSpace +
                mBtnMode.getBoundingRect().width() + buttonSpace +
                mBtnLifes.getBoundingRect().width() + buttonSpace +
                mBtnUseItems.getBoundingRect().width();
        r.left = (mScreenRect.width() - btnTotalWidth) / 2;
        r.top = Utils.getRealHeight(20);
        r.bottom = r.top + mBtnTeamAttack.getBoundingRect().height();
        mBtnTeamAttack.moveTo(r.left, r.top);
        r.left += mBtnTeamAttack.getBoundingRect().width() + buttonSpace;
        mBtnMode.moveTo(r.left, r.top);
        r.left += mBtnMode.getBoundingRect().width() + buttonSpace;
        mBtnLifes.moveTo(r.left, r.top);
        r.left += mBtnLifes.getBoundingRect().width() + buttonSpace;
        mBtnUseItems.moveTo(r.left, r.top);

        for(int i=0;i<BORAD_COUNT;i++) {
            ActorBoard b = mBoards.get(i);
            b.loadAssets();
        }

        // compute total width of board list
        float boardWidth = mBoards.get(0).getBoundingRect().width();
        float actorBoardTotalWidth = BORAD_COUNT * boardWidth;
        r.left = (mScreenRect.width() - actorBoardTotalWidth) / 2;
        r.top = r.bottom + Utils.getRealHeight(10);
        for(int i=0;i<BORAD_COUNT;i++) {
            ActorBoard b = mBoards.get(i);
            b.moveTo(r.left, r.top);
            r.left += boardWidth;
        }

        // platforms
        for(CharacterPlatform cp : mPlatforms) {
            cp.loadAssets();
        }

        float platformHorizontalSpace = Utils.getRealWidth(PLATFORM_SPACE_HORIZONTAL);
        int platformsPerRow = PLATFORM_COUNT / 2;
        float platformTotalWidth = (mPlatforms.get(0).getBoundingRect().width() + platformHorizontalSpace) * platformsPerRow - platformHorizontalSpace;
        r.left = (mScreenRect.width() - platformTotalWidth) / 2;
        r.top += mBoards.get(0).getBoundingRect().height() + Utils.getRealHeight(PLATFORM_SPACE_VERTICAL);
        int index = 0;
        for(CharacterPlatform cp : mPlatforms) {
            cp.moveTo(r.left, r.top);
            r.left += cp.getBoundingRect().width() + platformHorizontalSpace;
            index++;
            if(index == platformsPerRow) {
                r.left = (mScreenRect.width() - platformTotalWidth) / 2;
                r.top += cp.getBoundingRect().height() + Utils.getRealHeight(PLATFORM_SPACE_VERTICAL);
                index = 0;
            }
        }

        mBtnBack.loadAssets();
        mBtnNext.loadAssets();
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

    public void setCurrentPlatformIndex(int index) {
        mCurrentPlatformIndex = index;
        if(mCurrentPlatformIndex<0 || mCurrentPlatformIndex>=mPlatforms.size())
            return;

        CharacterPlatform platform = mPlatforms.get(mCurrentPlatformIndex);
        mChatacterSelectBorder.setHost(platform);
    }
}
