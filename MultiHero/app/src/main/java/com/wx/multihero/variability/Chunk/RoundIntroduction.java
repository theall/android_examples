package com.wx.multihero.variability.Chunk;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.SoundPlayer;
import com.wx.multihero.base.Utils;
import com.wx.multihero.variability.Sprite.Frame;

public class RoundIntroduction extends Chunk {
    private int mSoundReady;
    private int mSoundFight;
    private int mFrameSeg;
    private float mStepY;
    public RoundIntroduction() {
        super(Type.ROUND_INTRODUCTION);

        add(100, 2, 1);
        add(140, 2, 2);
        AssetsLoader assetsLoader = AssetsLoader.getInstance();
        mSoundReady = assetsLoader.loadSound("sound/ready.mp3");
        mSoundFight = assetsLoader.loadSound("sound/fight.mp3");
        mFrameSeg = 0;
        mStepY = Utils.getRealHeight(10);
    }

    @Override
    public void step() {
        super.step();
        mFrameSeg++;

        if(mFrameSeg<30) {

        } else if(mFrameSeg==30) {
            SoundPlayer.playAudio(mSoundReady);
        } else if(mFrameSeg==180) {
            SoundPlayer.playAudio(mSoundFight);
        }
    }
}
