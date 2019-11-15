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

import com.wx.multihero.game.base.SceneType;
import com.wx.multihero.game.base.Utils;
import com.wx.multihero.game.entity.CharacterManager;
import com.wx.multihero.game.ui.component.ActorBoard;
import com.wx.multihero.game.ui.component.BackwardButton;
import com.wx.multihero.game.ui.component.CharacterPlatform;
import com.wx.multihero.game.ui.component.ForwardButton;
import com.wx.multihero.game.ui.component.GameModeButton;
import com.wx.multihero.game.ui.component.LifeSwitchButton;
import com.wx.multihero.game.ui.component.TeamAttackButton;
import com.wx.multihero.game.ui.component.UseItemButton;
import com.wx.multihero.game.ui.widget.SelectedBorder;
import com.wx.multihero.game.ui.widget.TouchableWidget;
import com.wx.multihero.game.variability.Game;
import com.wx.multihero.game.variability.hero.HeroFactory;
import com.wx.multihero.game.variability.ui.Player;
import com.wx.multihero.os.TouchState;

import java.util.ArrayList;

public class CharacterChooseScene extends BaseScene implements TouchableWidget.Callback {   //角色选择场景
    private TeamAttackButton mBtnTeamAttack;
	private UseItemButton mBtnUseItems;
	private BackwardButton mBtnBack;
	private ForwardButton mBtnNext;
	private GameModeButton mBtnMode;
	private LifeSwitchButton mBtnLifes;
	private BackgroundScene mBackgroundScene;
	private SelectedBorder mSelectBorder;
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
    private static final int BOARD_COUNT = 12;
    private static final int PLATFORM_COUNT = 10;
    private static final float PLATFORM_SPACE_HORIZONTAL = 10;
    private static final float PLATFORM_SPACE_VERTICAL = 10;

	public CharacterChooseScene(SceneType sceneType, Notify notify) {
		super(sceneType, notify);

		Game game = Game.getInstance();
        mBtnTeamAttack = new TeamAttackButton(this, null);
        mBtnTeamAttack.setTag(ID_TEAM_ATTACK);
        mBtnTeamAttack.setBindValue(game.getAttackMate());
        mBtnUseItems = new UseItemButton(this, null);
        mBtnUseItems.setTag(ID_ITEMS);
        mBtnUseItems.setBindValue(game.getUseItems());

        mBtnBack = new BackwardButton(this, null);
        mBtnBack.setTag(ID_BACK);
        mBtnNext = new ForwardButton(this, null);
        mBtnNext.setTag(ID_NEXT);

        mBtnLifes = new LifeSwitchButton(this, null);
        mBtnLifes.setTag(ID_LIVES);
        mBtnLifes.setBindValue(game.getLifes());

        mBtnMode = new GameModeButton(this, null);
        mBtnMode.setTag(ID_GAME_MODE);
        mBtnMode.setBindValue(game.getGameMode());

        mBackgroundScene = new BackgroundScene(SceneType.INVALID, null);
        for(int i = 0; i< BOARD_COUNT; i++) {
            ActorBoard b = new ActorBoard(this, null);
            b.setTag((i<<16)+ID_BOARD);
            mBoards.add(b);
        }
        for(int i=0;i<PLATFORM_COUNT;i++) {
            CharacterPlatform p = new CharacterPlatform(null);
            p.setTag(i<<16+ID_PLATFORM);
            mPlatforms.add(p);
        }

        mSelectBorder = new SelectedBorder(null);
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
        mSelectBorder.render(canvas, paint);

        mBtnBack.render(canvas, paint);
        mBtnNext.render(canvas, paint);
	}

	public boolean processTouchState(TouchState touchState) {
	    // process select border 处理选择边框
        float x = touchState.getX();
        float y = touchState.getY();
        CharacterPlatform host = (CharacterPlatform)mSelectBorder.getHost();
        for(CharacterPlatform cp : mPlatforms) {
            if(cp.touchTest(x, y)) {
                if(host == cp) {
                    cp.processTouchState(touchState);
                } else {
                    mSelectBorder.setHost(cp);
                }
                return true;
            }
        }
        for(ActorBoard ab : mBoards) {
            ab.processTouchState(touchState);
        }
	    mBtnTeamAttack.processTouchState(touchState);
	    mBtnMode.processTouchState(touchState);
	    mBtnUseItems.processTouchState(touchState);
	    mBtnLifes.processTouchState(touchState);
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

        CharacterManager characterManager = CharacterManager.getInstance();
        characterManager.loadAssets();
        try {
            HeroFactory.create(characterManager.getCharacterList());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        Game.getInstance().getPlayer(0).setHero(HeroFactory.create(HeroFactory.ID.RYU));
        int validCharacterCount = Math.min(BOARD_COUNT, characterManager.getCharacterCount());
        for(int i=0;i<BOARD_COUNT;i++) {
            ActorBoard b = mBoards.get(i);
            b.loadAssets();
        }

        // set bind character 设置绑定字符
        for(int i=0;i<validCharacterCount;i++) {
            ActorBoard b = mBoards.get(i);
            b.setBindValue(HeroFactory.getHero(i));
        }

        // compute total width of board list  计算板列表总宽度
        float boardWidth = mBoards.get(0).getBoundingRect().width();
        float actorBoardTotalWidth = BOARD_COUNT * boardWidth;
        r.left = (mScreenRect.width() - actorBoardTotalWidth) / 2;
        r.top = r.bottom + Utils.getRealHeight(10);
        for(int i = 0; i< BOARD_COUNT; i++) {
            ActorBoard b = mBoards.get(i);
            b.moveTo(r.left, r.top);
            r.left += boardWidth;
        }

        // platforms
        for(int i=0;i<mPlatforms.size();i++) {
            CharacterPlatform cp = mPlatforms.get(i);
            cp.loadAssets();
            cp.setBindValue(Game.getInstance().getPlayer(i));
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
	    int typeId = id & 0xffff;
        if(typeId == ID_BACK) {
            mNotify.back(SceneType.CHARACTER);
        } else if(typeId == ID_NEXT) {
            mNotify.next(SceneType.CHARACTER, 0);
        } else if(typeId == ID_TEAM_ATTACK) {

        } else if(typeId == ID_GAME_MODE) {

        } else if(typeId == ID_LIVES) {

        } else if(typeId == ID_ITEMS) {

        } else if(typeId == ID_BOARD) {
            int boardId = (id&0xffff0000)>>16;
            ActorBoard board = mBoards.get(boardId);
            CharacterPlatform host = (CharacterPlatform)mSelectBorder.getHost();
            if(host!=null && board!=null) {
                host.getBindValue().setHero(board.getBindValue());
            }
        }
    }

    public void setCurrentPlatformIndex(int index) {
        mCurrentPlatformIndex = index;
        if(mCurrentPlatformIndex<0 || mCurrentPlatformIndex>=mPlatforms.size())
            return;

        CharacterPlatform platform = mPlatforms.get(mCurrentPlatformIndex);
        mSelectBorder.setHost(platform);
    }

    public ArrayList<Player> getPlayerList() {
	    ArrayList<Player> playerList = new ArrayList<Player>();
        for(CharacterPlatform characterPlatform : mPlatforms) {
            playerList.add(characterPlatform.getBindValue());
        }
        return playerList;
    }

    public void step() {

    }
}
