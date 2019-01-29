package com.wx.multihero.variability.Chunk;

public class Explosion extends Chunk {
    public Explosion() {
        super(Type.EXPLOSION_40);

        add(5, 4, 1);
        add(5, 4, 2);
        add(5, 4, 1);
        add(5, 4, 2);
    }
}
