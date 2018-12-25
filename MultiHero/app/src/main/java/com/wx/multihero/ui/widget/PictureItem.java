package com.wx.multihero.ui.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wx.multihero.base.BigFont;

public class PictureItem extends Widget {
    private Bitmap mBitmap;

    public PictureItem(int id, RectF boundingRect, Bitmap bitmap) {
        super(id, boundingRect);

        mBitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public void setHCenter(Boolean center) {
        if(mBitmap == null)
            return;

        if(center) {
            mDrawingRect.left = mBoundingRect.left + (mBoundingRect.width()-mBitmap.getWidth())/2;
        } else {
            mDrawingRect.left = mBoundingRect.left;
        }
    }

    public void setVCenter(Boolean center) {
        if(center) {
            mDrawingRect.top = mBoundingRect.top + (mBoundingRect.height() - mBitmap.getHeight()) / 2;
        } else {
            mDrawingRect.top = mBoundingRect.top;
        }
    }

    public void center() {
        setHCenter(true);
        setVCenter(true);
    }

    public void render(Canvas canvas, Paint paint) {
        if(mBitmap != null) {
            canvas.drawBitmap(mBitmap, mDrawingRect.left, mDrawingRect.top, paint);
        }
    }
}
