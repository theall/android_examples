package com.wx.multihero.variability.Chunk;
    
public class VulcanoExplosion extends Chunk {
    public VulcanoExplosion() {
        super(Type.VULCANO_EXPLOSION);

        add(10, 11, 1);
        add(25, 11, 2);

    }
}