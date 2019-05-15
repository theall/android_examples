package com.wx.multihero.variability.Hero;

import android.graphics.Bitmap;

import com.wx.multihero.entity.Character;
import com.wx.multihero.variability.Sprite.SerializedFrames;

public class Ryu extends Hero {
    private int mSndHue = -1;
    private int mSndUpperCut = -1;
    private int mSndBall = -1;
    private int mSndSpin = -1;

    public Ryu(Character character) {
        super(character);

        mBlowDist[Blow.PUNCH] = 44;
        mBlowDist[Blow.FLYING_KICK] = 48;
        mBlowDist[Blow.LOW_KICK] = 44;
        mBlowDist[Blow.UPPERCUT] = 48;
        mBlowDist[Blow.SPECIAL] = 300;
        mBlowDist[Blow.DOWN_SPECIAL] = 70;
        mBlowDist[Blow.HIGH_KICK] = 44;
        mBlowDist[Blow.CLUB] = 150;
        mBlowDist[Blow.SUPER_SPECIAL] = 600;
    }

    public void load(Character character) {
        mBlowFramesMap.clear();

        // walk
        SerializedFrames walkFrames = new SerializedFrames();
        for(Bitmap bitmap : character.mBlockList) {
            walkFrames.add(10, bitmap);
        }
        mBlowFramesMap.put(Blow.WALK, walkFrames);

        SerializedFrames blockFrames = new SerializedFrames();
        for(Bitmap bitmap : character.mBlockList) {
            blockFrames.add(bitmap);
        }
        mBlowFramesMap.put(Blow.BLOCKING, blockFrames);

        // punch
        SerializedFrames punchFrames = new SerializedFrames();
        punchFrames.add(8, character.mBlowList.get(0));
        punchFrames.add(7, character.mBlowList.get(1));
        punchFrames.add(10, character.mBlowList.get(2));
        punchFrames.add(5, character.mBlowList.get(5));
        mBlowFramesMap.put(Blow.PUNCH, punchFrames);
    }

    public void go() {

    }
}
