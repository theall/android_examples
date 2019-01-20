package com.wx.multihero.ui.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class PictureItem extends Widget {
    private Bitmap mBitmap;
    private Alignment mAlignment = new Alignment();
    public PictureItem(int id, RectF boundingRect, Bitmap bitmap) {
        super(id, boundingRect);

        setBitmap(bitmap);
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
        update();
    }

    public void setHCenter(Boolean center) {
        if(center)
            mAlignment.set(Alignment.HORIZONTAL_CENTER);
        else
            mAlignment.unset(Alignment.HORIZONTAL_CENTER);
        update();
    }

    public void setVCenter(Boolean center) {
        if(center)
            mAlignment.set(Alignment.VERTICAL_CENTER);
        else
            mAlignment.unset(Alignment.VERTICAL_CENTER);
        update();
    }

    public void center() {
        mAlignment.set(Alignment.CENTER);
        update();
    }

    public void render(Canvas canvas, Paint paint) {
        if(mBitmap != null) {
            canvas.drawBitmap(mBitmap, mDrawingRect.left, mDrawingRect.top, paint);
        }
    }

    public void positionChanged(float dx, float dy) {
        update();
    }

    private void update() {
        if(mBitmap == null)
            return;

        if(mAlignment.testFlag(Alignment.HORIZONTAL_CENTER)) {
            mDrawingRect.left = mBoundingRect.left + (mBoundingRect.width()-mBitmap.getWidth())/2;
        } else {
            mDrawingRect.left = mBoundingRect.left;
        }
        if(mAlignment.testFlag(Alignment.VERTICAL_CENTER)) {
            mDrawingRect.top = mBoundingRect.top + (mBoundingRect.height() - mBitmap.getHeight()) / 2;
        } else {
            mDrawingRect.top = mBoundingRect.top;
        }
    }
}
