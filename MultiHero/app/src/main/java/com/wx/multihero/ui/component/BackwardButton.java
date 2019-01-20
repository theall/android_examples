package com.wx.multihero.ui.component;

import android.graphics.Bitmap;
import android.graphics.RectF;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;
import com.wx.multihero.ui.widget.Button;

public class BackwardButton extends Button {
    public BackwardButton(int id, RectF boundingRect, Callback callback) {
        super(id, boundingRect, callback);
    }

    public void loadAssets() {
        Bitmap backBitmap = AssetsLoader.getInstance().loadBitmap("gfx/ui/backward.png");
        setBitmap(backBitmap, backBitmap);
        moveTo(Utils.getRealWidth(10), Utils.getScreenHeight() - backBitmap.getHeight() - Utils.getRealHeight(40));
        setTouchedSoundEffect(AssetsLoader.getInstance().loadSound("sound/click.mp3"));
    }
}
