package com.wx.multihero.ui.widget;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wx.multihero.base.BigFont;

public abstract class AbstractText extends Widget {
    protected String mText = "";

    public static final int HORIZONTAL_CENTER = 0x1;
    public static final int VERTICAL_CENTER = 0x2;
    public static final int CENTER = HORIZONTAL_CENTER|VERTICAL_CENTER;

    private int mAlignment = CENTER;
    public AbstractText(int id, RectF boundingRect) {
        super(id, boundingRect);
    }

    public abstract float getStringWidth(String text);
    public abstract float getStringHeight(String text);

    public void setHCenter(Boolean center) {
        if(center) {
            mAlignment |= HORIZONTAL_CENTER;
        } else {
            mAlignment ^= HORIZONTAL_CENTER;
        }
        update();
    }

    public void setVCenter(Boolean center) {
        if(center) {
            mAlignment |= VERTICAL_CENTER;
        } else {
            mAlignment ^= VERTICAL_CENTER;
        }
        update();
    }

    public void center() {
        mAlignment = CENTER;
        update();
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
        center();
    }

    private void update() {
        if(mText.isEmpty())
            return;

        if(mBoundingRect.isEmpty()) {
            mBoundingRect.left = 0;
            mBoundingRect.top = 0;
            mBoundingRect.right = getStringWidth(mText);
            mBoundingRect.bottom = getStringHeight(mText);
        }

        if((mAlignment&HORIZONTAL_CENTER)==HORIZONTAL_CENTER) {
            mDrawingRect.left = mBoundingRect.left + (mBoundingRect.width()-getStringWidth(mText))/2;
        } else {
            mDrawingRect.left = mBoundingRect.left;
        }

        if((mAlignment&VERTICAL_CENTER)==VERTICAL_CENTER) {
            mDrawingRect.top = mBoundingRect.top + (mBoundingRect.height() - getStringHeight(mText)) / 2;
        } else {
            mDrawingRect.top = mBoundingRect.top;
        }
    }

    public void positionChanged(float dx, float dy) {
        update();
    }
}
