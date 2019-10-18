package com.wx.multihero.variability.object;

import android.graphics.RectF;

import com.wx.multihero.entity.Platform;
import com.wx.multihero.variability.sprite.Sprite;

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
