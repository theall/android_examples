package com.wx.multihero.game.variability.sprite;

public class SoundItem {
    public int id;
    public int delay;

    public SoundItem() {
        id = -1;
        delay = 0;
    }

    public SoundItem(int id) {
        this.id = id;
        this.delay = 0;
    }

    public SoundItem(int id, int delay) {
        this.id = id;
        this.delay = delay;
    }

    public void copy(SoundItem soundItem) {
        id = soundItem.id;
        delay = soundItem.delay;
    }

    public boolean isValid() {
        return id >= 0;
    }
}
