package com.wx.multihero.variability;

import com.wx.multihero.entity.Map;

public class Game implements Stepable {
    public enum Mode {
        ADV,
        VS
    }
    public enum State {
        PAUSED,
        PREPARING,
        OVER,
        RUNNING
    }
    private Mode mMode;
    private Map mMap;
    private State mState;
    public Game() {
        mState = State.PREPARING;
        mMode = Mode.ADV;
    }

    public Mode getMode() {
        return mMode;
    }

    public void setMode(Mode mode) {
        mMode = mode;
    }

    public void step() {

    }
}
