package com.wx.multihero.variability.Chunk;
    
public class 4WayExplosion extends Chunk {
    public 4WayExplosion() {
        super(Type.4_WAY_EXPLOSION);

        add(2, 25, 1);
        add(3, 25, 2);
        add(4, 25, 3);
        add(3, 25, 4);

    }
}