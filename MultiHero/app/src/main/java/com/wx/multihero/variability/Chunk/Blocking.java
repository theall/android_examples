package com.wx.multihero.variability.Chunk;
    
public class Blocking extends Chunk {
    public Blocking() {
        super(Type.BLOCKING);

        add(5, 3, 1);
        add(5, 1, 1);
        add(4, 1, 2);

    }
}