package com.wx.multihero.ui.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.wx.multihero.base.Renderable;

public class Button extends TouchableWidget implements Renderable {
    private Bitmap mNormal;
    private Bitmap mDown;
    private PrimitiveText mText;

    public Button(int id, RectF boundingRect, Callback callback) {
        super(id, boundingRect, callback);

        mText = new PrimitiveText(0, boundingRect);
    }

    public Button(int id, RectF boundingRect, Callback callback, Bitmap normal) {
        super(id, boundingRect, callback);
        mText = new PrimitiveText(0, boundingRect);

        setBitmap(normal);
    }

    public Button(int id, RectF boundingRect, Callback callback, Bitmap normal, Bitmap down) {
        super(id, boundingRect, callback);
        mText = new PrimitiveText(0, boundingRect);

        setBitmap(normal, down);
    }

    public String getText() {
        return mText.getText();
    }

    public void setText(String text) {
        mText.setText(text);
    }

    public void setBitmap(Bitmap normal) {
        setBitmap(normal, normal);
    }

    public void setBitmap(Bitmap normal, Bitmap down) {
        mNormal = normal;
        mDown = down;
        if(mBoundingRect.isEmpty()) {
            mBoundingRect.left = 0;
            mBoundingRect.top = 0;
            mBoundingRect.right = mNormal.getWidth();
            mBoundingRect.bottom = mNormal.getHeight();
            mText.setBoundingRect(mBoundingRect);
        }
    }

    public void render(Canvas canvas, Paint paint) {
        Bitmap bitmap = null;
        if(isTouchingDown()) {
            if(mDown != null) {
                bitmap = mDown;
            }
        } else {
            bitmap = mNormal;
        }
        if(bitmap != null) {
            canvas.drawBitmap(bitmap, mBoundingRect.left, mBoundingRect.top, paint);
        }
        if(mText != null) {
            mText.render(canvas, paint);
        }
    }

    public boolean processTouchEvent(MotionEvent event) {
        super.processTouchEvent(event);
        return false;
    }

    public void positionChanged(float dx, float dy) {
        if(mText != null) {
            mText.offset(dx, dy);
        }
    }
}
