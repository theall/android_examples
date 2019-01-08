package com.wx.multihero.ui.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

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
        if(bitmap == null)
            return;

        mBitmap = bitmap;
        if(mBoundingRect.isEmpty()) {
            mBoundingRect.left = 0;
            mBoundingRect.top = 0;
            mBoundingRect.right = bitmap.getWidth();
            mBoundingRect.bottom = bitmap.getHeight();
        }
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
        if(mBitmap == null)
            return;

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

    public void positionChanged(float dx, float dy) {
        center();
    }
}
