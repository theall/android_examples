package com.wx.multihero.ui.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wx.multihero.base.BigFont;

public class BitmapText extends AbstractText {

    public BitmapText(int id, RectF boundingRect) {
        super(id, boundingRect);
    }

    public float getStringWidth(String text) {
        return BigFont.getStringWidth(text);
    }

    public float getStringHeight(String text) {
        return BigFont.getStringHeight();
    }

    public void render(Canvas canvas, Paint paint) {
        if(mText.length() > 0) {
            BigFont.drawString(canvas, mText, mDrawingRect, paint);
        }
    }
}
