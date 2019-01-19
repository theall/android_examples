package com.wx.multihero.ui.component;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.ui.widget.PictureItem;
import com.wx.multihero.ui.widget.Widget;
import com.wx.multihero.variability.Player;

public class Stage extends Widget {
    private PictureItem mStageBitmap;
    private Player mBindValue;

    public Stage(int id, RectF boundingRect) {
        super(id, boundingRect);

        mStageBitmap = new PictureItem(0, null, null);
    }

    public void setBindValue(Player value) {
        mBindValue = value;
    }

    public void render(Canvas canvas, Paint paint) {
        mStageBitmap.render(canvas, paint);
    }

    public void loadAssets() {
        Bitmap stageBitmap = AssetsLoader.getInstance().loadBitmap("gfx/ui/pad.png");
        mStageBitmap.setBitmap(stageBitmap);

        RectF rect = new RectF(mStageBitmap.getBoundingRect());
        rect.bottom *= 2;
        setBoundingRect(rect);

        mStageBitmap.moveTo(0, rect.bottom - stageBitmap.getHeight());
    }

    public void positionChanged(float dx, float dy) {
        mStageBitmap.offset(dx, dy);
    }
}
