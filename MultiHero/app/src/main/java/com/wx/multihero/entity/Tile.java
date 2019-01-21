package com.wx.multihero.entity;

import java.io.DataInputStream;
import java.io.IOException;

public class Tile {
    public int x;
    public int y;
    public int number;
    public int setNumber;
    public int xend1;
    public int xend2;
    public int xrand1;
    public int xrand2;
    public int xspeed;
    public int yend1;
    public int yend2;
    public int yrand1;
    public int yrand2;
    public int yspeed;
    public int xstart;
    public int ystart;
    public int follow;
    public int target;
    public int x2;
    public int y2;
    public int followType;
    public int xScrSpeed;
    public int yScrSpeed;

    public Tile(DataInputStream inputStream) throws IOException {
        x = inputStream.readInt();
        y = inputStream.readInt();
        number = inputStream.readInt();
        setNumber = inputStream.readInt();
        xend1 = inputStream.readInt();
        xend2 = inputStream.readInt();
        xrand1 = inputStream.readInt();
        xrand2 = inputStream.readInt();
        xspeed = inputStream.readInt();
        yend1 = inputStream.readInt();
        yend2 = inputStream.readInt();
        yrand1 = inputStream.readInt();
        yrand2 = inputStream.readInt();
        yspeed = inputStream.readInt();
        xstart = inputStream.readInt();
        ystart = inputStream.readInt();
        follow = inputStream.readInt();
        target = inputStream.readInt();
        x2 = inputStream.readInt();
        y2 = inputStream.readInt();
        followType = inputStream.readInt();
        xScrSpeed = inputStream.readInt();
        yScrSpeed = inputStream.readInt();
    }
}
