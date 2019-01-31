package com.wx.multihero.entity;

import com.wx.multihero.base.LittleEndianDataInputStream;

import java.io.IOException;

public class Tile {
    public float x;
    public float y;
    public int number;
    public int setNumber;
    public int xend1;
    public int xend2;
    public int xrand1;
    public int xrand2;
    public float xspeed;
    public int yend1;
    public int yend2;
    public int yrand1;
    public int yrand2;
    public float yspeed;
    public int xstart;
    public int ystart;
    public int follow;
    public int target;
    public int x2;
    public int y2;
    public int followType;
    public float xScrSpeed;
    public float yScrSpeed;
    public Tile nextTile = null;
    public int duration = 0;

    public Tile(LittleEndianDataInputStream inputStream) throws IOException {
        x = inputStream.readFloat();
        y = inputStream.readFloat();
        number = inputStream.readInt();
        setNumber = inputStream.readInt();
        xend1 = inputStream.readInt();
        xend2 = inputStream.readInt();
        xrand1 = inputStream.readInt();
        xrand2 = inputStream.readInt();
        xspeed = inputStream.readFloat();
        yend1 = inputStream.readInt();
        yend2 = inputStream.readInt();
        yrand1 = inputStream.readInt();
        yrand2 = inputStream.readInt();
        yspeed = inputStream.readFloat();
        xstart = inputStream.readInt();
        ystart = inputStream.readInt();
        follow = inputStream.readInt();
        target = inputStream.readInt();
        x2 = inputStream.readInt();
        y2 = inputStream.readInt();
        followType = inputStream.readInt();
        xScrSpeed = inputStream.readFloat();
        yScrSpeed = inputStream.readFloat();
    }
}
