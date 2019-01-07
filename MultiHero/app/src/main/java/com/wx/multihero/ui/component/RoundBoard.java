package com.wx.multihero.ui.component;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.ui.widget.PictureItem;
import com.wx.multihero.ui.widget.Widget;

public class RoundBoard extends Widget {
    PictureItem mBackground;
    PictureItem mActorCpu;
    PictureItem mActorHumen;
    PictureItem mActorUnknown;
    
    public RoundBoard(int id, RectF boundingRect) {
        super(id, boundingRect);

        mBackground = new PictureItem(0, boundingRect, null);
        mActorCpu = new PictureItem(0, boundingRect, null);
        mActorHumen = new PictureItem(0, boundingRect, null);
        mActorUnknown = new PictureItem(0, boundingRect, null);
    }

    public void render(Canvas canvas, Paint paint) {
        mBackground.render(canvas, paint);
        mActorCpu.render(canvas, paint);
    }

    public void loadAssets() {
        mBackground.setBitmap(AssetsLoader.loadBitmap("gfx/ui/board.png"));
        mActorCpu.setBitmap(AssetsLoader.loadBitmap("gfx/ui/butCPU.png"));
        mActorHumen.setBitmap(AssetsLoader.loadBitmap("gfx/ui/butHum.png"));
        mActorUnknown.setBitmap(AssetsLoader.loadBitmap("gfx/ui/butNa.png"));

    }
}
