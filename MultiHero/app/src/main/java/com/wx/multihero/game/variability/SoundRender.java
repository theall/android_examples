package com.wx.multihero.game.variability;

import com.wx.multihero.game.base.Stepable;
import com.wx.multihero.game.variability.sprite.SoundItem;
import com.wx.multihero.os.SoundPlayer;

import java.util.ArrayList;

public class SoundRender implements Stepable {
    private ArrayList<SoundItem> mSoundItemList = new ArrayList<SoundItem>();
    private static SoundRender mSoundRender;

    private SoundRender() {

    }

    public static SoundRender getInstance() {
        if(mSoundRender == null)
            mSoundRender = new SoundRender();
        return mSoundRender;
    }

    public SoundItem add(int soundId) {
        SoundItem soundItem = new SoundItem(soundId);
        mSoundItemList.add(soundItem);
        return soundItem;
    }

    public void add(SoundItem soundItem) {
        if(soundItem.isValid())
            mSoundItemList.add(soundItem);
    }

    public void clear() {
        mSoundItemList.clear();
    }

    public void render(SoundPlayer player) {
        if(player == null)
            return;

        ArrayList<SoundItem> soundItemsToPlay = new ArrayList<SoundItem>();
        for(int i=mSoundItemList.size()-1;i>=0;i--) {
            SoundItem soundItem = mSoundItemList.get(i);
            if(soundItem.delay <= 0) {
                mSoundItemList.remove(i);
                soundItemsToPlay.add(soundItem);
                continue;
            }
        }

        for(int i=soundItemsToPlay.size()-1;i>=0;i--) {
            player.playSound(soundItemsToPlay.get(i).id);
        }

    }

    public void step() {
        for(SoundItem soundItem : mSoundItemList) {
            soundItem.delay--;
        }
    }
}
