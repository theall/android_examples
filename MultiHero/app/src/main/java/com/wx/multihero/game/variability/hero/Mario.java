package com.wx.multihero.game.variability.hero;

import android.graphics.Bitmap;

import com.wx.multihero.game.base.AssetsLoader;
import com.wx.multihero.game.base.VectorF;
import com.wx.multihero.game.entity.Character;
import com.wx.multihero.game.variability.chunk.Chunk;
import com.wx.multihero.game.variability.sprite.Frame;

import java.util.ArrayList;
public class Mario extends Hero {
    public Mario(Character character) {
        super(character);
    }
    public void load(Character character) {
        clearActions();

        Action action = null;
        Frame frame = null;
        AssetsLoader assetsLoader = AssetsLoader.getInstance();

        // Ready
        action = getAction(Action.ID.READY);
        for (Bitmap bitmap : character.getReadyList()) {
            action.add(bitmap);
        }
        action.setBreakable(true);
    }
    public void go() {

    }
}
