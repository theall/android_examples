package com.wx.multihero.game.base;

public class VectorF {
    public float dx;
    public float dy;
    private Type type;
    public enum Type {
        ABSOLUTE,
        RELATIVE
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
