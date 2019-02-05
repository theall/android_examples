package com.wx.multihero.variability.Hero;

import android.renderscript.Sampler;

public class Blow {
    public static final int NONE = 0;
    public static final int BLOCKING = 1;
    public static final int PUNCH = 2;
    public static final int FLYING_KICK = 3;
    public static final int LOW_KICK = 4;
    public static final int UPPERCUT = 5;
    public static final int THROWING_ITEM = 6;
    public static final int SPECIAL = 7;
    public static final int DOGDING = 8;
    public static final int DOWN_SPECIAL = 9;
    public static final int HIGH_KICK = 10;
    public static final int CLUB = 11;
    public static final int SHOOTING_POSITION = 12;
    public static final int ITEM_PICKUP = 13;
    public static final int SUPER_SPECIAL = 14;
    public static final int THROW = 15;
    public static final int WALK = 16;
    public static final int COUNT = 17;
    public int value;
    public float distance;
    public Blow(int value, float distance) {
        this.value = value;
        this.distance = distance;
    }
}
