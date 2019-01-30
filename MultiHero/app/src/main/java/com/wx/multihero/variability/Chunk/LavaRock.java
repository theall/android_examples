package com.wx.multihero.variability.Chunk;

public class LavaRock extends Chunk {
    public LavaRock() {
        super(Type.LAVA_ROCK_BREAKING);

        add(10, 9, 1);
        add(10, 7, 2);
    }
}
