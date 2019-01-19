package com.wx.multihero.ui.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class PrimitiveText extends AbstractText {
    private int mColor;
    private int mFontSize;

    public PrimitiveText(int id, RectF boundingRect) {
        super(id, boundingRect);

        mColor = Color.BLACK;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public void setFontSize(int size) {
        mFontSize = size;
    }

    public float getStringWidth(String text) {
        Paint paint = new Paint();
        Rect rect = new Rect();
        paint.getTextBounds(text,0,text.length(), rect);
        return rect.width();
    }

    public float getStringHeight(String text) {
        Paint paint = new Paint();
        Rect rect = new Rect();
        paint.getTextBounds(text,0,text.length(), rect);
        return rect.height();
    }

    public void render(Canvas canvas, Paint paint) {
        if(canvas != null) {
            Paint.FontMetrics fm = paint.getFontMetrics();
            float realTop = mDrawingRect.top + getStringHeight(mText) - fm.descent;
            int oldColor = paint.getColor();
            paint.setColor(mColor);
            canvas.drawText(mText, mDrawingRect.left, realTop, paint);
            paint.setColor(oldColor);
        }
    }
}
