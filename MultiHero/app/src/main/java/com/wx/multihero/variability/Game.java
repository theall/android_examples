package com.wx.multihero.variability;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.wx.multihero.R;
import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Renderable;
import com.wx.multihero.base.SoundPlayer;
import com.wx.multihero.base.Stepable;
import com.wx.multihero.base.Utils;
import com.wx.multihero.entity.Map;
import com.wx.multihero.variability.Sprite.LayersManager;
import com.wx.multihero.variability.Sprite.Player;

import java.util.ArrayList;

public class Game implements Stepable, Renderable {
    private static Game mInstance = null;
    public static Game getInstance() {
        if(mInstance == null)
            mInstance = new Game();
        return mInstance;
    }

    public enum State {
        PAUSED,
        PREPARING,
        READY,
        OVER,
        RUNNING
    }

    private GameMode mGameMode = new GameMode(GameMode.Type.ADV);
    private State mState;
    private Boolean mAutoPilot = true;
    private Boolean mAttackMate = false;
    private Boolean mUseItems = true;
    private Integer mLifes = 3;
    private static int PLAYER_COUNT = 10;
    private ArrayList<Player> mPlayers = new ArrayList<Player>();
    private LayersManager mLayersManager;
    private int mBackgroundMusic;
    private boolean mMapLoading;

    public Game() {
        mState = State.PREPARING;
        for(int i=0;i<PLAYER_COUNT;i++) {
            Player player = new Player();
            player.setName(Utils.getStringFromResourceId(R.string.player)+" "+(i+1));
            mPlayers.add(player);
        }
        mPlayers.get(0).setType(Player.Type.HUM);
        mLayersManager = new LayersManager();
        mBackgroundMusic = -1;
    }

    public void loadMap(Map map) {
        mMapLoading = true;
        mBackgroundMusic = AssetsLoader.getInstance().loadSound(
                String.format("sound/music%d.mp3",map.mMusicN1));
        mLayersManager.setMap(map);
        mMapLoading = false;
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
        mState = State.READY;
    }

    public GameMode getGameMode() {
        return mGameMode;
    }

    public State getState() {
        return mState;
    }

    public void setState(State state) {
        mState = state;
        if(state == State.PAUSED) {
            SoundPlayer.stopAudio();
        } else if(state == State.RUNNING) {
            SoundPlayer.playAudio(mBackgroundMusic);
        }
    }
    
    public void step() {
        if(mMapLoading)
            return;
        mLayersManager.step();
        if (mState == State.RUNNING || mState == State.OVER) {

        }

        if (mState==State.RUNNING && mAutoPilot) {
            autoPilot();
        }
    }

    private void autoPilot() {
        if (!mAutoPilot)
            return;
    }

    public void render(Canvas canvas, Paint paint) {
        if(mMapLoading)
            return;
        mLayersManager.render(canvas, paint);
    }

    public ArrayList<Player> getPlayerList() {
        return mPlayers;
    }

    public Player getPlayer(int index) {
        if(index<0 || index>=mPlayers.size())
            return null;
        return mPlayers.get(index);
    }

    public void earthQuake() {
        mLayersManager.earthQuake();
    }
}
