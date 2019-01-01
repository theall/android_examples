package com.wx.multihero.ui.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

public class Button extends TouchableWidget implements Renderable,Touchable {
    private Bitmap mNormal;
    private Bitmap mDown;
    private Text mText;

    public Button(int id, RectF boundingRect, Callback callback) {
        super(id, boundingRect, callback);
    }

    public Button(int id, RectF boundingRect, Callback callback, Bitmap normal) {
        super(id, boundingRect, callback);

        mNormal = normal;
    }

    public Button(int id, RectF boundingRect, Callback callback, Bitmap normal, Bitmap down) {
        super(id, boundingRect, callback);

        mNormal = normal;
        mDown = down;
    }

    public String getText() {
        return mText.getText();
    }

    public void setText(String text) {
        mText.setText(text);
    }

    public void setBitmaps(Bitmap normal) {
        mNormal = normal;
        mDown = normal;
    }

    public void setBitmaps(Bitmap normal, Bitmap down) {
        mNormal = normal;
        mDown = down;
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

        mText.render(canvas, paint);
    }

    public int processTouchEvent(MotionEvent event) {
        return 0;
    }
}
