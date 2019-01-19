package com.wx.multihero.variability;

public class GameMode {
    public enum Type {
        ADV,
        DEATH_MATCH,
        HIT_TARGET,
        CATCH_FLAG
    }

    private Type mType;
    public GameMode(Type _type) {
        mType = _type;
    }
    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        mType = type;
    }
}
