package com.wx.multihero.entity;

import com.wx.multihero.base.LittleEndianDataInputStream;

import java.io.IOException;

public class Factory {
    public int x;
    public int y;
    public int dir;
    public int life;
    public int lives;
    public int team;
    public int damage;
    public int aiLevel;
    public int team_;
    public int category;
    public int type;
    public int delay;
    public int deadEvent;
    public int waitEvent;
    public int chunk;
    public int sound;
    public int var1;
    public int var2;
    public int var3;
    public int var4;
    public int var5;
    public Factory(LittleEndianDataInputStream inputStream) throws IOException {
        x = inputStream.readInt();
        y = inputStream.readInt();
        dir = inputStream.readInt();
        life = inputStream.readInt();
        lives = inputStream.readInt();
        team = inputStream.readInt();
        damage = inputStream.readInt();
        aiLevel = inputStream.readInt();
        team_ = inputStream.readInt();
        category = inputStream.readInt();
        type = inputStream.readInt();
        delay = inputStream.readInt();
        deadEvent = inputStream.readInt();
        waitEvent = inputStream.readInt();
        chunk = inputStream.readInt();
        sound = inputStream.readInt();
        var1 = inputStream.readInt();
        var2 = inputStream.readInt();
        var3 = inputStream.readInt();
        var4 = inputStream.readInt();
        var5 = inputStream.readInt();
    }
}
