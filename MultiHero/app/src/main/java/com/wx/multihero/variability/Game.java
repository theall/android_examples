package com.wx.multihero.variability;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.wx.multihero.base.Renderable;
import com.wx.multihero.base.Stepable;
import com.wx.multihero.entity.Map;

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

    public Game() {
        mState = State.PREPARING;
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
            autopilot();
        }
    }

    private void autopilot() {
        if (!mAutoPilot)
            return;
    }

    public void render(Canvas canvas, Paint paint) {

    }
}
