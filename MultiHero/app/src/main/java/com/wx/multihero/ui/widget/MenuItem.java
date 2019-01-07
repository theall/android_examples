package com.wx.multihero.ui.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class MenuItem extends TouchableWidget {
    private BitmapText mText;
    public MenuItem(int id, RectF rect, String text, Callback callback) {
        super(id, rect, callback);
        mDrawingRect = new RectF(rect);
        mText = new BitmapText(id, rect);
        mText.setText(text);
    }

    public void render(Canvas canvas, Paint paint) {
        if(isTouchingDown()) {
            Paint.Style oldStyle = paint.getStyle();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.GRAY);
            canvas.drawRect(mBoundingRect, paint);
            paint.setStyle(oldStyle);
        }
        mText.render(canvas, paint);
    }

    public void positionChanged(float dx, float dy) {
        mText.offset(dx, dy);
    }
}
