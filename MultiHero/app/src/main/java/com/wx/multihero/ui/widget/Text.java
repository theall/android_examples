package com.wx.multihero.ui.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.preference.PreferenceManager;

import com.wx.multihero.base.BigFont;

public class Text extends Widget {
    private String mText = "";
    private Boolean mUsePrimitiveFont = false;

    public Text(int id, RectF boundingRect) {
        super(id, boundingRect);
    }

    public void setHCenter(Boolean center) {
        if(center) {
            float stringDrawWidth = BigFont.getStringWidth(mText);
            mDrawingRect.left = mBoundingRect.left + (mBoundingRect.width()-stringDrawWidth)/2;
        } else {
            mDrawingRect.left = mBoundingRect.left;
        }
    }

    public void setVCenter(Boolean center) {
        if(center) {
            mDrawingRect.top = mBoundingRect.top + (mBoundingRect.height() - BigFont.getStringHeight()) / 2;
        } else {
            mDrawingRect.top = mBoundingRect.top;
        }
    }

    public void center() {
        setHCenter(true);
        setVCenter(true);
    }

    public Boolean getUsePrimitiveFont() {
        return mUsePrimitiveFont;
    }

    public void setUsePrimitiveFont(Boolean usePrimitiveFont) {
        mUsePrimitiveFont = usePrimitiveFont;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public void render(Canvas canvas, Paint paint) {
        if(mText.length() > 0) {
            if(mUsePrimitiveFont) {
                canvas.drawText(mText, mDrawingRect.left, mDrawingRect.top, paint);
            } else {
                BigFont.drawString(canvas, mText, mDrawingRect, paint);
            }
        }
    }
}
