package com.wx.multihero.ui.widget;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Layout;

import com.wx.multihero.base.BigFont;

public abstract class AbstractText extends Widget {
    protected String mText = "";

    private Alignment mAlignment = new Alignment();
    public AbstractText(int id, RectF boundingRect) {
        super(id, boundingRect);
    }

    public abstract float getStringWidth(String text);
    public abstract float getStringHeight(String text);

    public void setHCenter(Boolean center) {
        if(center) {
            mAlignment.set(Alignment.HORIZONTAL_CENTER);
        } else {
            mAlignment.unset(Alignment.HORIZONTAL_CENTER);
        }
        update();
    }

    public void setVCenter(Boolean center) {
        if(center) {
            mAlignment.set(Alignment.VERTICAL_CENTER);
        } else {
            mAlignment.unset(Alignment.VERTICAL_CENTER);
        }
        update();
    }

    public void setAlignment(int alignment) {
        mAlignment.setValue(alignment);
        update();
    }

    public void center() {
        mAlignment.setValue(Alignment.CENTER);
        update();
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
        update();
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

        if(mAlignment.testFlag(Alignment.HORIZONTAL_CENTER)) {
            mDrawingRect.left = mBoundingRect.left + (mBoundingRect.width()-getStringWidth(mText))/2;
        } else {
            mDrawingRect.left = mBoundingRect.left;
        }

        if(mAlignment.testFlag(Alignment.VERTICAL_CENTER)) {
            mDrawingRect.top = mBoundingRect.top + (mBoundingRect.height() - getStringHeight(mText)) / 2;
        } else {
            mDrawingRect.top = mBoundingRect.top;
        }
    }

    public void positionChanged(float dx, float dy) {
        update();
    }
}
