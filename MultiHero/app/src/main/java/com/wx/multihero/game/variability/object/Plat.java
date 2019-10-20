package com.wx.multihero.game.variability.object;

import com.wx.multihero.game.entity.Platform;
import com.wx.multihero.game.variability.sprite.Sprite;

public class Plat extends Sprite {
    public float right;
    public float bottom;
    public Plat(Platform platform) {
        x = platform.x;
        y = platform.y;
        right = x + platform.width;
        bottom = y + platform.height;
    }
}
