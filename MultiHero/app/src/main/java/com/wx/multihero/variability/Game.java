package com.wx.multihero.variability;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.wx.multihero.R;
import com.wx.multihero.base.Renderable;
import com.wx.multihero.base.Stepable;
import com.wx.multihero.base.Utils;
import com.wx.multihero.entity.Map;
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
    private Map mMap;
    private State mState;
    private Boolean mAutoPilot = true;
    private Boolean mAttackMate = false;
    private Boolean mUseItems = true;
    private Integer mLifes = 3;
    private static int PLAYER_COUNT = 10;
    private ArrayList<Player> mPlayers = new ArrayList<Player>();

    public Game() {
        mState = State.PREPARING;
        for(int i=0;i<PLAYER_COUNT;i++) {
            Player player = new Player();
            player.setName(Utils.getStringFromResourceId(R.string.player)+" "+(i+1));
            mPlayers.add(player);
        }
        mPlayers.get(0).setType(Player.Type.HUM);
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
    }
    
    public void step() {
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

    }

    public ArrayList<Player> getPlayerList() {
        return mPlayers;
    }

    public Player getPlayer(int index) {
        if(index<0 || index>=mPlayers.size())
            return null;
        return mPlayers.get(index);
    }
}
