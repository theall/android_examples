package com.wx.multihero.ui.component;

import android.graphics.Bitmap;
import android.graphics.RectF;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;
import com.wx.multihero.ui.widget.Button;

public class ForwardButton extends Button {
    public ForwardButton(int id, RectF boundingRect, Callback callback) {
        super(id, boundingRect, callback);
    }

    public void loadAssets() {
        Bitmap nextBitmap = AssetsLoader.getInstance().loadBitmap("gfx/ui/forward.png");
        setBitmaps(nextBitmap, nextBitmap);
        mBoundingRect.offsetTo(Utils.getScreenWidth() - nextBitmap.getWidth() - Utils.getRealWidth(10),
                Utils.getScreenHeight() - nextBitmap.getHeight() - Utils.getRealHeight(40));

        setTouchedSoundEffect(AssetsLoader.getInstance().loadSound("sound/click.mp3"));
    }
}
