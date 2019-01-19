package com.wx.multihero.variability;

public class Player {
    public enum Type {
        HUM,
        CPU
    }
    public enum Team {
        NONE,
        RED,
        BLUE,
        GREEN,
        YELLOW,
        WHITE,
        BROWN,
        ORANGE,
        PURPLE,
        PINK
    }
    private Type mType;
    private Team mTeam;
    private Character mCharacter;
    public Player() {
        mType = Type.CPU;
        mTeam = Team.NONE;
    }
}
