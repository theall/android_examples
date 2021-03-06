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

package com.wx.multihero.game.variability;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wx.multihero.R;
import com.wx.multihero.game.base.AssetsLoader;
import com.wx.multihero.game.base.GameState;
import com.wx.multihero.game.base.Renderable;
import com.wx.multihero.game.variability.sprite.Sprite;
import com.wx.multihero.os.SoundPlayer;
import com.wx.multihero.game.base.Stepable;
import com.wx.multihero.game.base.Utils;
import com.wx.multihero.game.entity.Map;
import com.wx.multihero.game.entity.TriggersManager;
import com.wx.multihero.game.variability.sprite.LayersManager;
import com.wx.multihero.game.variability.ui.Player;

import java.util.ArrayList;

public class Game implements Stepable, Renderable {
    private static final float GRAVITY = 0.35f;
    private static Game mInstance = null;
    public static Game getInstance() {
        if(mInstance == null)
            mInstance = new Game();
        return mInstance;
    }

    private GameMode mGameMode = new GameMode(GameMode.Type.ADV);
    private GameState mState;
    private Boolean mAutoPilot = true;
    private Boolean mAttackMate = false;
    private Boolean mUseItems = true;
    private Integer mLifes = 3;
    private static int PLAYER_COUNT = 10;
    private int mScore;
    private int mHighScore;
    private float mVelocity;
    private float mGravity;
    private boolean mFlyOver;
    private int mMapOffsetX;
    private TriggersManager mTriggersManager = new TriggersManager();
    private ArrayList<Player> mPlayers = new ArrayList<Player>();
    private LayersManager mLayersManager;
    private int mBackgroundMusic;
    private boolean mMapLoading;
    private ArrayList<Player> mTeamRed = new ArrayList<Player>();
    private ArrayList<Player> mTeamBlue = new ArrayList<Player>();
    private ArrayList<Player> mTeamGreen = new ArrayList<Player>();
    private Map mCurrentMap;
    private SoundPlayer mSoundPlayer;
    private SoundRender mSoundRender;
    private Game() {
        mState = GameState.PREPARING;
        mSoundPlayer = SoundPlayer.getInstance();
        mSoundRender = SoundRender.getInstance();
        for(int i=0;i<PLAYER_COUNT;i++) {
            Player player = new Player();
            player.setTag(i);
            player.setName(Utils.getStringFromResourceId(R.string.player)+" "+(i+1));
            mPlayers.add(player);
        }

        Player firstPlayer = mPlayers.get(0);
        firstPlayer.setType(Player.Type.HUM);
        firstPlayer.setTeam(Player.Team.GREEN);
        mLayersManager = new LayersManager();
        mBackgroundMusic = -1;
    }

    public void loadMap(Map map, ArrayList<Player> players) throws Exception {
        mMapLoading = true;
        mBackgroundMusic = AssetsLoader.getInstance().loadSound(String.format("music%d",map.getMusicN1()));
        mLayersManager.setMap(map, players);
        mCurrentMap = map;
        mMapLoading = false;

        if(map != null)
            Sprite.gravity = map.gravity;
    }

    public Map getCurrentMap() {
        return mCurrentMap;
    }

    private void initNewGame() {
        mScore = 0;
        mFlyOver = false;
        mGravity = GRAVITY;
        mMapOffsetX = 0;
        mVelocity = 0;
        Game.getInstance().reset();
    }

    public Boolean getAttackMate() {
        return mAttackMate;
    }

    public Boolean getUseItems() {
        return mUseItems;
    }

    public Integer getLifes() {
        return mLifes;
    }

    public void reset() {
        mState = GameState.READY;
    }

    public GameMode getGameMode() {
        return mGameMode;
    }

    public GameState getState() {
        return mState;
    }

    public void setState(GameState state) {
        mState = state;
        if(state == GameState.PAUSED) {
            SoundPlayer.getInstance().stopAudio();
        } else if(state == GameState.RUNNING) {
            SoundPlayer.getInstance().playAudio(mBackgroundMusic);
        }
    }
    
    public void step() {
        if(mMapLoading)
            return;

        for(Player player : mPlayers)
            player.step();

        mLayersManager.step();
        mSoundRender.step();
    }

    private void autoPilot() {
        if (!mAutoPilot)
            return;
    }

    public void render(Canvas canvas, Paint paint) {
        if(mMapLoading)
            return;

        mLayersManager.render(canvas, paint);

        for(Player player : mPlayers) {
            if(player.isActived()) {
                player.render(canvas, paint);
            }
        }

        // Chunk render

        // Sound render
        mSoundRender.render(mSoundPlayer);
    }

    public ArrayList<Player> getPlayerList() {
        return mPlayers;
    }

    public Player getPlayer(int index) {
        if(index<0 || index>=mPlayers.size())
            return null;

        return mPlayers.get(index);
    }

    public void loadPlayers(ArrayList<Player> playerList) {
        mTeamBlue.clear();
        mTeamGreen.clear();
        mTeamRed.clear();
        for(Player player : playerList) {
            Player.Team team = player.getTeam();
            if(team == Player.Team.BLUE) {
                mTeamBlue.add(player);
            } else if(team == Player.Team.RED) {
                mTeamRed.add(player);
            } else if(team == Player.Team.GREEN) {
                mTeamGreen.add(player);
            } else {
                mTeamGreen.add(player);
            }
        }

        float marginTopOrigin = Utils.getRealHeight(6);
        float marginTop = marginTopOrigin;
        float marginLeft = Utils.getRealWidth(10);
        float marginSpace = Utils.getRealHeight(5);
        for(Player player : mTeamBlue) {
            player.moveTo(marginLeft, marginTop);
            RectF playerRect = player.getBoundingRect();
            marginTop += playerRect.height() + marginSpace;
        }

        marginTop = marginTopOrigin;
        for(Player player : mTeamRed) {
            RectF playerRect = player.getBoundingRect();
            float marginRight = Utils.getScreenWidth() - playerRect.width() - marginLeft;
            player.moveTo(marginRight, marginTop);
            marginTop += playerRect.height() + marginSpace;
        }
    }

    public void earthQuake() {
        mLayersManager.earthQuake();
    }
}
