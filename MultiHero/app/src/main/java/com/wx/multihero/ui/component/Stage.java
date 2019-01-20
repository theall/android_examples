package com.wx.multihero.ui.component;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.entity.Character;
import com.wx.multihero.ui.widget.PictureItem;
import com.wx.multihero.ui.widget.Widget;
import com.wx.multihero.variability.Player;

public class Stage extends Widget {
    private PictureItem mStageBitmap;
    private PictureItem mForegroundBitmap;
    private Character mBindValue;

    public Stage(int id, RectF boundingRect) {
        super(id, boundingRect);

        mStageBitmap = new PictureItem(0, null, null);
        mForegroundBitmap = new PictureItem(0, null, null);
    }

    public void setBindValue(Character value) {
        mBindValue = value;
        if(mBindValue==null) {
            mForegroundBitmap.setBitmap(null);
        } else {
            mForegroundBitmap.setBitmap(value.getPreview());
        }
    }

    public void render(Canvas canvas, Paint paint) {
        mStageBitmap.render(canvas, paint);
        mForegroundBitmap.render(canvas, paint);
    }

    public void loadAssets() {
        Bitmap stageBitmap = AssetsLoader.getInstance().loadBitmap("gfx/ui/pad.png");
        mStageBitmap.setBitmap(stageBitmap);
        RectF rect = new RectF(mStageBitmap.getBoundingRect());
        rect.bottom *= 2;
        setBoundingRect(rect);
        mStageBitmap.moveTo(0, rect.bottom - stageBitmap.getHeight());
        rect.bottom -= stageBitmap.getHeight()/2;
        mForegroundBitmap.setBoundingRect(rect);
    }

    public void positionChanged(float dx, float dy) {
        mStageBitmap.offset(dx, dy);
        mForegroundBitmap.offset(dx, dy);
    }

    public int processTouchEvent(MotionEvent event) {
        return 0;
    }
}
