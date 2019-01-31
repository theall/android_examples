package com.wx.multihero.variability.Chunk;
    
public class FourWayExplosion extends Chunk {
    public FourWayExplosion() {
        super(Type.FOUR_WAY_EXPLOSION);

        add(2, 25, 1);
        add(3, 25, 2);
        add(4, 25, 3);
        add(3, 25, 4);

    }
}